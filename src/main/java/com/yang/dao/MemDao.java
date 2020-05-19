package com.yang.dao;

import com.yang.thrift.agent.MemData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemDao {

    @Select("SELECT * FROM memdatatable WHERE host = #{host} AND time = #{time}")
    List<MemData> getCurrentMemData(@Param("time") int time,@Param("host") String host);

    @Select({
            "<script>" +
                    "SELECT * FROM memdatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<MemData> getMemDataByMinute(@Param("timeList") List<Integer> timeList,@Param("host") String host); //60分钟内数据

    @Select({
            "<script>" +
                    "SELECT * FROM memdatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<MemData> getMemDataByHour(@Param("timeList") List<Integer> timeList,@Param("host") String host); //24小时内数据

    @Select({
            "<script>" +
                    "SELECT * FROM memdatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<MemData> getMemDataByDay(@Param("timeList") List<Integer> timeList,@Param("host") String host); //30天内数据

    @Insert("INSERT INTO memdatatable (`host`,`time`,`memTotal`,`memUsed`,`memFree`,`memUsedRatio`) " +
            "VALUES (#{host},#{time},#{memTotal},#{memUsed},#{memFree},#{memUsedRatio})")
    boolean insertMemData(MemData memData);

}
