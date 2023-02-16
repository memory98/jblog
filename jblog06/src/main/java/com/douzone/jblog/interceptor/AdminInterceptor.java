package com.douzone.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

public class AdminInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String[] id = request.getRequestURI().split("/");
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if(!userService.getId(id[2])) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		if(!id[2].equals(authUser.getId())) {
			response.sendRedirect(request.getContextPath()+"/"+id[2]);
			return false;
		}
		return true;
	}
}