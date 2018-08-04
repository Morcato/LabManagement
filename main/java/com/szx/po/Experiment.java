package com.szx.po;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Experiment {
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String teacher;

    private Integer locationid;
    
    private Integer alreadyPhase;
    
    private String introduction;
    
    private String duration;

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
        this.name = name == null ? null : name.trim();
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }

    public Integer getLocationid() {
        return locationid;
    }

    public void setLocationid(Integer locationid) {
        this.locationid = locationid;
    }

	public Integer getAlreadyPhase() {
		return alreadyPhase;
	}

	public void setAlreadyPhase(Integer alreadyPhase) {
		this.alreadyPhase = alreadyPhase;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
    
    
}