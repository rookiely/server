package com.yang.dao;

import com.yang.entity.ScriptEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptDao {

    @Insert("INSERT INTO scripttable (`shellName`,`shellContent`) VALUES (#{shellName},#{shellContent})")
    boolean insertScript(ScriptEntity scriptEntity);

    @Select("SELECT * FROM scripttable WHERE `shellName` = #{shellName}")
    ScriptEntity getScriptByName(@Param("shellName") String shellName);

    @Select("SELECT * FROM scripttable")
    List<ScriptEntity> getAllScript();

    @Select("SELECT DISTINCT `shellName` FROM scripttable")
    List<String> getScriptNameList();
}
