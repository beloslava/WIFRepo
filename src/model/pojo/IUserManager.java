package model.pojo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public interface IUserManager {
	
	void changeSettings(String name, String password, String gender, String about, String email) 
			throws UnsupportedEncodingException;
	
	void regUser(String email, String password, String name, String avatarPath,
			 Set<String> followers, Set<String> followed, Map<Integer, Album> albums);
	
	boolean validLogin(String email, String password) throws UnsupportedEncodingException;
	
	User getUser(String username);
	
	boolean isUserExists(String email);
	
	List<User> searchUsersByName(String name);
}
