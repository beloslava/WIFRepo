package model.pojo;

public interface IUserManager {

	void changeSettings(String email, String password, String name, int age, String gender,
			String about);
	
	void regUser(String email, String password, String name, int age, String gender,
			String personalDescription);
	
	boolean validLogin(String email, String password);
	
}
