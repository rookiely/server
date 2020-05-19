package com.yang.service.impl;

import com.yang.dao.ScriptResultDao;
import com.yang.service.ScriptResultService;
import com.yang.thrift.agent.ScriptResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptResultServiceImpl implements ScriptResultService {

    @Autowired
    private ScriptResultDao scriptResultDao;

    @Override
    public List<ScriptResultData> getScriptResultList() {
        return scriptResultDao.getScriptResultList();
    }

}
