package com.szx.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.szx.entity.ExperimentRequestDetail;
import com.szx.exception.CommonException;
import com.szx.service.ExperimentBookingService;
import com.szx.service.ExperimentService;
import com.szx.util.TimeUtil;

@Controller
@RequestMapping(value = "booking")
public class ExperimentBookingController {

	@Autowired
	private ExperimentBookingService bookingService;

	@Autowired
	private ExperimentService experimentService;

	@ResponseBody
	@RequestMapping(value = "book")
	public Map<String, Object> book(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "experimentalExecutionId") int experimentalExecutionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userNo = session.getAttribute("userNo").toString();
		if (bookingService.check(userNo, experimentalExecutionId)) {
			bookingService.insertOneExperimentExecutionRequestion(userNo, experimentalExecutionId);
			map.put("msg", "预定成功,请等候管理员审核");
			return map;
		} else {
			map.put("msg", "您的预定已通过或正在审核,请不要重复提交");
			return map;
		}

	}

	@RequestMapping(value = "list")
	public String requestList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Model model, @RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		String userNo = session.getAttribute("userNo").toString();
		PageInfo<ExperimentRequestDetail> pageInfo = new PageInfo<ExperimentRequestDetail>();
		pageInfo = bookingService.getExperimentExecutionRequestionsByUserNo(userNo, pageNum, pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "indexPages/requestList";

	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String toAddPages(HttpServletRequest request) {
		Map<Integer, String> map = new TreeMap<Integer, String>();
		map = experimentService.getAllExperimentIdAndName();
		request.setAttribute("map", map);
		return "indexPages/addExperimentalExecution";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addExperimentExecution(HttpServletRequest request, HttpServletRequest response,
			@RequestParam(value = "id") int experimentId, @RequestParam(value = "number") int number,
			@RequestParam(value = "startTime") String startTimeString,
			@RequestParam(value = "deadline") String deadlineString) {
		try {
			Date startTime = TimeUtil.changeStringToDate(startTimeString, "yyyy-MM-dd HH:mm");
			Date deadline = TimeUtil.changeStringToDate(deadlineString, "yyyy-MM-dd");
			if (bookingService.insertOneExperimenaltExecution(experimentId, startTime, deadline, number)) {
				request.setAttribute("msg", "添加成功");
				return "indexPages/succ";
			} else {
				request.setAttribute("msg", "添加失败");
				return "indexPages/err";
			}

		} catch (ParseException e) {
			System.out.println(e.getMessage());
			request.setAttribute("msg", e + "添加失败");
			return "indexPages/Err";
		}

	}

	@RequestMapping(value = "waitedRequestList", method = RequestMethod.GET)
	public String getWaitedRequestion(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		PageInfo<ExperimentRequestDetail> pageInfo = new PageInfo<ExperimentRequestDetail>();
		pageInfo = bookingService.getAllWaitedExperimentRequestDetail(pageNum, pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "indexPages/waitedRequestList";
	}

	@RequestMapping(value = "alreadyRequestList", method = RequestMethod.GET)
	public String getAlreadyRequestion(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		PageInfo<ExperimentRequestDetail> pageInfo = new PageInfo<ExperimentRequestDetail>();
		pageInfo = bookingService.getAllAlreadyExperimentRequestDetail(pageNum, pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "indexPages/alreayRequestList";
	}

	@RequestMapping(value = "examineWaitedRequestList", method = RequestMethod.POST)
	public String examineWaitedRequestList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "requestId") int requestId, @RequestParam(value = "status") int status,
			@RequestParam(value = "remark") String remark) {
		if (bookingService.updateOneExperimentExecutionRequestion(requestId, status, remark)) {
			return "redirect:/booking/waitedRequestList";
		} else {
			request.setAttribute("msg", "审核表单提交工程中出现问题");
			return "indexPages/err";
		}

	}

	/**
	 * 检查在相同一天中 该实验室是否为空
	 */
	@ResponseBody
	@RequestMapping(value = "ajax_checkLocationAndTime" ,method = RequestMethod.POST)
	public Map<String, Object> checkLocationAndTime(@RequestParam(value = "location") String location,
			@RequestParam(value = "startTime") String startTimeString) {
		Date startTime;
		try {
			startTime = TimeUtil.changeStringToDate(startTimeString, "yyyy-MM-dd HH:mm");
			Map<String, Object> map = new HashMap<String, Object>();
			if (bookingService.checkLocationAndStartTime(location, startTime)) {
				map.put("isPass", true);
				return map;
			} else {
				map.put("isPass", false);
				return map;
			}
		} catch (ParseException e) {
		   System.out.println(e.getMessage());
		   throw new CommonException(e.getMessage());
		}


	}
}
