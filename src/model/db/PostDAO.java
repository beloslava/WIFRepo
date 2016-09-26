package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.pojo.Comment;
import model.pojo.Post;
import model.pojo.User;
import model.pojo.UsersManager;

public class PostDAO implements IPostDAO {

	private static final String INSERT_INTO_POSTS = "INSERT INTO posts (user_email, album_id, category_name, picture, post_name, key_words) VALUES (?,?,?,?,?,?);";
	private static final String DELETE_POST = "DELETE FROM posts WHERE post_id = ?;";
	private static final String SELECT_ALL_POSTS = "SELECT post_id, user_email, album_id, category_name, picture, post_name, key_words, post_date FROM posts ORDER BY post_date DESC;";
	private static final String LIKE_POST = "INSERT INTO post_likes (comment_id, user_email) VALUES (?,?);";
	private static final String DISLIKE_POST = "INSERT INTO post_dislikes (comment_id, user_email) VALUES (?,?);";
	private static final String SELECT_LIKES_BY_POST = "SELECT user_email FROM post_likes WHERE post_id = ?;";
	private static final String SELECT_DISLIKES_BY_POST = "SELECT user_email FROM post_dislikes WHERE post_id = ?;;";
	
	TreeMap<Integer, Post> allPosts; // all posts from the page
	HashMap<Integer, ArrayList<String>> postLikes; // postId -> list from userEmails
	HashMap<Integer, ArrayList<String>> postDislikes; // postId -> list from userEmails


	private static PostDAO instance;

	private PostDAO() {

		allPosts = new TreeMap<Integer, Post>();
		allPosts = (TreeMap<Integer, Post>) takeAllPosts();

	}

	public synchronized static PostDAO getInstance() {
		if (instance == null) {
			instance = new PostDAO();
		}
		return instance;
	}

	public Post getPost(int postId) {
		return allPosts.get(postId);
	}

	@Override
	public Map<Integer, Post> getAllPosts() {
		return allPosts;
	}

	public Map<Integer, Post> takeAllPosts() {
		TreeMap<Integer, Post> allPosts = new TreeMap<Integer, Post>((postId1, postId2) -> postId2 - postId1);
		Statement st;
		try {
			st = DBManager.getInstance().getConnection().createStatement();
			// post_id, user_email, tag_name, picture, post_like, post_dislike,
			// post_date
			ResultSet resultSet = st.executeQuery(SELECT_ALL_POSTS);
			while (resultSet.next()) {
				List<Comment> postComments = (List<Comment>) CommentDAO.getInstance()
						.getAllCommentsByPost(resultSet.getInt("post_id"));
				Set<String> postLikes = getAllLikesForPost(resultSet.getInt("post_id"));
				Set<String> postDislikes = getAllDislikesForPost(resultSet.getInt("post_id"));

				allPosts.put(resultSet.getInt("post_id"), new Post( resultSet.getInt("post_id"), 
																	resultSet.getString("user_email"),
																	resultSet.getInt("album_id"),
																	resultSet.getString("category_name"),
																	resultSet.getString("picture"),
																	resultSet.getString("post_name"),
																	resultSet.getString("key_words"),
																	resultSet.getTimestamp("post_date"),
																	postComments

														));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get posts right now");
			e.printStackTrace();
			// return Collections.unmodifiableMap(allPosts);
			return (Map<Integer, Post>) allPosts.clone();

		}

		// return Collections.unmodifiableMap(allPosts);
		return (Map<Integer, Post>) allPosts.clone();

	}

	@Override
	public void addPost(String userEmail, int albumId, String category, String picture, String name, String keyWords,
			Timestamp time, List<Comment> comments) {
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_INTO_POSTS,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, userEmail);
			statement.setInt(2, albumId);
			statement.setString(3, category);
			statement.setString(4, picture);
			statement.setString(5, name);
			statement.setString(6, keyWords);
			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			Post post = new Post((int) id, userEmail, albumId, category, picture, name, keyWords, time, comments);

			User user = UsersManager.getInstance().getUser(userEmail);
			user.getPosts().add(post);
			allPosts.put(post.getId(), post);
		} catch (SQLException e) {
			System.out.println("Cannot upload post right now");
			e.printStackTrace();
		}

	}

	@Override
	public void removePost(String userEmail, Post post) {
		if (getAllPostsByUser(userEmail).contains(post)) {
			getAllPostsByUser(userEmail).remove(post);
			// user.getPosts().remove(post);
			allPosts.remove(post.getId());

			try {
				PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(DELETE_POST);
				statement.setInt(1, post.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("The post can not be delete right now");
				e.printStackTrace();
			}
		} else {
			System.out.println("There is no such post for this user");
		}

	}

	@Override
	public void likePost(int postId, String userEmail) {	
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(LIKE_POST);
			statement.setInt(1, postId);
			statement.setString(2, userEmail);
			statement.executeUpdate();
			allPosts.get(postId).getLikes().add(userEmail);
			System.out.println("like");
		} catch (SQLException e) {
			System.out.println("The post cannot be liked right now");
			e.printStackTrace();
		}

	}

	@Override
	public void dislikePost(int postId, String userEmail) {
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(DISLIKE_POST);
			statement.setInt(1, postId);
			statement.setString(2, userEmail);
			statement.executeUpdate();
			allPosts.get(postId).getDislikes().add(userEmail);
			System.out.println("like");
		} catch (SQLException e) {
			System.out.println("The post cannot be liked right now");
			e.printStackTrace();
		}
		
	}

	@Override
	public Set<String> getAllLikesForPost(int postId) {
		
		HashSet<String> likesByPost = new HashSet<>();
		
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_LIKES_BY_POST);
			statement.setInt(1, postId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				likesByPost.add(resultSet.getString("userEmail"));
			}
		} catch (SQLException e) {
			System.out.println("The likes for the post cannot be selected right now");
			e.printStackTrace();
		}
		
		return likesByPost;
	}

	@Override
	public Set<String> getAllDislikesForPost(int postId) {
		
		HashSet<String> dislikesByPost = new HashSet<>();
		
		try {
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_DISLIKES_BY_POST);
			statement.setInt(1, postId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				dislikesByPost.add(resultSet.getString("user_email"));
			}
		} catch (SQLException e) {
			System.out.println("The dislikes for the post cannot be selected right now");
			e.printStackTrace();
		}
		
		return dislikesByPost;
	}
	
	@Override
	public List<Post> getAllPostsByUser(String userEmail) {

		List<Post> postsByUser = new ArrayList<>();

		for (Post post : allPosts.values()) {
			if (post.getUserEmail().equals(userEmail)) {
				postsByUser.add(post);
			}
		}

		return postsByUser;
	}

	@Override
	public List<Post> getAllPostsByCategory(String category) {

		List<Post> postsByTag = new ArrayList<>();

		for (Post post : allPosts.values()) {
			if (post.getCategory().equals(category)) {
				postsByTag.add(post);
			}
		}

		return postsByTag;

	}

	@Override
	public List<Post> getTopTenPosts() {
		List<Post> top = new ArrayList<>();
		List<Post> topTen = new ArrayList<>();

		for (Post p : allPosts.values()) {
			top.add(p);
		}

		Collections.sort(top, new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return o2.getLikes().size() - o1.getLikes().size();
			}
		});

		for (Post post : top) {
			topTen.add(post);
			if (topTen.size() == 10) {
				break;
			}
		}
		return topTen;
	}

}
