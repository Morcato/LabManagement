package com.szx.po;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Laboratory {
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String college;

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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }
}