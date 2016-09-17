package model.db;

import java.util.List;

import model.pojo.User;

public interface IUserDAO {

	List<User> getAllUsers();
	
	void saveUser(User user);

	void updateUser(User user);

}
