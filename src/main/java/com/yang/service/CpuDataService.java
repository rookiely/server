package com.yang.service;

import com.yang.thrift.agent.CpuData;

import java.util.List;

public interface CpuDataService {

    List<CpuData> getCpuDataByMinute(String host);

    List<CpuData> getCpuDataByHour(String host);

    List<CpuData> getCpuDataByDay(String host);
}
