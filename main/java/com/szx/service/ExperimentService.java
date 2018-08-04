package com.szx.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szx.dao.ExperimentDetailMapper;
import com.szx.dao.ExperimentalExecutionDetailMapper;
import com.szx.dao.impl.ExperimentMapper;
import com.szx.entity.ExperimentDetail;
import com.szx.entity.ExperimentalExecutionDetail;
import com.szx.exception.CommonException;
import com.szx.po.Experiment;
import com.szx.util.TimeUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ExperimentService {

	@Autowired
	private ExperimentalExecutionDetailMapper experimentalExecutionDetailDao;

	@Autowired
	private ExperimentMapper experimentMapper;

	@Autowired
	private ExperimentDetailMapper experimentDetailMapper;

	public PageInfo<ExperimentalExecutionDetail> selectAllExperimentalExecutionDetail(Integer PageNum,
			Integer PageSize) {
		try {
			int pageNum;
			int pageSize;
			if (PageNum == null) {
				pageNum = 1;
			} else {
				pageNum = PageNum;
			}
			if (PageSize == null) {
				pageSize = 5;
			} else {
				pageSize = PageSize;
			}
			List<ExperimentalExecutionDetail> executionDetails = new ArrayList<ExperimentalExecutionDetail>();
			PageHelper.startPage(pageNum, pageSize, true);
			executionDetails = experimentalExecutionDetailDao.selectAll();
			Date d = TimeUtil.getDateWithoutTime(new Date());
			for (ExperimentalExecutionDetail executionDetail : executionDetails) {
				// 当前时间比截至日期早或者就是当日
				if (d.before(executionDetail.getDeadline()) || d.equals(executionDetail.getDeadline())) {
					executionDetail.setExpire(false);
				} else {
					executionDetail.setExpire(true);
				}
			}
			PageInfo<ExperimentalExecutionDetail> pageInfo = new PageInfo<ExperimentalExecutionDetail>(
					executionDetails);
			return pageInfo;

		} catch (Exception e) {
			throw new CommonException(e.getMessage());
		}

	}

	public ExperimentDetail getExperimentByExperimentId(int experimentId) {
		ExperimentDetail experimentDetail = experimentDetailMapper.getExperimentDetailByExperimentId(experimentId);
		return experimentDetail;
	}
	public ExperimentDetail getExperimentDetailAndDeviceByExperimentId(int experimentId) {
		ExperimentDetail experimentDetail = experimentDetailMapper.selectExperimentDetailAndDevice(experimentId);
		return experimentDetail;
	}

	public Map<Integer, String> getAllExperimentIdAndName() {
		Map<Integer, String> map = new TreeMap<Integer, String>();
		List<Experiment> experiments = experimentMapper.selectAll();
		for (Experiment e : experiments) {
			map.put(e.getId(), e.getName() + "  (编号:" + e.getId().toString() + ")");
		}
		return map;
	}

	public int experimentPhaseAdd(int experimentId) {
		Experiment experiment = new Experiment();
		experiment = experimentMapper.selectByPrimaryKey(experimentId);
		int phase = experiment.getAlreadyPhase();
		phase = phase + 1;
		experiment.setAlreadyPhase(phase);
		experimentMapper.updateByPrimaryKeySelective(experiment);
		return phase;
	}

	/**
	 * 
	 * @param experimentName
	 * @return true 代表数据库中没有重名的实验,false代表有
	 */
	public boolean checkExperimentName(String experimentName) {
		Example example = new Example(Experiment.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("name", experimentName);
		if (experimentMapper.selectByExample(example).size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean insertOneExperiment(String experimentName, String teacher, String introduction, int locationId,String duration) {
       Experiment experiment = new Experiment();
       experiment.setAlreadyPhase(0);
       experiment.setIntroduction(introduction);
       experiment.setLocationid(locationId);
       experiment.setName(experimentName);
       experiment.setTeacher(teacher);
       experiment.setDuration(duration);
       if(experimentMapper.insertSelective(experiment)==1) {
    	   return true;
       }else {
    	   throw new CommonException("添加实验时出错");
       }
	}

}
