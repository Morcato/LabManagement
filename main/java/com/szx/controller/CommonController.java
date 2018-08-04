package com.szx.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.szx.exception.CommonException;
import com.szx.service.ExperimentService;

@Controller
public class CommonController {
	
	@Autowired
	private ExperimentService experimentService;

	@RequestMapping(value = "left")
	public String ToLeft() {
		return "loginPages/left";
	}
	@RequestMapping(value = "index")
	public String ToIndex() {
		return "indexPages/index";
	}
	@RequestMapping(value = "main")
	public String ToMain() {
		return "indexPages/main";
	}
	@RequestMapping(value = "top")
	public String ToTop() {
		return "indexPages/top";
	}
	@RequestMapping(value = "left2")
	public String ToIndexLeft() {
		return "indexPages/left";
	}
	@RequestMapping(value = "right")
	public String ToIndexRight() {
		return "indexPages/right";
	}
	@RequestMapping(value = "right_experimentPage")
	public String ToExperimentRight() {
		return "experimentPages/right";
	}
	@RequestMapping(value = "left_experimentPage")
	public String ToExperimentLeft(Model model) {
		Map<Integer, String> map = new TreeMap<Integer, String>();
		map = experimentService.getAllExperimentIdAndName();
		model.addAttribute("map",map);
		return "experimentPages/left";
	}
	@RequestMapping(value = "top_experimentPage")
	public String ToExperimentTop() {
		return "experimentPages/top";
	}
	@RequestMapping(value = "main_experimentPage")
	public String ToExperimentMain() {
		return "experimentPages/main";
	}
	
	@RequestMapping(value = "experimentPage")
	public String ToExperimentPage() {
		return "experimentPages/index";
	}
	@RequestMapping(value = "top_loginPage")
	public String ToLoginTop() {
		return "loginPages/top";
	}
	
	@RequestMapping(value = "main_loginPage")
	public String ToLoginMain() {
		return "loginPages/main";
	}
	@RequestMapping(value = "error")
	public String toErrPages() {
	   return "commonPages/err";	
	}
}
