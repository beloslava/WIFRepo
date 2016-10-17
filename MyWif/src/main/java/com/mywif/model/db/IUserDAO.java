package com.mywif.model.db;

import java.util.List;

import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.User;

public interface IUserDAO {

	List<User> getAllUsers();

	void saveUser(User user) throws DBException;

	void updateUser(User user) throws DBException;

}
