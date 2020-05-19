package com.yang.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ThresholdEntity {

    @Getter @Setter
    private Double cpuThreshold;

    @Getter @Setter
    private Double memThreshold;

    @Getter @Setter
    private Double diskThreshold;
}
