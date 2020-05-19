package com.yang.service;

import com.yang.thrift.agent.ScriptResultData;

import java.util.List;

public interface ScriptResultService {

    public List<ScriptResultData> getScriptResultList();
}
