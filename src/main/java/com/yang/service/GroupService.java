package com.yang.service;

import com.yang.entity.GroupEntity;

import java.util.List;

public interface GroupService {

    public List<String> getGroupNameList();

    public List<String> getHostList();

    public List<GroupEntity> getGroupList();

    public List<String> getHostListByGroupName(String groupName);

    boolean updateHostGroup(String host,String group);

    boolean addHostGroup(String host, String group);
}
