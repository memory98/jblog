package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	
	@Autowired 
	private SqlSession sqlSession;
	public List<CategoryVo> findById(String id) {
		return sqlSession.selectList("category.findById",id);
	}
	public void insert(CategoryVo categoryVo) {
		System.out.println("categoryVo : "+categoryVo);
		sqlSession.selectOne("category.insert",categoryVo);
	}
	public void deleteNo(Long no) {
		sqlSession.selectOne("category.deleteNo",no);
	}
	public int getCount(String id) {
		return sqlSession.selectOne("category.getCount",id);
	}
}