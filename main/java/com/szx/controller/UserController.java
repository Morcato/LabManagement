package com.szx.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.szx.po.User;
import com.szx.service.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "list")
	public String userList(Model model, @RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		PageInfo<User> pageInfo = new PageInfo<User>();
		pageInfo = userService.getAllUser(pageNum, pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "indexPages/userList";
	}

	@RequestMapping(value = "update")
	public String updateUser(HttpServletRequest request, Model model, @RequestParam(value = "userNo") String userNo,
			@RequestParam(value = "userName") String userName, @RequestParam(value = "college") String college,
			@RequestParam(value = "password") String password, @RequestParam(value = "rank") int rank) {
		if (userService.updateUser(userNo, userName, college, password, rank)) {
			return "redirect:/user/list";
		} else {
			request.setAttribute("msg", "修改信息时出错");
			return "indexPages/err";
		}

	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String toAddUserPage() {
		return "indexPages/addUser";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addUser(HttpServletRequest request, Model model, @RequestParam(value = "userNo") String userNo,
			@RequestParam(value = "userName") String userName, @RequestParam(value = "college") String college,
			@RequestParam(value = "password") String password, @RequestParam(value = "rank") int rank) {
		if (userService.InsertOneUser(userNo, userName, college, password, rank)) {
			request.setAttribute("msg", "添加用户成功");
			return "indexPages/succ";
		} else {
			request.setAttribute("msg", "添加用户时出错");
			return "indexPages/err";
		}
	}

	@ResponseBody
	@RequestMapping(value = "ajax_checkUserNo", method = RequestMethod.POST)
	public Map<String, Object> checkUserNo(@RequestParam(value = "userNo") String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userService.checkUserNo(userNo)) {
			map.put("isPass", true);
		} else {
			map.put("isPass", false);
		}
		return map;
	}
	
	@RequestMapping(value = "delete")
	public String deleteUser(@RequestParam(value = "userNo") String userNo,HttpServletRequest request) {
		if(userService.deleteOneUser(userNo)) {
			return "redirect:/user/list";
		}else {
			request.setAttribute("msg", "删除用户时出错");
			return "indexPages/err";
		}
	}
}
