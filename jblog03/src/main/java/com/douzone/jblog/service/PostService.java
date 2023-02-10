package com.douzone.jblog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	public void addPost(PostVo postVo) {
		postRepository.insert(postVo);
	}
	public boolean checkCategory(Map<String,Object> map) {
		return !postRepository.findCategory(map).isEmpty()?true:false;
	}
	public List<PostVo> getAllPost(String id) {
		return postRepository.findAllCategory(id);
	}
	public PostVo getPost(Long postNo) {
		return postRepository.findPost(postNo);
	}
	public List<PostVo> getPostByCate(Map<String,Object> map) {
		List<PostVo> list = postRepository.findCategory(map);
		System.out.println("getPostByCate - list : "+list);
		if(list == null) {
			list = new ArrayList<PostVo>();
			PostVo postVo = new PostVo();
			postVo.setTitle("글이 없습니다.");
			postVo.setContents("글의 contents가 없습니다.");
			list.add(postVo);
		}
		System.out.println("list : "+list);
		return list;
	}
}