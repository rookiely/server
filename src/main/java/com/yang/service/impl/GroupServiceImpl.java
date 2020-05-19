package com.yang.service.impl;

import com.yang.dao.GroupDao;
import com.yang.entity.GroupEntity;
import com.yang.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Override
    public List<String> getGroupNameList() {
        return groupDao.getGroupNameList();
    }

    @Override
    public List<String> getHostList() {
        return groupDao.getHostList();
    }

    @Override
    public List<GroupEntity> getGroupList() {
        return groupDao.getGroupList();
    }

    @Override
    public List<String> getHostListByGroupName(String groupName) {
        return groupDao.getHostListByGroupName(groupName);
    }

    @Override
    public boolean updateHostGroup(String host, String group,int port) {
        return groupDao.updateHostGroup(host, group,port);
    }

    @Override
    public boolean addHostGroup(String host, String group,int port) {
        return groupDao.addHostGroup(host, group,port);
    }

}
