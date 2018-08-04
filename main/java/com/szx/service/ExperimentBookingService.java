package com.szx.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szx.dao.ExperimentRequestDetailMapper;
import com.szx.dao.ExperimentalExecutionDetailMapper;
import com.szx.dao.impl.ExperimentExecutionRequestionMapper;
import com.szx.dao.impl.ExperimentalExecutionMapper;
import com.szx.entity.ExperimentRequestDetail;
import com.szx.exception.CommonException;
import com.szx.po.ExperimentExecutionRequestion;
import com.szx.po.ExperimentalExecution;
import com.szx.util.TimeUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ExperimentBookingService {

	@Autowired
	private ExperimentExecutionRequestionMapper experimentExecutionRequestionMapper;

	@Autowired
	private ExperimentalExecutionMapper experimentalExecutionDao;
	
	@Autowired
	private ExperimentalExecutionDetailMapper experimentalExecutionDetailDao;

	@Autowired
	private ExperimentRequestDetailMapper experimentRequestDetailDao;
	
	@Autowired
	private ExperimentService experimentService;

	@Transactional
	public void insertOneExperimentExecutionRequestion(String userNo, int experimentalExecutionId) {
		ExperimentExecutionRequestion requestion = new ExperimentExecutionRequestion();
		requestion.setUserno(userNo);
		requestion.setExperimentalExecutionId(experimentalExecutionId);
		requestion.setStatus(0);
		requestion.setRequestTime(new Date());
		if (experimentExecutionRequestionMapper.insert(requestion) != 1) {
			throw new CommonException("预定失败,请重试");
		}

	}

	/**
	 * 
	 * @param userNo
	 * @param experimentalExecutionId
	 * @return true代表没有待审核的该实验可以报名,false代表不能
	 */
	public boolean check(String userNo, int experimentalExecutionId) {
		Example example = new Example(ExperimentExecutionRequestion.class);
		Criteria criteria1 = example.createCriteria();
		criteria1.andEqualTo("experimentalExecutionId", experimentalExecutionId);
		criteria1.andEqualTo("userno", userNo);
		Criteria criteria2 = example.createCriteria();
		criteria2.orEqualTo("status", 0);
		criteria2.orEqualTo("status", 1);
		example.and(criteria2);
		List<ExperimentExecutionRequestion> requestions = experimentExecutionRequestionMapper.selectByExample(example);
		if (requestions.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public PageInfo<ExperimentRequestDetail> getExperimentExecutionRequestionsByUserNo(String userNo,Integer PageNum,Integer PageSize ) {
		try {
			int pageNum;
			int pageSize;
			if(PageNum == null) {
				 pageNum = 1;
			}else {
			 pageNum = PageNum;
			}
			if(PageSize == null) {
				pageSize = 5;
			}else {
				pageSize = PageSize;
			}
			List<ExperimentRequestDetail> requestions = new ArrayList<ExperimentRequestDetail>();
			PageHelper.startPage(pageNum, pageSize, true);
			requestions = experimentRequestDetailDao.selectRequestDetailsByUserNo(userNo);
			for (ExperimentRequestDetail requestDetail : requestions) {
				switch (requestDetail.getStatus()) {
				case 0:
					requestDetail.setStatusString("待审批");

					break;
				case 1:
					requestDetail.setStatusString("预约已通过");
					break;
				case 2:
					requestDetail.setStatusString("预约未通过");
					break;
				default:
					throw new CommonException("预约单审批状态暂时出现问题");
				}
			}
			PageInfo<ExperimentRequestDetail> pageInfo = new PageInfo<ExperimentRequestDetail>(requestions);
			return pageInfo;
		} catch (Exception e) {
			throw new CommonException(e.getMessage());
		}
	}

	@Transactional
	public boolean insertOneExperimenaltExecution(int experimentId, Date startTime, Date deadline,
			int number) {
		int phase = experimentService.experimentPhaseAdd(experimentId);
		ExperimentalExecution experimentalExecution = new ExperimentalExecution();
		experimentalExecution.setExperimentid(experimentId);
		experimentalExecution.setNumber(number);
		experimentalExecution.setPhase(phase);
		experimentalExecution.setDeadline(deadline);
		experimentalExecution.setStarttime(startTime);
		if (experimentalExecutionDao.insert(experimentalExecution) == 1) {
			return true;
		} else {
			return false;
		}

	}

	public PageInfo<ExperimentRequestDetail> getAllWaitedExperimentRequestDetail(Integer PageNum,Integer PageSize) {
		int pageNum;
		int pageSize;
		if(PageNum == null) {
			 pageNum = 1;
		}else {
		 pageNum = PageNum;
		}
		if(PageSize == null) {
			pageSize = 5;
		}else {
			pageSize = PageSize;
		}
		List<ExperimentRequestDetail> list = new ArrayList<ExperimentRequestDetail>();
		PageHelper.startPage(pageNum,pageSize,true);
		list = experimentRequestDetailDao.selectAllRequestByStatus(0);
		PageInfo<ExperimentRequestDetail> pageInfo = new PageInfo<ExperimentRequestDetail>(list);
		return pageInfo;
	}

	public 	PageInfo<ExperimentRequestDetail> getAllAlreadyExperimentRequestDetail(Integer PageNum,Integer PageSize) {
		int pageNum;
		int pageSize;
		if(PageNum == null) {
			 pageNum = 1;
		}else {
		 pageNum = PageNum;
		}
		if(PageSize == null) {
			pageSize = 5;
		}else {
			pageSize = PageSize;
		}
		List<ExperimentRequestDetail> list = new ArrayList<ExperimentRequestDetail>();
		PageHelper.startPage(pageNum,pageSize,true);
		list = experimentRequestDetailDao.selectAllAlreadyRequest();
		for (ExperimentRequestDetail requestDetail : list) {
			switch (requestDetail.getStatus()) {
			case 0:
				requestDetail.setStatusString("待审批");

				break;
			case 1:
				requestDetail.setStatusString("已通过");
				break;
			case 2:
				requestDetail.setStatusString("未通过");
				break;
			default:
				throw new CommonException("预约单审批状态暂时出现问题");
			}
		}
		PageInfo<ExperimentRequestDetail> pageInfo = new PageInfo<ExperimentRequestDetail>(list);
		return pageInfo;
	}

	@Transactional
	public boolean updateOneExperimentExecutionRequestion(int requestId, int status, String remark) {
		ExperimentExecutionRequestion requestion = new ExperimentExecutionRequestion();
		requestion.setId(requestId);
		requestion.setStatus(status);
		requestion.setRemark(remark);
		if (experimentExecutionRequestionMapper.updateByPrimaryKeySelective(requestion) == 1) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * 
	 * @param location
	 * @param startTime
	 * @return
	 * true 代表该天该地方可以做实验
	 * false  代表不可以
	 */
	public boolean checkLocationAndStartTime(String location,Date startTime) {
		startTime = TimeUtil.getDateWithoutTime(startTime);
		java.sql.Date sqlDate = new java.sql.Date(startTime.getTime());  
		System.out.println();
		if(experimentalExecutionDetailDao.countByLocationNameAndStartTime(location, sqlDate)==0) {
			return true;
		}else {
			return false;
		}
	}

}
