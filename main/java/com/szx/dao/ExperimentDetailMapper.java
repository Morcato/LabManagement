package com.szx.dao;

import java.util.List;

import com.szx.entity.ExperimentDetail;

public interface ExperimentDetailMapper {
    ExperimentDetail getExperimentDetailByExperimentId(int id);
    
    ExperimentDetail selectExperimentDetailAndDevice(int id);
}
