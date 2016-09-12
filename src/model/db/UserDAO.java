package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.exceptions.DBException;
import model.pojo.User;

public class UserDAO {
	//email, password, name, avatarPath, age, gender, personalDescription
	
private static final String SELECT_ALL_USERS = "SELECT email, user_password, user_name, avatar, age, gender, about FROM users;";
private static final String INSERT_INTO_USERS = "INSERT INTO users (email, user_password, user_name, avatar, age, gender, about) VALUES (?, ?, ?, ?, ?, ?, ?);";
private static final String UPDATE_USER_PASSWORD = "UPDATE users SET user_password = ? WHERE email = ?;"; 
private static final String UPDATE_USER_NAME = "UPDATE users SET user_name = ? WHERE email = ?;"; 
private static final String UPDATE_USER_AVATAR = "UPDATE users SET avarar = ? WHERE email = ?;"; 
private static final String UPDATE_USER_AGE = "UPDATE users SET age = ? WHERE email = ?;"; 
private static final String UPDATE_USER_PERSONAL_DESCRIPTION = "UPDATE users SET about = ? WHERE email = ?;"; 



private static UserDAO instance;
	
	private UserDAO(){}
	
	public synchronized static UserDAO getInstance(){
		if(instance == null){
			instance = new UserDAO();
		}
		return instance;
	}
	
	public Set<User> getAllUsers(){
		Set<User> users = new HashSet<User>();
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery(SELECT_ALL_USERS);
			while(resultSet.next()){
				
				users.add(new User(	
									resultSet.getString("email"),
									resultSet.getString("user_password"),
									resultSet.getString("user_name"),
									resultSet.getString("avatar"),
									resultSet.getInt("age"), 
									resultSet.getString("gender"),
									resultSet.getString("about")
									
									
									));
			}
		} catch (SQLException e) {
			System.out.println("Cannot get all users right now!");
			return users;
		}
		System.out.println("Users loaded successfully");
		return users;
	}
	
	public void saveUser(User user){
		try {
			
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(INSERT_INTO_USERS);
			st.setString(2, user.getEmail());
			st.setString(4, user.getPassword());
			st.setString(3, user.getName());
			st.setString(1, user.getAvatarPath());
			st.setInt(6, user.getAge());
			st.setString(5, user.getGender());
			st.setString(7, user.getPersonalDescription());

			st.executeUpdate();
			System.out.println("User added successfully");
		} catch (SQLException e) {
			System.out.println("Cannot save user right now!");
			e.printStackTrace();
		}
		
	}
	
	public void updateUserPassword(String email, String password) {
		try {
			
			PreparedStatement statement = DBManager.getInstance().getConnection()
					.prepareStatement(UPDATE_USER_PASSWORD);
			statement.setString(1, email);
			statement.setString(2, password);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Cannot update password right now!");
			e.printStackTrace();
		}
		
	}
	
	public void updateUserName(String email, String name) {
		try {
			
			PreparedStatement statement = DBManager.getInstance().getConnection()
					.prepareStatement(UPDATE_USER_NAME);
			statement.setString(1, email);
			statement.setString(2, name);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Cannot update name right now!");
			e.printStackTrace();
		}
		
	}
	
	public void updateUserAvatar(String email, String avatarPath) {
		try {
		
			PreparedStatement statement = DBManager.getInstance().getConnection()
					.prepareStatement(UPDATE_USER_AVATAR);
			statement.setString(1, email);
			statement.setString(2, avatarPath);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Cannot update avatar right now!");
			e.printStackTrace();
		}
		
	}
	
	public void updateUserAge(String email, int age) {
		try {
			
			PreparedStatement statement = DBManager.getInstance().getConnection()
					.prepareStatement(UPDATE_USER_AGE);
			statement.setString(1, email);
			statement.setInt(2, age);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Cannot update age right now!");
			e.printStackTrace();
		}
		
	}
	
	public void updateUserPersonalDescription(String email, String desc) {
		try {
			
			PreparedStatement statement = DBManager.getInstance().getConnection()
					.prepareStatement(UPDATE_USER_PERSONAL_DESCRIPTION);
			statement.setString(1, email);
			statement.setString(1, desc);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Cannot update personal description right now!");
			e.printStackTrace();
		}
		
	}
	
	
	
}
