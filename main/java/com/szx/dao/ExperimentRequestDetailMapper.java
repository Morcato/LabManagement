package com.szx.dao;

import java.util.List;

import com.szx.entity.ExperimentRequestDetail;

public interface  ExperimentRequestDetailMapper {
   List<ExperimentRequestDetail> selectRequestDetailsByUserNo(String userNo);
   
   List<ExperimentRequestDetail> selectAllRequestByStatus(int status);
   
   List<ExperimentRequestDetail> selectAllAlreadyRequest();
}
