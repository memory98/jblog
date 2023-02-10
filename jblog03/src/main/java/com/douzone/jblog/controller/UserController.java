package com.douzone.jblog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
	@RequestMapping("/login/{id}")
	public String login(Model model,@PathVariable String id) {
		model.addAttribute("nowBlogId", id);
		return "user/login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpSession session, String nowblogid,UserVo userVo) {
		UserVo authUser = userService.getUser(userVo);
		if(authUser == null) {
			System.out.println("authUser : "+authUser);
			return "redirect:/user/login";
		}
		
		session.setAttribute("authUser", authUser);
		if(nowblogid != null) {
			
			return "redirect:/"+nowblogid;	
		}

		System.out.println("move blog ");
		return "redirect:/";
	}
	
	@RequestMapping("/join")
	public String join() {
		return "user/join";
	}
	@RequestMapping(value = "/join",method=RequestMethod.POST)
	public String join(UserVo userVo) {
		userService.join(userVo);
		blogService.addBlog(userVo.getId());
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setName("기타");
		categoryVo.setId(userVo.getId());
		categoryVo.setDescription("카테고리를 지정하지 않은 경우");
		categoryService.addCategory(categoryVo);
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
	@RequestMapping("/logout/{id}")
	public String logout(HttpSession session,@PathVariable String id) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/"+id;
	}
}