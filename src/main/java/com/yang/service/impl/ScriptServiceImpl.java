package com.yang.service.impl;

import com.yang.dao.ScriptDao;
import com.yang.entity.ScriptEntity;
import com.yang.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptDao scriptDao;

    @Override
    public boolean insertScript(ScriptEntity scriptEntity) {
        return scriptDao.insertScript(scriptEntity);
    }

    @Override
    public ScriptEntity getScriptByName(String shellName) {
        return scriptDao.getScriptByName(shellName);
    }

    @Override
    public List<ScriptEntity> getAllScript() {
        return scriptDao.getAllScript();
    }

    @Override
    public List<String> getScriptNameList() {
        return scriptDao.getScriptNameList();
    }

}
