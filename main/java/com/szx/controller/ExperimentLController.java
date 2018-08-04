package com.szx.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.szx.entity.ExperimentDetail;
import com.szx.entity.ExperimentalExecutionDetail;
import com.szx.po.Location;
import com.szx.service.ExperimentService;
import com.szx.service.LaboratoryService;
import com.szx.service.LocationService;

@Controller
@RequestMapping("experiment")
public class ExperimentLController {

	@Autowired
	private ExperimentService experimentService;

	@Autowired
	private LaboratoryService laboratoryService;

	@Autowired
	private LocationService locationService;

	@RequestMapping("list")
	public String getList(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		PageInfo<ExperimentalExecutionDetail> pageInfo = new PageInfo<ExperimentalExecutionDetail>();
		pageInfo = experimentService.selectAllExperimentalExecutionDetail(pageNum, pageSize);
		model.addAttribute("pageInfo", pageInfo);
		int rank = (Integer) session.getAttribute("rank");
		if (rank == 10) {
			return "indexPages/experimentListManagement";
		} else {
			return "indexPages/experimentList";
		}

	}

	@ResponseBody
	@RequestMapping("ajax_getExperimentInf")
	public Map<String, Object> getExperimentInf(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "experimentId") int experimentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ExperimentDetail experimentDetail = experimentService.getExperimentByExperimentId(experimentId);
		map.put("experiment", experimentDetail);
		return map;
	}

	@RequestMapping("getExperimentDetail")
	public String getExperimentDetail(Model model, @RequestParam(value = "experimentId") int experimentId) {
		ExperimentDetail experimentDetail = experimentService.getExperimentDetailAndDeviceByExperimentId(experimentId);
		model.addAttribute("experimentDetail", experimentDetail);
		return "experimentPages/experiment";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String toAddExperimentPages(Model model) {
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		map = laboratoryService.getAllLaboratoryNameAndId();
		model.addAttribute("map", map);
		return "indexPages/addExperiment";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String AddExperiment(HttpServletRequest request, @RequestParam(value = "experimentName") String experimentName,
			@RequestParam(value = "teacher") String teacher, @RequestParam(value = "location") int locationId,
			@RequestParam(value = "introduction") String introduction,@RequestParam(value = "duration") String duration) {
		if(experimentService.insertOneExperiment(experimentName, teacher, introduction, locationId,duration)) {
			request.setAttribute("msg", "添加成功");
			return "indexPages/succ";
		}else {
			request.setAttribute("msg", "添加失败");
			return "indexPages/err";
		}
	}

	@ResponseBody
	@RequestMapping(value = "ajax_getLocationByLaboratoryId")
	public Map<String, Object> getLoacation(@RequestParam(value = "laboratoryId") int laboratoryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locations = new ArrayList<Location>();
		locations = locationService.getLocationsByLaboratoryId(laboratoryId);
		map.put("locations", locations);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "ajax_checkExperimentName")
	public Map<String, Object> checkExperimentName(@RequestParam(value = "experimentName") String experimentName) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (experimentService.checkExperimentName(experimentName)) {
			map.put("isPass", true);
		} else {
			map.put("isPass", false);
		}
		return map;
	}

}
