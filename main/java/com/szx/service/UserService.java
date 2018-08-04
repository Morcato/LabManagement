package com.szx.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szx.dao.impl.UserMapper;
import com.szx.exception.CommonException;
import com.szx.po.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public String checkLogin(String userNo, String password, int rank) {
		try {
			User user = null;
			user = userMapper.selectByPrimaryKey(userNo);
			if (user == null) {
				return null;
			} else if (!user.getPassword().equals(password) || !user.getRank().equals(rank)) {
				return null;
			} else {
				return user.getUsername();
			}
		} catch (Exception e) {
			throw new CommonException(e.getMessage());
		}

	}
	/**
	 * 
	 * @param userNo
	 * @return 
	 * 返回值为true 代表  数据库中无重复userNo
	 * 返回值为false 代表  数据库中有重复userNo
	 */
	public boolean checkUserNo(String userNo) {
		User user = null;
		user = userMapper.selectByPrimaryKey(userNo);
		if(user == null) {
			return true;
		}else {
			return false;
		}
	}

	public PageInfo<User> getAllUser(Integer PageNum, Integer PageSize) {
		int pageNum;
		int pageSize;
		if (PageNum == null) {
			pageNum = 1;
		} else {
			pageNum = PageNum;
		}
		if (PageSize == null) {
			pageSize = 5;
		} else {
			pageSize = PageSize;
		}
		List<User> users = new ArrayList<User>();
		PageHelper.startPage(pageNum, pageSize, true);
		users = userMapper.selectAll();
		PageInfo<User> pageInfo = new PageInfo<User>(users);
		return pageInfo;
	}

	@Transactional
	public boolean updateUser(String userNo, String userName, String college, String password, int rank) {
		User user = new User();
		user.setUserno(userNo);
		user.setUsername(userName);
		user.setRank(rank);
		user.setPassword(password);
		user.setCollege(college);
		if (userMapper.updateByPrimaryKeySelective(user) == 1) {
			return true;
		} else {
			throw new CommonException("修改用户信息时出错!");
		}
	}
	@Transactional
	public boolean InsertOneUser(String userNo, String userName, String college, String password, int rank) {
		if(this.checkUserNo(userNo)) {
			User user = new User();
			user.setUserno(userNo);
			user.setUsername(userName);
			user.setRank(rank);
			user.setPassword(password);
			user.setCollege(college);

			if (userMapper.insertSelective(user)==1) {
				return true;
			} else {
				throw new CommonException("添加用户信息时出错!");
			}			
		}else {
			return false;
		}
		
	}
	@Transactional
	public boolean deleteOneUser(String userNo) {
		if(userMapper.deleteByPrimaryKey(userNo)==1) {
			return true;
		}else {
			return false;
		}
	}
}
