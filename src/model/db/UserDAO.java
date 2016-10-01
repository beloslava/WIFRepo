package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import model.pojo.Album;
import model.pojo.Post;
import model.pojo.User;

public class UserDAO implements IUserDAO {
	// email, password, name, gender, about, avatarPath

	private static final String SELECT_ALL_USERS = "SELECT email, user_password, user_name, gender, about, avatar FROM users;";
	private static final String INSERT_INTO_USERS = "INSERT INTO users (email, user_password, user_name, gender, about, avatar) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_USER = "UPDATE users SET user_name = ?, user_password = ?,  gender = ?, about = ?  WHERE email = ?;";
	private static final String UPDATE_AVATAR = "UPDATE users SET avatar = ? WHERE email = ?;";
	private static final String SELECT_FOLLOWERS = "SELECT follower_email FROM followers WHERE user_email = ?;";
	private static final String SELECT_FOLLOWED = "SELECT user_email FROM followers WHERE follower_email = ?;";
	private static final String INSERT_FOLLOWER = "INSERT INTO followers (user_email, follower_email) VALUES (?,?);";

	private static UserDAO instance;

	private UserDAO() {
	}

	public synchronized static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	/**
	 * get all users from db
	 * generate posts from db
	 * @return list with all users in db
	 */
	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().createStatement();
			resultSet = statement.executeQuery(SELECT_ALL_USERS);
			while (resultSet.next()) {
				List<Post> posts = (List<Post>) PostDAO.getInstance().getAllPostsByUser(resultSet.getString("email"));
				Set<String> followers = getAllFollowersForUser(resultSet.getString("email"));
				Set<String> followed = getAllFollowedForUser(resultSet.getString("email"));
				TreeMap<Integer, Album> albums = AlbumDAO.getInstance().getAllAlbumsByUser(resultSet.getString("email"));
				users.add(new User( resultSet.getString("email"), 
									resultSet.getString("user_password"),
									resultSet.getString("user_name"),
									resultSet.getString("gender"),
									resultSet.getString("about"),
									resultSet.getString("avatar"),
									posts, 
									followers,
									followed,
									albums
			
							));
			}
		} catch (SQLException e) {
			System.out.println("Cannot get all users right now!");
			return users;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Users loaded successfully");
		//System.out.println("Users " + users.size());
		return users;
	}
	
	/**
	 * save user in db
	 */
	@Override
	public void saveUser(User user) {
		PreparedStatement statement = null;
		try {
			// email, user_password, user_name, gender, about, avatar
			statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_INTO_USERS);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getName());
			statement.setString(4, user.getGender());
			statement.setString(5, user.getAbout());
			statement.setString(6, user.getAvatarPath());
				
			statement.executeUpdate();
				
			System.out.println("User added successfully");
		} catch (SQLException e) {
			System.out.println("Cannot save user right now!");
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * update user in db
	 */
	@Override
	public void updateUser(User user) {
		PreparedStatement statement = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(UPDATE_USER);
			// user_name = ?, user_password = ?,  gender = ?, about = ?
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getGender());
			statement.setString(4, user.getAbout());
			statement.setString(5, user.getEmail());
			statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Can not updadte user right now");
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void updateAvatar(User user) {
		PreparedStatement statement = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(UPDATE_AVATAR);
			// avatar = ?, email = ?
			statement.setString(1, user.getAvatarPath());
			statement.setString(2, user.getEmail());
			statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Can not updadte avatar right now");
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	public void followUser(String userEmail, String followerEmail){
		PreparedStatement statement = null;
		try {
			// email, user_password, user_name, gender, about, avatar
			statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_FOLLOWER);
			statement.setString(1, userEmail);
			statement.setString(2, followerEmail);		
			statement.executeUpdate();
				
			System.out.println("User followed successfully");
		} catch (SQLException e) {
			System.out.println("Cannot follow user right now!");
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Set<String> getAllFollowersForUser(String userEmail){
		HashSet<String> followers = new HashSet<>();
		PreparedStatement statement = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_FOLLOWERS);
			statement.setString(1, userEmail);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				followers.add(resultSet.getString("follower_email"));
			}
		} catch (SQLException e) {
			System.out.println("Cannot get followers right now");
			e.printStackTrace();
			return followers;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return followers;

	}
	
	public Set<String> getAllFollowedForUser(String followerEmail){
		HashSet<String> followed = new HashSet<>();
		PreparedStatement statement = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_FOLLOWED);
			statement.setString(1, followerEmail);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				followed.add(resultSet.getString("user_email"));
			}
		} catch (SQLException e) {
			System.out.println("Cannot get followed right now");
			e.printStackTrace();
			return followed;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return followed;

	}

}
