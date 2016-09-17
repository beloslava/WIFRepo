package model.pojo;

import java.io.UnsupportedEncodingException;
import java.util.List;


public interface IUserManager {

	void changeSettings(String email, String password, String name, int age, String gender,
			String about, String avatarPath) throws UnsupportedEncodingException;
	
	void regUser(String email, String password, String name, int age, String gender,
			String personalDescription, String avatarPath, List<Post> posts);
	
	boolean validLogin(String email, String password) throws UnsupportedEncodingException;
	
	User getUser(String username);
}
