package com.douzone.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileuploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private FileuploadService fileuploadService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping({"","/{category_no}","/{category_no}/{post_no}"})
	public String index(Model model, 
		@PathVariable("id") String id,
		@PathVariable("category_no") Optional<Long> category_no,
		@PathVariable("post_no") Optional<Long> post_no) {

		Long categoryNo = 0L;
		Long postNo = 0L;

		if(post_no.isPresent() ) {
			categoryNo = category_no.get();
			postNo=post_no.get();
		} else if(category_no.isPresent()) {
			categoryNo = category_no.get();
		}
		Map<String, Object> id_cd = Map.of("id",id,"categoryNo",categoryNo);
		List<PostVo> postList = postService.getPostByCate(id_cd);
		
		PostVo postVo;
		if(postNo==0) {
			if(postList.size()!=0) {
				postVo = postList.get(0);
			} else {
				postVo = new PostVo();
				postVo.setTitle("글이 없습니다.");
				postVo.setContents("글의 contents가 없습니다.");
			}
		} else {
			postVo = postService.getPost(postNo);
		}
		if(postVo==null) {
			}
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categoryList = categoryService.getCategory(id);
		servletContext.setAttribute("id", id);
		servletContext.setAttribute("blogVo", blogVo);
		model.addAttribute("postList", postList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postVo",postVo);

		return "blog/main";
	}
	@RequestMapping("/admin")
	public String admin(@PathVariable String id,Model model) {
		BlogVo blogVo = blogService.getBlog(id);
		model.addAttribute("blogVo", blogVo);
		return "blog/admin-basic";
	}
	
	@RequestMapping(value="/admin/update")
	public String update(
			@PathVariable String id,
			BlogVo blogVo,
			MultipartFile file,
			Model model) {
		String url = fileuploadService.restore(file);
		if(url != null) {
			blogVo.setProfile(url);
		}
		blogVo.setId(id);
		blogService.updateBlog(blogVo);
		servletContext.setAttribute("blogVo", blogVo);
		return "redirect:/"+id+"/admin";
	}
	
	@RequestMapping("/admin/category")
	public String category(@PathVariable String id,Model model) {
		List<CategoryVo> list = categoryService.getCategory(id);
		model.addAttribute("list",list);
		return "blog/admin-category";
	}
	
	@RequestMapping({"/admin/category/delete/{no}","/admin/category/delete/{no}/{message}"})
	public String delete(
			@PathVariable String id,
			@PathVariable("no") Long no,
			Model model,
			@PathVariable("message") Optional<String> message) {
		String msg="";
		if(message.isPresent()) {
			msg=message.get();
		}
		Map<String, Object> id_cd = Map.of("id",id,"categoryNo",no);
		System.out.println("msg : "+msg);
		if(postService.checkCategory(id_cd)) {
			System.out.println(1);
			msg="Post 중 해당 카테고리가 있습니다.";
			model.addAttribute("msg", msg);
//			return "blog/admin-category";
			return "redirect:/"+id+"/admin/category";
		}
		if(categoryService.getCount(id)==1) {
			System.out.println(2);
			msg = "마지막 카테고리 입니다.";
			model.addAttribute("msg", msg);
//			return "blog/admin-category";	
			return "redirect:/"+id+"/admin/category";
		}
		System.out.println(3);
		categoryService.removeCategory(no);
		return "redirect:/"+id+"/admin/category";
	}
	
	@RequestMapping("/admin/category/insert")
	public String categoryInsert(@PathVariable String id,
			CategoryVo categoryVo,
			Model model) {
		System.out.println("id : "+id);
		System.out.println("categoryVo : "+categoryVo);
		categoryVo.setId(id);
		categoryService.addCategory(categoryVo);
		return "redirect:/"+id+"/admin/category";
	}
	
	@RequestMapping("/admin/write")
	public String write(@PathVariable String id,Model model) {
		List<CategoryVo> list = categoryService.getCategory(id);
		model.addAttribute("list", list);
		System.out.println("list : "+list);
		return "blog/admin-write";
	}
	@RequestMapping("/admin/write/insert")
	public String write(
			@PathVariable String id,
			PostVo postVo,
			Model model) {
		postService.addPost(postVo);
		return "redirect:/"+id+"/admin/write";
	}
}