package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.pojo.User;

public class UserDAO implements IUserDAO {
	// email, password, name, avatarPath, age, gender, personalDescription

	private static final String SELECT_ALL_USERS = "SELECT email, user_password, user_name,  age, gender, about, avatar FROM users;";
	private static final String INSERT_INTO_USERS = "INSERT INTO users (email, user_password, user_name,  age, gender, about, avatar) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_USER = "UPDATE users SET email = ?, user_password = ?, user_name = ?, age = ?, gender = ?, about = ?, avatar = ?  WHERE email = ?;";

	private static UserDAO instance;

	private UserDAO() {
	}

	public synchronized static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	@Override
	public Set<User> getAllUsers() {
		Set<User> users = new HashSet<User>();
		try {
			Statement st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery(SELECT_ALL_USERS);
			while (resultSet.next()) {
				users.add(new User( resultSet.getString("email"), 
									resultSet.getString("user_password"),
									resultSet.getString("user_name"),
									resultSet.getInt("age"),
									resultSet.getString("gender"), 
									resultSet.getString("about"),
									resultSet.getString("avatar")

				));
			}
		} catch (SQLException e) {
			System.out.println("Cannot get all users right now!");
			return users;
		}
		System.out.println("Users loaded successfully");
		return users;
	}

	@Override
	public void saveUser(User user) {
		try {

			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(INSERT_INTO_USERS);
			st.setString(1, user.getEmail());
			st.setString(2, user.getPassword());
			st.setString(3, user.getName());
			st.setInt(4, user.getAge());
			st.setString(5, user.getGender());
			st.setString(6, user.getAbout());
			st.setString(6, user.getAvatarPath());

			st.executeUpdate();
			System.out.println("User added successfully");
		} catch (SQLException e) {
			System.out.println("Cannot save user right now!");
			e.printStackTrace();
		}

	}

	
	@Override
	public void updateUser(User user) {
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection()
					.prepareStatement(UPDATE_USER);
			//email = ?, user_password = ?, user_name = ?, age = ?, gender = ?, about = ?, avatar = ?
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getAge());
			statement.setString(4, user.getGender());
			statement.setString(5, user.getAbout());
			statement.setString(5, user.getAvatarPath());
			statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Can not updadte user right now");
			e.printStackTrace();
		}
		
	}

}
