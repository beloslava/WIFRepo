package com.mywif.model.pojo;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mywif.model.exception.DBException;

public interface IUserManager {
	
	void changeSettings(String name, String password, String gender, String about, String email) throws UnsupportedEncodingException, DBException;
	
	void regUser(String email, String password, String name, String avatarPath,
			 Set<String> followers, Set<String> followed, Map<Integer, Album> albums) throws DBException;
	
	boolean validLogin(String email, String password) throws UnsupportedEncodingException;
	
	User getUser(String username);
	
	boolean isUserExists(String email);
	
	List<Searchable> searchUsersByName(String name);
}
