package com.yang.service.impl;

import com.yang.dao.AlertDao;
import com.yang.entity.ThresholdEntity;
import com.yang.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertDao alertDao;

    @Override
    public ThresholdEntity getThreshold() {
        return alertDao.getThreshold().get(0);
    }

    @Override
    public boolean updateThreshold(ThresholdEntity thresholdEntity) {
        return alertDao.updateThreshold(thresholdEntity);
    }
}
