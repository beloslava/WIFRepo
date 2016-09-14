package model.db;

import java.util.Set;

import model.pojo.User;

public interface IUserDAO {

	Set<User> getAllUsers();
	
	void saveUser(User user);

	void updateUser(User user);

}
