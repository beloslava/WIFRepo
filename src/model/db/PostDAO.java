package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import model.pojo.Post;
import model.pojo.User;
import model.pojo.UsersManager;

public class PostDAO implements IPostDAO {

	private static final String INSERT_INTO_POSTS = "INSERT INTO posts (user_email, tag_name, picture, post_like, post_dislike) VALUES (?,?,?,?,?);";
	private static final String DELETE_POST = "DELETE FROM posts WHERE post_id = ?;";
	private static final String LIKE_POST = "UPDATE posts SET post_like = post_like+1 WHERE post_id = ?;";
	private static final String DISLIKE_POST = "UPDATE posts SET post_dislike = post_dislike+1 WHERE post_id = ?;";
	private static final String SELECT_POSTS_BY_USER = "SELECT post_id, user_email, tag_name, picture, post_like, post_dislike, post_date FROM posts WHERE user_email = ? ORDER BY post_date DESC;";
	private static final String SELECT_POSTS_BY_TAG = "SELECT post_id, user_email, tag_name, picture, post_like, post_dislike, post_date FROM posts WHERE tag_name = ? ORDER BY post_date DESC;";
	private static final String SELECT_ALL_POSTS = "SELECT post_id, user_email, tag_name, picture, post_like, post_dislike, post_date FROM posts ORDER BY post_date DESC;";
	private static final String SELECT_TOP_TEN_POSTS = "SELECT post_id, user_email, tag_name, picture, post_like, post_dislike, post_date FROM posts ORDER BY post_like DESC LIMIT 10;";

	
	
	ConcurrentHashMap<Integer, Post> allPosts; // all posts from the page
	private static PostDAO instance;

	private PostDAO() {

		allPosts = new ConcurrentHashMap<Integer, Post>();

	}

	public synchronized static PostDAO getInstance() {
		if (instance == null) {
			instance = new PostDAO();
		}
		return instance;
	}

	public Map<Integer, Post> getAllPosts() {
		return Collections.unmodifiableMap(allPosts);
	}
	
	@Override
	public void addPost(String userEmail, String tag, String picture, int like, int dislike,
			Timestamp time) {

		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_INTO_POSTS,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, userEmail);
			statement.setString(2, tag);
			statement.setString(3, picture);
			statement.setInt(4, like);
			statement.setInt(5, dislike);
			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			Post post = new Post((int)id, userEmail, tag, picture, like, dislike, time);

			User user = UsersManager.getInstance().getUser(userEmail);
			user.getPosts().add(post);
		
		} catch (SQLException e) {
			System.out.println("Cannot upload post right now");
			e.printStackTrace();
		}

	}


	@Override
	public void editPost(Post post) {
		// TODO

	}

	@Override
	public void removePost(User user, Post post) {
//		Set<Post> posts = getAllPostsByUser(user);
		if (getAllPostsByUser(user).contains(post)) {
			getAllPostsByUser(user).remove(post);
//			posts.remove(post);
		}
		allPosts.remove(post);
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(DELETE_POST);
			statement.setInt(1, post.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("The post can not be delete right now");
			e.printStackTrace();
		}

	}

	@Override
	public void likePost(Post post) {
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(LIKE_POST);
			statement.setInt(1, post.getId());
		} catch (SQLException e) {
			System.out.println("The post cannot be liked right now");
			e.printStackTrace();
		}

	}

	@Override
	public void dislikePost(Post post) {
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(DISLIKE_POST);
			statement.setInt(1, post.getId());
		} catch (SQLException e) {
			System.out.println("The post cannot be disliked right now");
			e.printStackTrace();
		}

	}

	@Override
	public Set<Post> getAllPostsByUser(User user) {

		TreeSet<Post> postsByUser = new TreeSet<>();
		Statement st;
		try {
			st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery(SELECT_POSTS_BY_USER);
			while (resultSet.next()) {
				postsByUser.add(new Post(resultSet.getInt("post_id"), resultSet.getString("user_email"),
						resultSet.getString("tag_name"), resultSet.getString("picture"), resultSet.getInt("post_like"),
						resultSet.getInt("post_dislike"), resultSet.getTimestamp("post_date")

				));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get user's posts right now");
			e.printStackTrace();
			return postsByUser;

		}

		return postsByUser;
	}

	@Override
	public Set<Post> getAllPostsByTag(String tag) {
		TreeSet<Post> postsByTag = new TreeSet<>();
		Statement st;
		try {
			st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery(SELECT_POSTS_BY_TAG);
			while (resultSet.next()) {
				postsByTag.add(new Post(resultSet.getInt("post_id"), resultSet.getString("user_email"),
						resultSet.getString("tag_name"), resultSet.getString("picture"), resultSet.getInt("post_like"),
						resultSet.getInt("post_dislike"), resultSet.getTimestamp("post_date")

				));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get posts right now");
			e.printStackTrace();
			return postsByTag;

		}
		return postsByTag;
	}
	
	public Set<Post> getTopTenPosts(){
		TreeSet<Post> topTen = new TreeSet<>();
		Statement st;
		try {
			st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery(SELECT_TOP_TEN_POSTS);
			while (resultSet.next()) {
				topTen.add(new Post(resultSet.getInt("post_id"), 
						resultSet.getString("user_email"),
						resultSet.getString("tag_name"),
						resultSet.getString("picture"), 
						resultSet.getInt("post_like"),
						resultSet.getInt("post_dislike"),
						resultSet.getTimestamp("post_date")

				));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get top ten posts right now");
			e.printStackTrace();
			return topTen;

		}
		return topTen;
	}

}
