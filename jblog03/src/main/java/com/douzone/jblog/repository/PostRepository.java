package com.douzone.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {
	
	@Autowired
	private SqlSession sqlSession;
	public void insert(PostVo postVo) {
		sqlSession.selectOne("post.insert",postVo);
	}
	public PostVo findPost(Long postNo) {
		return sqlSession.selectOne("post.findPost",postNo);
	}
	public List<PostVo> findCategory(Map<String,Object> map) {
		Long no = (Long) map.get("categoryNo");
		String id = (String)map.get("id");
		if(no==0) {
			no=sqlSession.selectOne("post.maxCategory_No",id);
		}
		if(no==null) {
			return null;
		}
		Map<String,Object> m = Map.of("id",id,"categoryNo",no);
		return sqlSession.selectList("post.findCategory",m);
	}
	public List<PostVo> findAllCategory(String id) {
		return sqlSession.selectList("post.finddAllCatgory",id);
	}
}