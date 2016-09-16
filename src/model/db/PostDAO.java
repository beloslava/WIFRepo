package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import model.pojo.Post;
import model.pojo.User;
import model.pojo.UsersManager;

public class PostDAO implements IPostDAO {

	private static final String INSERT_INTO_POSTS = "INSERT INTO posts (user_email, tag_id, post_like, post_dislike,picture) VALUES (?,?,?,?,?);";
	private static final String DELETE_POST = "DELETE FROM posts WHERE post_id = ?;";
	private static final String LIKE_POST = "UPDATE posts SET post_like = post_like+1 WHERE post_id = ?;";
	private static final String DISLIKE_POST = "UPDATE posts SET post_dislike = post_dislike+1 WHERE post_id = ?;";
	private static final String SELECT_POSTS_BY_USER = "SELECT post_id, user_email, tag_id, picture, post_like, post_dislike, post_date FROM posts WHERE user_email = ? ORDER BY post_date DESC;";
	private static final String SELECT_POSTS_BY_TAG = "SELECT post_id, user_email, tag_id, picture, post_like, post_dislike, post_date FROM posts WHERE tag_id = ? ORDER BY post_date DESC;";
	private static final String SELECT_ALL_POSTS = "SELECT post_id, user_email, tag_id, picture, post_like, post_dislike, post_date FROM posts ORDER BY post_date DESC;";
	private static final String SELECT_TOP_TEN_POSTS = "SELECT post_id, user_email, tag_id, picture, post_like, post_dislike, post_date FROM posts ORDER BY post_like DESC LIMIT 10;";

	
	
	HashMap<Integer, Post> allPosts; // all posts from the page
	private static PostDAO instance;

	private PostDAO() {

		allPosts = new HashMap<Integer, Post>();
		allPosts = (HashMap<Integer, Post>) getAllPosts();

	}

	public synchronized static PostDAO getInstance() {
		if (instance == null) {
			instance = new PostDAO();
		}
		return instance;
	}

	
	public Post getPost(Post post){
		//TODO
		return post;
	}
	
	@Override
	public Map<Integer, Post> getAllPosts() {
		HashMap<Integer, Post> allPosts = new HashMap<Integer, Post>();
		Statement st;
		try {
			st = DBManager.getInstance().getConnection().createStatement();
			//post_id, user_email, tag_id, picture, post_like, post_dislike, post_date
			ResultSet resultSet = st.executeQuery(SELECT_ALL_POSTS);
			while (resultSet.next()) {
				allPosts.put(resultSet.getInt("post_id"), new Post( resultSet.getInt("post_id"),
																	resultSet.getString("user_email"),
																	resultSet.getString("tag_id"),
																	resultSet.getString("picture"),
																	resultSet.getInt("post_like"),
																	resultSet.getInt("post_dislike"),
																	resultSet.getTimestamp("post_date")

																  
						
						));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get posts right now");
			e.printStackTrace();
			//return Collections.unmodifiableMap(allPosts);
			return (Map<Integer, Post>) allPosts.clone();

		}
		
		//return Collections.unmodifiableMap(allPosts);
		return (Map<Integer, Post>) allPosts.clone();

		
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
	public void removePost(User user, Post post) {
		if (getAllPostsByUser(user).contains(post)) {
			getAllPostsByUser(user).remove(post);
			user.getPosts().remove(post);
			allPosts.remove(post.getId());
		
			try {
				PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(DELETE_POST);
				statement.setInt(1, post.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("The post can not be delete right now");
				e.printStackTrace();
			}
		}
		else{
			System.out.println("There is no such post for this user");
		}

	}

	@Override
	public void likePost(Post post) {
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(LIKE_POST);
			statement.setInt(1, post.getId());
			int like = post.getLike()+1;
			post.setLike(like);
			allPosts.put(post.getId(), post);
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
			int like = post.getDislike()+1;
			post.setDislike(like);
			allPosts.put(post.getId(), post);
		} catch (SQLException e) {
			System.out.println("The post cannot be disliked right now");
			e.printStackTrace();
		}

	}

	@Override
	public Set<Post> getAllPostsByUser(User user) {

		HashSet<Post> postsByUser = new HashSet<>();
		PreparedStatement statement;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_POSTS_BY_USER);
			statement.setString(1, user.getEmail());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				postsByUser.add(new Post(resultSet.getInt("post_id"),
										resultSet.getString("user_email"),
										resultSet.getString("tag_id"),
										resultSet.getString("picture"), 
										resultSet.getInt("post_like"),
										resultSet.getInt("post_dislike"), 
										resultSet.getTimestamp("post_date")

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
		HashSet<Post> postsByTag = new HashSet<>();
		PreparedStatement statement;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_POSTS_BY_TAG);
			statement.setString(1, tag);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				postsByTag.add(new Post(resultSet.getInt("post_id"), 
										resultSet.getString("user_email"),
										resultSet.getString("tag_id"), 
										resultSet.getString("picture"), 
										resultSet.getInt("post_like"),
										resultSet.getInt("post_dislike"), 
										resultSet.getTimestamp("post_date")

				));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get posts right now");
			e.printStackTrace();
			return postsByTag;

		}
		return postsByTag;
	}
	
	@Override
	public Set<Post> getTopTenPosts(){
		HashSet<Post> topTen = new HashSet<>();
		Statement st;
		try {
			st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery(SELECT_TOP_TEN_POSTS);
			while (resultSet.next()) {
				topTen.add(new Post(resultSet.getInt("post_id"), 
									resultSet.getString("user_email"),
									resultSet.getString("tag_id"),
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
