package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	public void addBlog(String id) {
		blogRepository.insert(id);
	}
	public BlogVo getBlog(String id) {
		return blogRepository.findById(id);
	}
	public void updateBlog(BlogVo blogVo) {
		blogRepository.update(blogVo);
	}
}