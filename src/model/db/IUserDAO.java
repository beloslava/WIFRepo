package model.db;

import java.util.Set;

import model.pojo.User;

public interface IUserDAO {

	Set<User> getAllUsers();

	void saveUser(User user);

	void updateUserPassword(String email, String password);

	void updateUserName(String email, String name);

	void updateUserAvatar(String email, String avatarPath);

	void updateUserAge(String email, int age);

	void updateUserPersonalDescription(String email, String desc);

}
