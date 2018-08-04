package com.szx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szx.dao.impl.LaboratoryMapper;
import com.szx.po.Laboratory;

@Service
public class LaboratoryService {
	@Autowired
	private LaboratoryMapper laboratoryMapper;
	
	public TreeMap<String, Integer> getAllLaboratoryNameAndId(){
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		List<Laboratory> laboratories = new ArrayList<Laboratory>();
		laboratories = laboratoryMapper.selectAll();
		for(Laboratory laboratory:laboratories) {
			map.put(laboratory.getName(), laboratory.getId());
		}
		return map;
	}
}
