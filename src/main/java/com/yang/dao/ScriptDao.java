package com.yang.dao;

import com.yang.entity.ScriptEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptDao {

    @Insert("INSERT INTO scripttable (`scriptName`,`scriptContent`) VALUES (#{scriptName},#{scriptContent})")
    boolean insertScript(ScriptEntity scriptEntity);

    @Select("SELECT * FROM scripttable WHERE `scriptName` = #{scriptName}")
    ScriptEntity getScriptByName(@Param("scriptName") String scriptName);

    @Select("SELECT * FROM scripttable")
    List<ScriptEntity> getAllScript();

    @Select("SELECT DISTINCT `scriptName` FROM scripttable")
    List<String> getScriptNameList();
}
