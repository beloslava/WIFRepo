package model.pojo;

import java.util.TreeSet;


public interface IUserManager {

	void changeSettings(String email, String password, String name, int age, String gender,
			String about, String avatarPath);
	
	void regUser(String email, String password, String name, int age, String gender,
			String personalDescription, String avatarPath, TreeSet<Post> posts);
	
	boolean validLogin(String email, String password);
	
	User getUser(String username);
}
