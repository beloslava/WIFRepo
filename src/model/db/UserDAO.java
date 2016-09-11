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

	
private static final String SELECT_ALL_USERS = "SELECT email, password, name, avatarPath, age, gender, personalDescription FROM users;";
private static final String INSERT_INTO_USERS = "INSERT INTO users (email, password, name, avatarPath, age, gender, personalDescription) VALUES (?, ?, ?, ?, ?, ?, ?);";

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
									resultSet.getString("password"),
									resultSet.getString("name"),
									resultSet.getString("avatarPath"),
									resultSet.getInt("age"), 
									resultSet.getString("gender"),
									resultSet.getString("personalDescription")

									
									
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
	
	
}
