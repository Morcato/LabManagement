package com.szx.entity;

import java.util.List;

import com.szx.po.Device;

public class ExperimentDetail {
	private Integer id;

	private String name;

	private String teacher;

	private String location;
	
	private String duration;

	private Integer nowPhase; // 下次开课的期数

	private Integer alreadyPhase; // 已经开过的期数

	private String introduction;

	private String laboratoryName; // 所属实验室

	private String college; // 所属学院

	private List<Device> devices;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getNowPhase() {
		return nowPhase;
	}

	public void setNowPhase(Integer nowPhase) {
		this.nowPhase = nowPhase;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getAlreadyPhase() {
		return alreadyPhase;
	}

	public void setAlreadyPhase(Integer alreadyPhase) {
		this.alreadyPhase = alreadyPhase;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public String getLaboratoryName() {
		return laboratoryName;
	}

	public void setLaboratoryName(String laboratoryName) {
		this.laboratoryName = laboratoryName;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
