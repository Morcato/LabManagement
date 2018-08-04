package com.szx.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szx.entity.ExperimentalExecutionDetail;

public interface ExperimentalExecutionDetailMapper {
    List<ExperimentalExecutionDetail> selectAll();
    
    Integer countByLocationNameAndStartTime(@Param("location")String location,@Param("startTime")Date startTime);
}
