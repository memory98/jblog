package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	public void insert(String id) {
		BlogVo blogVo = new BlogVo();
		blogVo.setTitle(id+"의 블로그");
		blogVo.setProfile("profile");
		blogVo.setId(id);
		sqlSession.selectOne("blog.insert",blogVo);
	}
	public BlogVo findById(String id) {
		return sqlSession.selectOne("blog.findById",id);
	}
	public void update(BlogVo blogVo) {
		sqlSession.selectOne("blog.update",blogVo);
	}
}