package com.cos.project.service;

import java.util.List;

import com.cos.project.domain.User;
import com.cos.project.domain.UserDao;

public class BoardService {
	
private UserDao userDao;
	
	public BoardService() {
		userDao = new UserDao();
	}
	public List<User> 글목록보기(){
		return userDao.findAll();
	}
	public int 글개수() {
		return userDao.count();
	}
}
