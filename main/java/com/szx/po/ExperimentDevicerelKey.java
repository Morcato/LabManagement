package com.szx.po;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "experiment_devicerel")
public class ExperimentDevicerelKey {
	@Id
	private Integer experimentid;
	@Id
	private Integer deviceid;

	public Integer getExperimentid() {
		return experimentid;
	}

	public void setExperimentid(Integer experimentid) {
		this.experimentid = experimentid;
	}

	public Integer getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}
}