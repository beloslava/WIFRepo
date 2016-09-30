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
	private static final String LIKE_POST = "INSERT INTO post_likes (post_id, user_email) VALUES (?,?);";
	private static final String DISLIKE_POST = "INSERT INTO post_dislikes (post_id, user_email) VALUES (?,?);";
	private static final String SELECT_LIKES_BY_POST = "SELECT user_email FROM post_likes WHERE post_id = ?;";
	private static final String SELECT_DISLIKES_BY_POST = "SELECT user_email FROM post_dislikes WHERE post_id = ?;";
	
	TreeMap<Integer, Post> allPosts; // all posts from the page
	HashMap<Integer, HashSet<String>> postLikes; // postId -> list from userEmails
	HashMap<Integer, HashSet<String>> postDislikes; // postId -> list from userEmails


	private static PostDAO instance;

	private PostDAO() {

		postLikes = new HashMap<>();
		postDislikes = new HashMap<>();
		allPosts = new TreeMap<Integer, Post>();
		allPosts = (TreeMap<Integer, Post>) takeAllPosts();
		
		for(Post post : allPosts.values()){
			int id = post.getId();
			postLikes.put(id, (HashSet<String>) getAllLikesForPost(id));
			postDislikes.put(id, (HashSet<String>) getAllDislikesForPost(id));

		}

	}

	public synchronized static PostDAO getInstance() {
		if (instance == null) {
			instance = new PostDAO();
		}
		return instance;
	}
	
	/**
	 * get post by id from allPosts collection
	 * @return post
	 */
	public Post getPost(int postId) {
		return allPosts.get(postId);
	}

	/**
	 * get all posts from allPosts collection
	 * @return map with postId -> post values
	 */
	@Override
	public Map<Integer, Post> getAllPosts() {
		return allPosts;
	}
	
	/**
	 * take all posts from db
	 * generate posts comments from db
	 * generate likes and dislikes for posts from db
	 * @return map with post id -> post values
	 */
	public Map<Integer, Post> takeAllPosts() {
		TreeMap<Integer, Post> allPosts = new TreeMap<Integer, Post>((postId1, postId2) -> postId2 - postId1);
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().createStatement();
			// post_id, user_email, tag_name, picture, post_like, post_dislike,
			// post_date
			resultSet = statement.executeQuery(SELECT_ALL_POSTS);
			while (resultSet.next()) {
				List<Comment> postComments = (List<Comment>) CommentDAO.getInstance()
						.getAllCommentsByPost(resultSet.getInt("post_id"));
				for(Comment c : postComments){
					List<Comment> commentComments = (List<Comment>) CommentDAO.getInstance()
							.getAllCommentsByComment(c.getCommentId());
					c.setCommentComments((ArrayList<Comment>) commentComments);
				}
				
				Set<String> postLikes = getAllLikesForPost(resultSet.getInt("post_id"));
				Set<String> postDislikes = getAllDislikesForPost(resultSet.getInt("post_id"));

				allPosts.put(resultSet.getInt("post_id"), new Post( resultSet.getInt("post_id"), 
																	resultSet.getString("user_email"),
																	(int)resultSet.getLong("album_id"),
																//	resultSet.getInt("album_id"),
																	resultSet.getString("category_name"),
																	resultSet.getString("picture"),
																	resultSet.getString("post_name"),
																	resultSet.getString("key_words"),
																	resultSet.getTimestamp("post_date"),
																	postComments, 
																	postLikes, 
																	postDislikes

														));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get posts right now");
			e.printStackTrace();
			// return Collections.unmodifiableMap(allPosts);
			return (Map<Integer, Post>) allPosts.clone();

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

		// return Collections.unmodifiableMap(allPosts);
		return (Map<Integer, Post>) allPosts.clone();

	}

	/**
	 * add post in db, allPosts collection and user's posts
	 */
	@Override
	public void addPost(String userEmail, Integer albumId, String category, String picture, String name, String keyWords,
			Timestamp time, List<Comment> comments, Set<String> likes, Set<String> dislikes) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_INTO_POSTS, 
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, userEmail);
			statement.setObject(2, albumId);
		//	statement.setNull(2, java.sql.Types.INTEGER);
			statement.setString(3, category);
			statement.setString(4, picture);
			statement.setString(5, name);
			statement.setString(6, keyWords);
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			long id = resultSet.getLong(1);
			
			Set<String> postLikes = getAllLikesForPost((int)id);
			Set<String> postDislikes = getAllDislikesForPost((int)id);
			
			Post post = new Post((int) id, userEmail, albumId, category, picture, name, keyWords, time, 
					comments, postLikes, postDislikes);

			User user = UsersManager.getInstance().getUser(userEmail);
			user.getPosts().add(post);
			allPosts.put(post.getId(), post);
		} catch (SQLException e) {
			System.out.println("Cannot upload post right now");
			e.printStackTrace();
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

	}

	@Override
	public void removePost(String userEmail, Post post) {
		//TODO
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
	/**
	 * like post and put it in db and user's posts
	 * @param post id
	 * @param user email
	 */
	@Override
	public void likePost(int postId, String userEmail) {	
		PreparedStatement statement = null;
		//System.out.println(postLikes.get(postId).contains(userEmail) + " " + postLikes.get(postId));
		if((!postLikes.get(postId).contains(userEmail)) && (!postDislikes.get(postId).contains(userEmail))){
			try {
				 statement = DBManager.getInstance().getConnection().prepareStatement(LIKE_POST);
				statement.setInt(1, postId);
				statement.setString(2, userEmail);
				statement.executeUpdate();
				
				Post post = getPost(postId);
				HashSet<String> likes = (HashSet<String>) post.getLikes();
				likes.add(userEmail);
				post.setLikes(likes);
				postLikes.put(postId, likes);
				
				System.out.println("like post");
			} catch (SQLException e) {
				System.out.println("The post cannot be liked right now");
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

	}

	/**
	 * dislike post and put it in db and user's posts
	 * @param post id
	 * @param user email
	 */
	@Override
	public void dislikePost(int postId, String userEmail) {
//		System.out.println(postDislikes.containsKey(postId));
//		System.out.println(postDislikes.get(postId).size());
//		System.out.println(postDislikes.get(postId).contains(userEmail));
		PreparedStatement statement = null;
		if(!postDislikes.get(postId).contains(userEmail) && (!postLikes.get(postId).contains(userEmail))){
			try {
				statement = DBManager.getInstance().getConnection().prepareStatement(DISLIKE_POST);
				statement.setInt(1, postId);
				statement.setString(2, userEmail);
				statement.executeUpdate();
				
				Post post = getPost(postId);
				HashSet<String> dislikes = (HashSet<String>) post.getDislikes();
				dislikes.add(userEmail);
				post.setDislikes(dislikes);
				postDislikes.put(postId, dislikes);
				System.out.println("dislike post");
			} catch (SQLException e) {
				System.out.println("The post cannot be disliked right now");
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
		
	}

	/**
	 * get all likes for post from db
	 * @param post id
	 * @return set with users emails that liked the post
	 */
	@Override
	public Set<String> getAllLikesForPost(int postId) {	
		HashSet<String> likesByPost = new HashSet<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_LIKES_BY_POST);
			statement.setInt(1, postId);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				likesByPost.add(resultSet.getString("user_email"));
			}
		} catch (SQLException e) {
			System.out.println("The likes for the post cannot be selected right now");
			e.printStackTrace();
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
		
		return likesByPost;
	}

	/**
	 * get all dislikes for post from db
	 * @param post id
	 * @return set with users emails that disliked the post
	 */
	@Override
	public Set<String> getAllDislikesForPost(int postId) {		
		HashSet<String> dislikesByPost = new HashSet<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_DISLIKES_BY_POST);
			statement.setInt(1, postId);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				dislikesByPost.add(resultSet.getString("user_email"));
			}
		} catch (SQLException e) {
			System.out.println("The dislikes for the post cannot be selected right now");
			e.printStackTrace();
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
		
		return dislikesByPost;
	}
	
	/**
	 * get number of likes for the post from post collection
	 * @param post id
	 * @return number of likes for the post
	 */
	@Override
	public int getNumberOfPostLikes(int postId){
		return getPost(postId).getLikes().size();
	}
	
	/**
	 * get number of dislikes for the post from post collection
	 * @param post id
	 * @return number of dislikes for the post
	 */
	@Override
	public int getNumberOfPostDislikes(int postId){
		return getPost(postId).getDislikes().size();
	}
	
	/**
	 * get all posts for a user from allPosts collection
	 * @param user email
	 * @return list with user's posts
	 */
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

	/**
	 * get all posts for a certain category from allPosts collection
	 * @param category name
	 * @return list with posts from this category
	 */
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
	
	/**
	 * get top ten posts from allPosts collection
	 * @return list with ten most liked posts
	 */
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
