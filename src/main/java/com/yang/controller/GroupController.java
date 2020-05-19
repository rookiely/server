package com.yang.controller;

import com.yang.entity.GroupEntity;
import com.yang.service.GroupService;
import com.yang.thrift.ClientHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/group")
public class GroupController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupService groupService;

    @Autowired
    private ClientHelper clientHelper;

    @RequestMapping(value = "/namelist", method = RequestMethod.GET)
    public List<String> getGroupNameList() {
        try {
            return groupService.getGroupNameList();
        } catch (Exception e) {
            logger.error("获取组别名称列表失败", e);
            return null;
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<GroupEntity> getGroupList() {
        try {
            return groupService.getGroupList();
        } catch (Exception e) {
            logger.error("获取组别列表失败", e);
            return null;
        }
    }

    @RequestMapping(value = "/update/{host}/{group}/{port}", method = RequestMethod.PUT)
    public boolean updateHostGroup(@PathVariable String host,@PathVariable String group,@PathVariable int port) {
        try {
            return groupService.updateHostGroup(host, group,port);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            return false;
        }
    }

    @RequestMapping(value = "/add/{host}/{group}/{port}", method = RequestMethod.POST)
    public boolean addHostGroup(@PathVariable String host,@PathVariable String group,@PathVariable int port) {
        try {
            boolean isSuccess = groupService.addHostGroup(host, group, port);
            clientHelper.addClient(host, port);
            return isSuccess;
        } catch (Exception e) {
            logger.error("添加数据失败", e);
            return false;
        }
    }

}
