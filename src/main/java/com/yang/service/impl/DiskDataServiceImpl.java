package com.yang.service.impl;

import com.yang.dao.DiskDao;
import com.yang.service.DiskDataService;
import com.yang.thrift.agent.DiskData;
import com.yang.util.GetCurrentTimeByMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiskDataServiceImpl implements DiskDataService {


    @Autowired
    private DiskDao diskDao;

    @Override
    public List<DiskData> getDiskDataByMinute(String host) {
        // 60分钟数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime();
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            timeList.add(currentMinuteTime - i * 60);
        }
        return diskDao.getDiskDataByMinute(timeList, host);
    }

    @Override
    public List<DiskData> getDiskDataByHour(String host) {
        // 24小时数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime() - 60;
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            timeList.add(currentMinuteTime - i * 60 * 60);
        }
        return diskDao.getDiskDataByHour(timeList, host);
    }

    @Override
    public List<DiskData> getDiskDataByDay(String host) {
        // 30天数据
        int currentMinuteTime = GetCurrentTimeByMin.getCurrentMinuteTime() - 60;
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            timeList.add(currentMinuteTime - i * 24 * 60 * 60);
        }
        return diskDao.getDiskDataByDay(timeList, host);
    }
}
