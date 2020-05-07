package com.yang.service;

import com.yang.entity.ScriptEntity;

import java.util.List;

public interface ScriptService {

    boolean insertScript(ScriptEntity scriptEntity);

    ScriptEntity getScriptByName(String shellName);

    List<ScriptEntity> getAllScript();

    List<String> getScriptNameList();
}
