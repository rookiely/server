package com.yang.service.impl;

import com.yang.dao.MemDao;
import com.yang.service.MemDataService;
import com.yang.thrift.agent.MemData;
import com.yang.util.GetCurrentTimeByMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemDataServiceImpl implements MemDataService {

    @Autowired
    private MemDao memDao;

    @Override
    public List<MemData> getMemDataByMinute(String host) {
        // 60分钟数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime();
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            timeList.add(currentMinuteTime - i * 60);
        }
        return memDao.getMemDataByMinute(timeList, host);
    }

    @Override
    public List<MemData> getMemDataByHour(String host) {
        // 24小时数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime() - 60;
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            timeList.add(currentMinuteTime - i * 60 * 60);
        }
        return memDao.getMemDataByHour(timeList, host);
    }

    @Override
    public List<MemData> getMemDataByDay(String host) {
        // 30天数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime() - 60;
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            timeList.add(currentMinuteTime - i * 24 * 60 * 60);
        }
        return memDao.getMemDataByDay(timeList, host);
    }
}
