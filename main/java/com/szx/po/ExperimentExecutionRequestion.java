package com.szx.po;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class ExperimentExecutionRequestion {
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private Integer experimentalExecutionId;

	private String userno;

	private Integer status;

	private String remark;
	
	private Date requestTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExperimentalExecutionId() {
		return experimentalExecutionId;
	}

	public void setExperimentalExecutionId(Integer experimentalExecutionId) {
		this.experimentalExecutionId = experimentalExecutionId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	
}