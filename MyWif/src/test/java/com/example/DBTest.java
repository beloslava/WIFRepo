package com.example;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.mywif.model.db.AlbumDAO;
import com.mywif.model.db.CommentDAO;
import com.mywif.model.db.DBManager;
import com.mywif.model.db.PostDAO;
import com.mywif.model.db.UserDAO;
import com.mywif.model.pojo.User;

public class DBTest {

	@Test
	public void dbConnectionTest(){
		assertNotNull(DBManager.getInstance().getConnection());
	}
	
	@Test
	public void getAllCommentsFromBDTest(){		
		assertNotNull(CommentDAO.getInstance().getAllComments());
	}
	
	@Test
	public void getAllPostsFromDBTest(){
		assertNotNull(PostDAO.getInstance().takeAllPosts());
	}
	
	@Test
	public void getAllAlbumsFromDBTest(){
		assertNotNull(AlbumDAO.getInstance().getAllAlbums());
	}
	
	@Test
	public void getAllUsersFromDBTest(){
		List<User> users = UserDAO.getInstance().getAllUsers();
		assertNotNull(users);
	}
}
