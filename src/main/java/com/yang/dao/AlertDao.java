package com.yang.dao;

import com.yang.entity.ThresholdEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AlertDao {

    @Update("UPDATE thresholdtable SET " +
            "`cpuThreshold`=#{cpuThreshold},`memThreshold`=#{memThreshold},`diskThreshold`=#{diskThreshold}")
    boolean updateThreshold(ThresholdEntity thresholdData);

    @Select("SELECT * FROM thresholdtable")
    List<ThresholdEntity> getThreshold();
}
