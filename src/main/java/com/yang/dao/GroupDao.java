package com.yang.dao;

import com.yang.entity.GroupEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupDao {

    @Select("SELECT DISTINCT `group` FROM grouptable")
    List<String> getGroupNameList();

    @Select("SELECT DISTINCT `host` FROM grouptable")
    List<String> getHostList();

    @Select("SELECT * FROM grouptable")
    List<GroupEntity> getGroupList();

    @Select("SELECT `host` FROM grouptable WHERE `group`=#{groupName}")
    List<String> getHostListByGroupName(@Param("groupName") String groupName);

    @Update("UPDATE grouptable SET `group`=#{group} WHERE `host`=#{host}")
    boolean updateHostGroup(@Param("host") String host, @Param("group") String group);

    @Insert("INSERT INTO grouptable (`host`,`group`) VALUES (#{host},#{group})")
    boolean addHostGroup(@Param("host") String host, @Param("group") String group);
}
