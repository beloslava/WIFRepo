package com.example;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.junit.Test;

import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.UsersManager;

public class UsersTest{
	
	@Test
	public void isUserExistTest() {
		String email = "beloslava_vg@abv.bg";
		boolean emailInCollection = UsersManager.getInstance().isUserExists(email);
		assertTrue(emailInCollection);
	}
	
	@Test
	public void validLoginTest(){
		String email = "beloslava_vg@abv.bg";
		String pass = "Beloslava1234";
		try {
			assertTrue(UsersManager.getInstance().validLogin(email, pass));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	@Test
	public void getUserTest(){
		String email = "beloslava_vg@abv.bg";
		UsersManager.getInstance().getUser(email);
		assertNotNull(UsersManager.getInstance().getUser(email));
	}
	
	@Test
	public void folowTest(){
		String follower = "toni@abv.bg";
		String followed = "beloslava_vg@abv.bg";
		try {
			UsersManager.getInstance().follow(followed, follower);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> followersUsers = UsersManager.getInstance().getFollowersByUser(followed);
		Set<String> followedUsers = UsersManager.getInstance().getFollowedByUser(follower);
		assertTrue(followersUsers.contains(follower));
		assertTrue(followedUsers.contains(followed));
	}

	@Test
	public void unfolowTest(){
		String follower = "toni@abv.bg";
		String followed = "beloslava_vg@abv.bg";
		try {
			UsersManager.getInstance().unfollow(followed, follower);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> followersUsers = UsersManager.getInstance().getFollowersByUser(followed);
		Set<String> followedUsers = UsersManager.getInstance().getFollowedByUser(follower);
		assertFalse(followersUsers.contains(follower));
		assertFalse(followedUsers.contains(followed));
	}
	
}
