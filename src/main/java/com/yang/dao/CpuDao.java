package com.yang.dao;

import com.yang.thrift.agent.CpuData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CpuDao {

    @Select("SELECT * FROM cpudatatable WHERE host = #{host} AND time = #{time}")
    List<CpuData> getCurrentCpuData(@Param("time") int time,@Param("host") String host);

    @Select({
            "<script>" +
                    "SELECT * FROM cpudatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<CpuData> getCpuDataByMinute(@Param("timeList") List<Integer> timeList,@Param("host") String host); //60分钟内数据

    @Select({
            "<script>" +
                    "SELECT * FROM cpudatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<CpuData> getCpuDataByHour(@Param("timeList") List<Integer> timeList,@Param("host") String host); //24小时内数据

    @Select({
            "<script>" +
                    "SELECT * FROM cpudatatable WHERE host = #{host} AND time in " +
                    "<foreach collection='timeList' item='item' index='index' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>" +
                    "</script>"
    })
    List<CpuData> getCpuDataByDay(@Param("timeList") List<Integer> timeList,@Param("host") String host); //30天内数据

    @Insert("INSERT INTO cpudatatable (`host`,`cpuId`,`time`,`cpuCombinedRatio`,`cpuIdleRatio`,`cpuWaitRatio`,`cpuNiceRatio`) " +
            "VALUES (#{host},#{cpuId},#{time},#{cpuCombinedRatio},#{cpuIdleRatio},#{cpuWaitRatio},#{cpuNiceRatio})")
    boolean insertCpuData(CpuData cpuData);


    @Insert({
            "<script>" +
                    "INSERT INTO cpudatatable (`host`,`cpuId`,`time`,`cpuCombinedRatio`,`cpuIdleRatio`,`cpuWaitRatio`,`cpuNiceRatio`) VALUES " +
                    "<foreach collection='cpuDataList' item='item' index='index' separator=','>" +
                    "(#{item.host},#{item.cpuId},#{item.time},#{item.cpuCombinedRatio},#{item.cpuIdleRatio},#{item.cpuWaitRatio},#{item.cpuNiceRatio})" +
                    "</foreach>" +
                    "</script>"
    })
    boolean insertCpuDataList(@Param("cpuDataList") List<CpuData> cpuDataList);
}
