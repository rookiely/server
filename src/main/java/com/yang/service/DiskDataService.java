package com.yang.service;

import com.yang.thrift.agent.DiskData;

import java.util.List;

public interface DiskDataService {

    List<DiskData> getDiskDataByMinute(String host);

    List<DiskData> getDiskDataByHour(String host);

    List<DiskData> getDiskDataByDay(String host);
}
