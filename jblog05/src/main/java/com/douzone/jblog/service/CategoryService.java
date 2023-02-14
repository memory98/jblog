package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public void addCategory(CategoryVo categoryVo) {
		categoryRepository.insert(categoryVo);
	}
	
	public List<CategoryVo> getCategory(String id) {
		return categoryRepository.findById(id);
	}

	public void removeCategory(Long no) {
		categoryRepository.deleteNo(no);
	}

	public int getCount(String id) {
		return categoryRepository.getCount(id);
	}
}