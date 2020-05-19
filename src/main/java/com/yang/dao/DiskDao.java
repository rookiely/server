package com.yang.dao;

import com.yang.thrift.agent.DiskData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DiskDao {

    @Select("SELECT * FROM diskdatatable WHERE host = #{host} AND time = #{time}")
    List<DiskData> getCurrentDiskData(@Param("time") int time,@Param("host") String host);

    @Select({
            "<script>" +
                    "SELECT * FROM diskdatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<DiskData> getDiskDataByMinute(@Param("timeList") List<Integer> timeList,@Param("host") String host); //60分钟内数据

    @Select({
            "<script>" +
                    "SELECT * FROM diskdatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<DiskData> getDiskDataByHour(@Param("timeList") List<Integer> timeList,@Param("host") String host); //24小时内数据

    @Select({
            "<script>" +
                    "SELECT * FROM diskdatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<DiskData> getDiskDataByDay(@Param("timeList") List<Integer> timeList,@Param("host") String host); //30天内数据

    @Insert("INSERT INTO diskdatatable (`host`,`diskName`,`time`,`diskTotal`,`diskFree`,`diskUsed`,`diskAvail`,`diskUsedRatio`) " +
            "VALUES (#{host},#{diskName},#{time},#{diskTotal},#{diskFree},#{diskUsed},#{diskAvail},#{diskUsedRatio})")
    boolean insertDiskData(DiskData diskData);


    @Insert({
            "<script>" +
                    "INSERT INTO diskdatatable (`host`,`diskName`,`time`,`diskTotal`,`diskFree`,`diskUsed`,`diskAvail`,`diskUsedRatio`) VALUES " +
                    "<foreach collection='diskDataList' item='item' index='index' separator=','>" +
                    "(#{item.host},#{item.diskName},#{item.time},#{item.diskTotal},#{item.diskFree},#{item.diskUsed},#{item.diskAvail},#{item.diskUsedRatio})" +
                    "</foreach>" +
                    "</script>"
    })
    boolean insertDiskDataList(@Param("diskDataList") List<DiskData> diskDataList);

}
