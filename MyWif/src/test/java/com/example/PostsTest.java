package com.example;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mywif.model.db.PostDAO;

public class PostsTest {

	@Test
	public void getPostTest(){
		int postId = 19;
		assertNotNull(PostDAO.getInstance().getPost(postId));
	}
	
	
}
