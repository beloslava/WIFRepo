package model.pojo;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;


public interface IUserManager {
	
	void changeSettings(String name, String password, String gender, String about, String email) 
			throws UnsupportedEncodingException;
	
	void regUser(String email, String password, String name, String avatarPath, List<Post> posts, Set<String> followers, Set<String> followed);
	
	boolean validLogin(String email, String password) throws UnsupportedEncodingException;
	
	User getUser(String username);
	
	boolean isUserExists(String email);
	
	List<User> searchUsersByName(String name);
}
