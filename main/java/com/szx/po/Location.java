package com.szx.po;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Location {
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;
    
    private int laboratoryId;

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

	public int getLaboratoryId() {
		return laboratoryId;
	}

	public void setLaboratoryId(int laboratoryId) {
		this.laboratoryId = laboratoryId;
	}
    
    
}