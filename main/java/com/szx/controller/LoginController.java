package com.szx.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szx.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String Tologin(Model model) {
		return "loginPages/login";
	}

	@ResponseBody
	@RequestMapping(value = "ajax_login", method = RequestMethod.POST)
	public Map<String, Object> ajax_checkLogin(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "userNo") String userNo,
			@RequestParam(value = "password") String passWord, @RequestParam(value = "rank") int rank) {
		String userName = userService.checkLogin(userNo, passWord, rank);
		Map<String, Object> map = new HashMap<String, Object>();
		if (userName == null) {
			model.addAttribute("Error", "用户名或密码错误");
			map.put("isLogin", false);
			return map;
		} else {
			session.setAttribute("userName", userName);
			session.setAttribute("userNo", userNo);
			session.setAttribute("rank", rank);
			map.put("isLogin", true);
			return map;
		}
	}

	@RequestMapping(value = "logOut", method = RequestMethod.GET)
	public String logOut(HttpSession session) {
		session.removeAttribute("userName");
		session.removeAttribute("userNo");
		session.removeAttribute("rank");
		return "loginPages/login";
	}

}
