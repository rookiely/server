package com.yang.thrift;

import com.yang.dao.*;
import com.yang.entity.GroupEntity;
import com.yang.thrift.agent.*;
import com.yang.thrift.server.ScriptFile;
import com.yang.thrift.server.ServerService;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ClientHelper {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private CpuDao cpuDao;

    @Autowired
    private MemDao memDao;

    @Autowired
    private DiskDao diskDao;

    @Autowired
    private ScriptResultDao scriptResultDao;

    private Map<String, ServerService.Client> clientMap = new HashMap<>();

    private ExecutorService threadpool = Executors.newFixedThreadPool(16);

    public void initClients() {
        List<GroupEntity> groupList = groupDao.getGroupList();
        for (int i = 0; i < groupList.size(); i++) {
            int finalI = i;
            Runnable runnable = () -> {
                TSocket tSocket = new TSocket(groupList.get(finalI).getHost(), groupList.get(finalI).getPort());
                ServerService.Client client = new ServerService.Client(new TBinaryProtocol(tSocket));
                clientMap.put(groupList.get(finalI).getHost(), client);
                try {
                    tSocket.open();
                    waitForCallback(tSocket);
                    client.getResourceData();
                } catch (TException e) {
                    e.printStackTrace();
                }
            };
            this.threadpool.execute(runnable);
        }
    }

    public void addClient(String host, int port) {
        Runnable runnable = () -> {
            TSocket tSocket = new TSocket(host, port);
            ServerService.Client client = new ServerService.Client(new TBinaryProtocol(tSocket));
            clientMap.put(host, client);
            try {
                tSocket.open();
                waitForCallback(tSocket);
                client.getResourceData();
            } catch (TException e) {
                e.printStackTrace();
            }
        };
        this.threadpool.execute(runnable);
    }

    public void execScript(List<String> hostList, ScriptFile scriptFile) {
        for (int i = 0; i < hostList.size(); i++) {
            ServerService.Client client = clientMap.get(hostList.get(i));
            try {
                client.execScript(scriptFile);

            } catch (TException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitForCallback(TSocket tSocket) {
        new Thread(() -> {
            TProcessor processor = new AgentCallbackService.Processor<AgentCallbackService.Iface>(new AgentCallbackService.Iface() {

                @Override
                public void pushResourceData(List<CpuData> cpuDataList, List<MemData> memDataList, List<DiskData> diskDataList) throws TException {
                    cpuDao.insertCpuDataList(cpuDataList);
                    memDao.insertMemData(memDataList.get(0));
                    diskDao.insertDiskDataList(diskDataList);
                }

                @Override
                public void pushScriptResult(ScriptResultData scriptResultData) throws TException {
                    scriptResultDao.insertScriptResult(scriptResultData);
                }

            });
            TBinaryProtocol protocol = new TBinaryProtocol(tSocket);
            try{
                while (processor.process(protocol, protocol)) {
                }
            } catch (TException e) {
                tSocket.close();
                e.printStackTrace();
            }
        }).start();
    }
}
