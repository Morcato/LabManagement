package com.szx.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szx.dao.impl.LocationMapper;
import com.szx.po.Location;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class LocationService {

	@Autowired
	private LocationMapper locationMapper;

	public List<Location> getLocationsByLaboratoryId(int laboratoryId) {
		List<Location> locations = new ArrayList<Location>();
		Example example = new Example(Location.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("laboratoryId", laboratoryId);
		locations = locationMapper.selectByExample(example);
		return locations;
	}
}
