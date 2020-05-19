package com.yang.service;

import com.yang.thrift.agent.MemData;

import java.util.List;

public interface MemDataService {

    List<MemData> getMemDataByMinute(String host);

    List<MemData> getMemDataByHour(String host);

    List<MemData> getMemDataByDay(String host);
}
