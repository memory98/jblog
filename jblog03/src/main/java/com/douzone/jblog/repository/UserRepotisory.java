package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRepotisory {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(UserVo userVo) {
		sqlSession.selectOne("user.insert",userVo);
	}
	
	public UserVo findByIdAndPassword(UserVo userVo) {
		return sqlSession.selectOne("user.findByIdAndPassword",userVo);
	}
}