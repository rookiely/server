package com.yang.service;

import com.yang.entity.ThresholdEntity;

public interface AlertService {

    public ThresholdEntity getThreshold();

    public boolean updateThreshold(ThresholdEntity thresholdEntity);
}
