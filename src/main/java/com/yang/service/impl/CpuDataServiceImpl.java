package com.yang.service.impl;

import com.yang.dao.CpuDao;
import com.yang.service.CpuDataService;
import com.yang.thrift.agent.CpuData;
import com.yang.util.GetCurrentTimeByMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CpuDataServiceImpl implements CpuDataService {

    @Autowired
    private CpuDao cpuDao;

    @Override
    public List<CpuData> getCpuDataByMinute(String host) {
        // 60分钟数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime();
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            timeList.add(currentMinuteTime - i * 60);
        }
        return cpuDao.getCpuDataByMinute(timeList, host);
    }

    @Override
    public List<CpuData> getCpuDataByHour(String host) {
        // 24小时数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime() - 60;
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            timeList.add(currentMinuteTime - i * 60 * 60);
        }
        return cpuDao.getCpuDataByHour(timeList, host);
    }

    @Override
    public List<CpuData> getCpuDataByDay(String host) {
        // 30天数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime() - 60;
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            timeList.add(currentMinuteTime - i * 24 * 60 * 60);
        }
        return cpuDao.getCpuDataByDay(timeList, host);
    }
}
