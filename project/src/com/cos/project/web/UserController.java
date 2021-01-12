package com.cos.project.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.project.domain.User;
import com.cos.project.domain.dto.CommonRespDto;
import com.cos.project.domain.dto.JoinReqDto;
import com.cos.project.domain.dto.LoginReqDto;
import com.cos.project.service.UserService;
import com.cos.project.util.Script;
import com.google.gson.Gson;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 public UserController() {
	        super();
	    }
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		UserService userService = new UserService();
		// http://localhost:8000/project/board?cmd=loginForm
		if(cmd.equals("loginForm")) {
			RequestDispatcher dis=request.getRequestDispatcher("user/login.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("joinForm")) {
			RequestDispatcher dis=request.getRequestDispatcher("user/join.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("login")) {
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			
			LoginReqDto dto=new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			
			User userEntity=userService.login(dto);
			
			if(userEntity != null) {
				HttpSession session = request.getSession();
				session.setAttribute("principal", userEntity); // 인증주체
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response, "로그인실패");
			}
		}else if(cmd.equals("join")) {
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			String role=request.getParameter("role");
			
			JoinReqDto dto=new JoinReqDto();
			
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			dto.setRole(role);
			
			System.out.println(dto);
			
			int result=userService.join(dto);
			
			if(result == 1) {
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response,"회원가입 실패");
			}
		}else if(cmd.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = userService.유저리스트삭제(id);

			CommonRespDto commonDto = new CommonRespDto<>();
			commonDto.setStatusCode(result);  //1, -1

			Gson gson = new Gson();
			String jsonData = gson.toJson(commonDto);
			// { "statusCode" : 1 }
			Script.responseData(response, jsonData);
		}else if(cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
	}
}
