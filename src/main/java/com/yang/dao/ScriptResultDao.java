package com.yang.dao;

import com.yang.thrift.agent.ScriptResultData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptResultDao {

    @Insert("INSERT INTO scriptresulttable (`scriptName`,`host`,`scriptResult`,`time`) " +
            "VALUES (#{scriptName},#{host},#{scriptResult},#{time})")
    boolean insertScriptResult(ScriptResultData scriptResultData);

    @Select("SELECT * FROM scriptresulttable")
    List<ScriptResultData> getScriptResultList();
}
