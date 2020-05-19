package com.yang.controller;

import com.yang.entity.ScriptEntity;
import com.yang.service.GroupService;
import com.yang.service.ScriptResultService;
import com.yang.service.ScriptService;
import com.yang.thrift.ClientHelper;
import com.yang.thrift.agent.ScriptResultData;
import com.yang.thrift.server.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/script")
public class ScriptController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private ScriptResultService scriptResultService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ClientHelper clientHelper;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public boolean addScript(@RequestBody ScriptEntity scriptEntity) {
        boolean isSuccess = scriptService.insertScript(scriptEntity);
        if (!isSuccess) {
            logger.info("添加脚本文件失败");
        }
        return isSuccess;
    }

    @RequestMapping(value = "/exec/{groupName}/{scriptName}")
    public boolean execScript(@PathVariable String groupName,@PathVariable String scriptName) {
        try {
            List<String> hostList = groupService.getHostListByGroupName(groupName);
            ScriptEntity scriptEntity = scriptService.getScriptByName(scriptName);
            ScriptFile file = createFile(scriptEntity);
            clientHelper.execScript(hostList,file);
            return true;
        } catch (Exception e) {
            logger.error("执行脚本文件失败", e);
            return false;
        }
    }

    @RequestMapping(value = "/content/{scriptName}",method = RequestMethod.GET)
    public ScriptEntity getScriptByName(@PathVariable String scriptName) {
        try {
            return scriptService.getScriptByName(scriptName);
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

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public List<ScriptResultData> getScriptExecResultList() {
        try {
            return scriptResultService.getScriptResultList();
        } catch (Exception e) {
            logger.error("获取脚本执行结果列表失败", e);
            return null;
        }
    }

    private ScriptFile createFile(ScriptEntity scriptEntity) {
        String fileName = scriptEntity.getScriptName();
        String fileContent = scriptEntity.getScriptContent();
        String[] contentArr = fileContent.split("\n");

        File file = new File("src/main/resources/files/" + fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
            file.setExecutable(true);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bf = new BufferedWriter(fw);
            for (int i = 0; i < contentArr.length; i++) {
                bf.write(contentArr[i]);
                if (i < contentArr.length - 1) {
                    bf.newLine();
                }
            }
            bf.flush();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = toByteArray("src/main/resources/files/" + fileName);
        ScriptFile scriptFile = new ScriptFile();
        scriptFile.fileName = fileName;
        scriptFile.fileContent = ByteBuffer.wrap(bytes);
        return scriptFile;
    }

    private byte[] toByteArray(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
