package com.yang.controller;

import com.yang.entity.ThresholdEntity;
import com.yang.service.AlertService;
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
    private AlertService alertService;

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public boolean updateThreshold(@RequestBody ThresholdEntity thresholdData) {
        try {
            return alertService.updateThreshold(thresholdData);
        } catch (Exception e) {
            logger.error("更新阈值失败", e);
            return false;
        }
    }

}
