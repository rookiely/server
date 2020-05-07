package com.yang.controller;

import com.yang.RPCThriftClient;
import com.yang.entity.ScriptEntity;
import com.yang.service.GroupService;
import com.yang.service.ScriptService;
import com.yang.thrift.script.ShellData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/script")
public class ScriptController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RPCThriftClient rpcThriftClient;

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public boolean addScript(@RequestBody ScriptEntity scriptEntity) {
        boolean isSuccess = scriptService.insertScript(scriptEntity);
        try {
            rpcThriftClient.open();
            List<String> hostList = groupService.getHostList();
            //针对每个host都建立shell文件
            ShellData shellData = new ShellData(scriptEntity.getShellName(), scriptEntity.getShellContent());
            rpcThriftClient.getScriptClient().createScriptFile(shellData);
        } catch (Exception e) {
            logger.error("创建shell文件失败", e);
            return false;
        } finally {
            rpcThriftClient.close();
        }
        return isSuccess;
    }

    @RequestMapping(value = "/exec/{groupName}/{shellName}")
    public boolean execScript(@PathVariable String groupName,@PathVariable String shellName) {
        try {
            rpcThriftClient.open();
            List<String> hostList = groupService.getHostListByGroupName(groupName);
            //针对每个host都进行thrift交互，执行脚本
            boolean isSuccess = rpcThriftClient.getScriptClient().execScript(shellName);
            return isSuccess;
        } catch (Exception e) {
            logger.error("执行shell文件失败", e);
            return false;
        } finally {
            rpcThriftClient.close();
        }
    }

    @RequestMapping(value = "/content/{shellName}",method = RequestMethod.GET)
    public ScriptEntity getScriptByName(@PathVariable String shellName) {
        try {
            return scriptService.getScriptByName(shellName);
        } catch (Exception e) {
            logger.error("获取脚本数据失败", e);
            return null;
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public List<ScriptEntity> getAllScript() {
        try {
            return scriptService.getAllScript();
        } catch (Exception e) {
            logger.error("获取脚本列表失败", e);
            return null;
        }
    }

    @RequestMapping(value = "/namelist", method = RequestMethod.GET)
    public List<String> getScriptNameList() {
        try {
            return scriptService.getScriptNameList();
        } catch (Exception e) {
            logger.error("获取脚本名称列表失败", e);
            return null;
        }
    }

}
