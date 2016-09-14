package model.db;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import model.pojo.Post;
import model.pojo.User;

public class PostDAO implements IPostDAO {

	private static final String INSERT_INTO_POSTS = "INSERT INTO posts (user_email, tag_name, picture, post_like, post_dislike) VALUES (?,?,?,?,?);";
	private static final String DELETE_POST = "DELETE FROM posts WHERE post_id = ?;";
	private static final String LIKE_POST = "UPDATE posts SET post_like = post_like+1 WHERE post_id = ?;";
	private static final String DISLIKE_POST = "UPDATE posts SET post_dislike = post_dislike+1 WHERE post_id = ?;";
	private static final String SELECT_POSTS_BY_USER = "SELECT post_id, user_email, tag_name, picture, post_like, post_dislike, post_date FROM posts WHERE user_email = ?;";
	private static final String SELECT_POSTS_BY_TAG = "SELECT post_id, user_email, tag_name, picture, post_like, post_dislike, post_date FROM posts WHERE tag_name = ?;";

	ConcurrentHashMap<Integer, Post> allPosts; //all posts from the page
	private static PostDAO instance;
	
	private PostDAO() {
	}

	public synchronized static PostDAO getInstance() {
		if (instance == null) {
			instance = new PostDAO();
		}
		return instance;
	}
	
	@Override
	public void addPost(User user, Post post) {
		
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_INTO_POSTS,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getEmail());
			statement.setString(2, post.getTag());
			statement.setBinaryStream(3, post.getPicture());
			statement.setInt(4, post.getLike());
			statement.setInt(5, post.getDislike());
			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			post.setId((int)id);
			
			user.getPosts().put(post.getId(), post);
//			 Post p = new Post((int)id, user.getEmail(), post.getTag(),
//			 post.getPicture(), post.getLike(), post.getDislike(), new Timestamp(System.currentTimeMillis()));
		} catch (SQLException e) {
			System.out.println("Cannot upload post right now");
			e.printStackTrace();
		}

	}
	
	public void createPost(int id, String userEmail, String tag, InputStream picture, int like, int dislike,
			Timestamp time){
		Post post = new Post(id, userEmail, tag, picture, like, dislike, time);
		allPosts.put(id, post);
		//addPost(user, post);
	}

	@Override
	public void editPost(Post post) {
		// TODO

	}

	@Override
	public void removePost(User user, Post post) {
		Set<Post> posts = getAllPostsByUser(user);
		if (posts.contains(post)) {
			posts.remove(post);
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
		//TODO
		TreeSet<Post> postsByUser = new TreeSet<>();
		Statement st;
		try {
			st = DBManager.getInstance().getConnection().createStatement();
			ResultSet resultSet = st.executeQuery(SELECT_POSTS_BY_USER);
			while (resultSet.next()) {
//			postsByUser.add(new Post(
//					resultSet.getInt("post_id"),
//					resultSet.getString("user_email"),
//					resultSet.getString("tag_name")
//					resultSet.getBinaryStream("picture"),
//					resultSet.getInt("post_like"),
//					resultSet.getInt("post_dislike"),
//					resultSet.getTimestamp("post_date")
//					
//					));
					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Set<Post> getAllPostsByTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
