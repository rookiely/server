package com.yang.controller;

import com.yang.service.CpuDataService;
import com.yang.service.DiskDataService;
import com.yang.service.MemDataService;
import com.yang.thrift.agent.CpuData;
import com.yang.thrift.agent.DiskData;
import com.yang.thrift.agent.MemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/resource")
public class ResourceDataController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CpuDataService cpuDataService;

    @Autowired
    private MemDataService memDataService;

    @Autowired
    private DiskDataService diskDataService;

    @RequestMapping(value = "/cpu/{host}/{type}", method = RequestMethod.GET)
    public List<CpuData> getCpuData(@PathVariable String host,@PathVariable String type) {
        try {
            if ("bymin".equals(type)) {
                return cpuDataService.getCpuDataByMinute(host);
            } else if ("byhour".equals(type)) {
                return cpuDataService.getCpuDataByHour(host);
            } else {
                return cpuDataService.getCpuDataByDay(host);
            }
        } catch (Exception e) {
            logger.error("获取cpu资源数据失败", e);
            return null;
        }
    }

    @RequestMapping(value = "/mem/{host}/{type}", method = RequestMethod.GET)
    public List<MemData> getMemData(@PathVariable String host,@PathVariable String type) {
        try {
            if ("bymin".equals(type)) {
                return memDataService.getMemDataByMinute(host);
            } else if ("byhour".equals(type)) {
                return memDataService.getMemDataByHour(host);
            } else {
                return memDataService.getMemDataByDay(host);
            }
        } catch (Exception e) {
            logger.error("获取内存资源数据失败", e);
            return null;
        }
    }

    @RequestMapping(value = "/disk/{host}/{type}", method = RequestMethod.GET)
    public List<DiskData> getDiskData(@PathVariable String host,@PathVariable String type) {
        try {
            if ("bymin".equals(type)) {
                return diskDataService.getDiskDataByMinute(host);
            } else if ("byhour".equals(type)) {
                return diskDataService.getDiskDataByHour(host);
            } else {
                return diskDataService.getDiskDataByDay(host);
            }
        } catch (Exception e) {
            logger.error("获取磁盘资源数据失败", e);
            return null;
        }
    }
}
