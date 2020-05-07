package com.yang;

import com.yang.thrift.alert.AlertService;
import com.yang.thrift.cpu.CpuDataService;
import com.yang.thrift.disk.DiskDataService;
import com.yang.thrift.mem.MemDataService;
import com.yang.thrift.script.ScriptService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class RPCThriftClient {

    private CpuDataService.Client cpuDataClient;
    private MemDataService.Client memDataClient;
    private DiskDataService.Client diskDataClient;
    private ScriptService.Client scriptClient;
    private AlertService.Client alertClient;

    private TBinaryProtocol protocol;
    private TSocket transport;
    private String host;
    private int port;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public void init() {
        transport = new TSocket(host, port);
        protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"CpuDataService");
        cpuDataClient = new CpuDataService.Client(mp1);
        TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"MemDataService");
        memDataClient = new MemDataService.Client(mp2);
        TMultiplexedProtocol mp3 = new TMultiplexedProtocol(protocol,"DiskDataService");
        diskDataClient = new DiskDataService.Client(mp3);
        TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"ScriptService");
        scriptClient = new ScriptService.Client(mp4);
        TMultiplexedProtocol mp5 = new TMultiplexedProtocol(protocol,"AlertService");
        alertClient = new AlertService.Client(mp5);
    }

    public CpuDataService.Client getCpuDataClient() {
        return cpuDataClient;
    }

    public MemDataService.Client getMemDataClient() {
        return memDataClient;
    }

    public DiskDataService.Client getDiskDataClient() {
        return diskDataClient;
    }

    public ScriptService.Client getScriptClient() {
        return scriptClient;
    }

    public AlertService.Client getAlertClient() {
        return alertClient;
    }

    public void open() throws TTransportException {
        transport.open();
    }

    public void close() {
        transport.close();
    }

}
