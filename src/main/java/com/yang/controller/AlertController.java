package com.yang.controller;

import com.yang.RPCThriftClient;
import com.yang.thrift.alert.ThresholdData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/alert")
public class AlertController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RPCThriftClient rpcThriftClient;

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public boolean updateThreshold(@RequestBody ThresholdData thresholdData) {
        try {
            rpcThriftClient.open();
            rpcThriftClient.getAlertClient().updateThreshold(thresholdData);
            return true;
        } catch (Exception e) {
            logger.error("创建shell文件失败", e);
            return false;
        } finally {
            rpcThriftClient.close();
        }
    }
}
