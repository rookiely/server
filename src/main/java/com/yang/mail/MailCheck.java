package com.yang.mail;

import com.yang.dao.*;
import com.yang.entity.ThresholdEntity;
import com.yang.service.MailService;
import com.yang.thrift.agent.CpuData;
import com.yang.thrift.agent.DiskData;
import com.yang.thrift.agent.MemData;
import com.yang.util.GetCurrentTimeByMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Component
public class MailCheck {

    @Autowired
    private AlertDao alertDao;

    @Autowired
    private CpuDao cpuDao;

    @Autowired
    private MemDao memDao;

    @Autowired
    private DiskDao diskDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void checkThreshold() throws UnknownHostException {
        int time = GetCurrentTimeByMin.getCurrentMinuteTime();
        boolean shouldEmail = false;

        // 获取threshold值
        List<ThresholdEntity> threshold = alertDao.getThreshold();
        ThresholdEntity thresholdData = threshold.get(0);
        double cpuThreshold = thresholdData.getCpuThreshold();
        double memThreshold = thresholdData.getMemThreshold();
        double diskThreshold = thresholdData.getDiskThreshold();

        List<String> hostList = groupDao.getHostList();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < hostList.size(); i++) {
            List<CpuData> currentCpuData = cpuDao.getCurrentCpuData(time, hostList.get(i));
            if (currentCpuData.size() == 0) {
                currentCpuData = cpuDao.getCurrentCpuData(time - 60, hostList.get(i));
            }
            for (CpuData cpuDataEle : currentCpuData) {
                if (cpuDataEle.getCpuCombinedRatio() >= cpuThreshold) {
                    sb.append("主机：").append(hostList.get(i)).append("cpu利用率超过阈值").append("\n");
                    shouldEmail = true;
                    break;
                }
            }
        }
        sb.append("\n");

        for (int i = 0; i < hostList.size(); i++) {
            List<MemData> currentMemData = memDao.getCurrentMemData(time, hostList.get(i));
            if (currentMemData.size() == 0) {
                currentMemData = memDao.getCurrentMemData(time - 60, hostList.get(i));
            }
            for (MemData memDataEle : currentMemData) {
                double memUsedRatio = memDataEle.getMemUsedRatio() / 100;
                if (memUsedRatio >= memThreshold) {
                    sb.append("主机：").append(hostList.get(i)).append("mem利用率超过阈值").append("\n");
                    shouldEmail = true;
                    break;
                }
            }
        }
        sb.append("\n");

        for (int i = 0; i < hostList.size(); i++) {
            List<DiskData> currentDiskData = diskDao.getCurrentDiskData(time, hostList.get(i));
            if (currentDiskData.size() == 0) {
                currentDiskData = diskDao.getCurrentDiskData(time - 60, hostList.get(i));
            }
            for (DiskData diskDataEle : currentDiskData) {
                if (diskDataEle.getDiskUsedRatio() >= diskThreshold) {
                    sb.append("主机：").append(hostList.get(i)).append("disk利用率超过阈值").append("\n");
                    shouldEmail = true;
                    break;
                }
            }
        }

        if (shouldEmail) {
            mailService.sendSimpleMail("1243604607@qq.com", "资源预警邮件", sb.toString());
        }
    }

}
