package com.yang.controller;

import com.yang.RPCThriftClient;
import com.yang.thrift.cpu.CpuData;
import com.yang.thrift.disk.DiskData;
import com.yang.thrift.mem.MemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/resource")
public class RPCThriftContoller {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RPCThriftClient rpcThriftClient;

    @RequestMapping(value = "/cpu/{host}", method = RequestMethod.GET)
    public List<CpuData> getCpuData(@PathVariable String host) {
        try {
            //连接host主机进行thrift交互
            rpcThriftClient.open();
            List<CpuData> cpuDataByMin = rpcThriftClient.getCpuDataClient().getCpuDataByMin();
            return cpuDataByMin;
        } catch (Exception e) {
            logger.error("获取cpu资源数据失败", e);
            return null;
        } finally {
            rpcThriftClient.close();
        }
    }

    @RequestMapping(value = "/mem/{host}", method = RequestMethod.GET)
    public List<MemData> getMemData(@PathVariable String host) {
        try {
            //连接host主机进行thrift交互
            rpcThriftClient.open();
            return rpcThriftClient.getMemDataClient().getMemDataByMin();
        } catch (Exception e) {
            logger.error("获取内存资源数据失败", e);
            return null;
        } finally {
            rpcThriftClient.close();
        }
    }

    @RequestMapping(value = "/disk/{host}", method = RequestMethod.GET)
    public List<DiskData> getDiskData(@PathVariable String host) {
        try {
            //连接host主机进行thrift交互
            rpcThriftClient.open();
            return rpcThriftClient.getDiskDataClient().getDiskDataByMin();
        } catch (Exception e) {
            logger.error("获取磁盘资源数据失败", e);
            return null;
        } finally {
            rpcThriftClient.close();
        }
    }
}
