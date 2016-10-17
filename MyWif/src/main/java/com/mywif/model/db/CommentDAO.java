package com.mywif.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.Comment;
import com.mywif.model.pojo.Post;

public class CommentDAO implements ICommentDAO {

	private static final String SELECT_COMMENTS_BY_POST = "SELECT comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date FROM post_comments WHERE post_id = ? ORDER BY comment_date DESC;";
	private static final String INSERT_COMMENT = "INSERT INTO post_comments (post_id, user_email, parent_comment_id, comment_text) VALUES (?,?,?,?);";
	private static final String SELECT_COMMENTS_BY_COMMENT = "SELECT comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date FROM post_comments WHERE parent_comment_id = ? ORDER BY comment_date DESC;";
	private static final String SELECT_ALL_COMMENTS = "SELECT comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date FROM post_comments ORDER BY comment_date DESC;";
	private static final String SELECT_COMMENT_LIKES = "SELECT user_email FROM comments_likes WHERE comment_id = ?;";
	private static final String LIKE_COMMENT = "INSERT INTO comments_likes (comment_id, user_email) VALUES (?,?);";
	private static final String UNLIKE_COMMENT = "DELETE FROM comments_likes WHERE comment_id=? AND user_email = ?;";
	private static final String DELETE_COMMENT_LIKES = "DELETE FROM comments_likes WHERE comment_id = ?;";
	private static final String DELETE_COMMENT_PARENT = "UPDATE post_comments SET parent_comment_id = NULL WHERE parent_comment_id = ?;";
	private static final String DELETE_COMMENT_BY_ID = "DELETE FROM post_comments WHERE comment_id = ?;";
	
	private TreeMap<Integer, Comment> allComments; // comment id -> comment
	private HashMap<Integer, HashSet<String>> commentLikes;
	
	private static CommentDAO instance;

	private CommentDAO() {
		commentLikes = new HashMap<>();
		allComments = (TreeMap<Integer, Comment>) getAllComments();
		
		for(Comment comment : allComments.values()){
			int id = comment.getCommentId();
			commentLikes.put(id, (HashSet<String>) getAllLikesForComment(id));
		}
	}

	public synchronized static CommentDAO getInstance() {
		if (instance == null) {
			instance = new CommentDAO();
		}
		return instance;
	}

	/**
	 * get comment by id
	 * @return comment
	 */
	public Comment getComment(int commentId){
		return allComments.get(commentId);
	}
	
	/**
	 * get comment likes by id
	 * @return number of likes for comment
	 */
	public int getCommentLikes(int commentId){
		return getComment(commentId).getCommentLikes().size();
	}
	
	/**
	 * get comments by post from allComments collection (use in delete post)
	 * @return list with comments by post
	 */
	public List < Comment> getCommentsByPost(int postId){
		List<Comment> comments = new ArrayList<>();
		for(Comment c : allComments.values()){
			if(c.getPostId() == postId){
				comments.add(c);
			}
		}
		return comments;
	}
	
	/**
	 * add comment in db, allComments collection and post's comments or comment comments collection
	 * @throws DBException 
	 */
	@Override
	public void addComment(int postId, String userEmail, Integer parentCommentId, String text, Timestamp time,
			ArrayList<Comment> commentComments, Set<String> commentLikes) throws DBException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS);
			// post_id, user_email, parent_comment_id, comment_text
			statement.setInt(1, postId);
			statement.setString(2, userEmail);
			statement.setObject(3, parentCommentId);
			statement.setString(4, text);
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			long commentId = resultSet.getLong(1);
			
			Set<String> likes = getAllLikesForComment((int)commentId);

			Comment comment = new Comment((int) commentId, postId, userEmail, parentCommentId, text, time,
					commentComments, likes);
			
			allComments.put((int)commentId, comment);
			
			if(parentCommentId == null){
				PostDAO.getInstance().getPost(postId).addComent(comment);
			}
			else{
				allComments.get(parentCommentId).addCommentComment(comment);	
			}

		} catch (SQLException e) {
			throw new DBException("Cannot add comment right now", e);

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				throw new DBException(DBException.ERROR_MESSAGE_CLOSE_CONN, e);

			}
		}

	}

	/**
	 * remove comment
	 * @param comment id
	 * @throws DBException 
	 */
	@Override
	public void removeComment(int commentId) throws DBException {
		//TODO
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

		Connection conn = (Connection) DBManager.getInstance().getConnection();
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);

			ps1 = conn.prepareStatement(DELETE_COMMENT_LIKES);
			ps1.setInt(1, commentId); // delete comment likes
			ps1.executeUpdate();

			ps2 = conn.prepareStatement(DELETE_COMMENT_PARENT);
			ps2.setInt(1, commentId); // set parentId null
			ps2.executeUpdate();

			ps3 = conn.prepareStatement(DELETE_COMMENT_BY_ID);
			ps2.setInt(1, commentId); // delete comment
			ps2.executeUpdate();

			conn.commit();

			if (allComments.containsKey(commentId)) {
				Comment comment = getComment(commentId);
				int postId = comment.getPostId();
				PostDAO.getInstance().getPost(postId).removeComent(comment);
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Rollback!", e);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
				if (ps1 != null) {
					ps1.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
				if (ps3 != null) {
					ps3.close();
				}
			} catch (SQLException e) {
				throw new DBException(DBException.ERROR_MESSAGE_CLOSE_CONN, e);

			}
		}
	}

	/**
	 * get all comments for a post from the post 
	 * @param post id
	 * @return list with comments for this post
	 */
	@Override
	public List<Comment> takeAllCommentsByPost(int postId) { 
		Post post = PostDAO.getInstance().getPost(postId);
		List<Comment> commentsByPost = post.getComments();
		return commentsByPost;
	}
	
	/**
	 * get all comments for comment from the comment 
	 * @param comment id
	 * @return list with comments for this comment
	 */
	@Override
	public List<Comment> takeAllCommentsByComment(int commentId) { 
		Comment comment = getComment(commentId);
		List<Comment> commentsByComment = comment.getCommentComments();
		return commentsByComment;
	}
	
	
	@Override
	public void likeComment(int commentId, String userEmail) throws DBException{
		PreparedStatement statement = null;
	//	System.out.println(commentLikes.get(commentId).contains(userEmail) + " " + commentLikes.get(commentId));
		if((!commentLikes.containsKey(commentId) || !commentLikes.get(commentId).contains(userEmail))){
			try {
				statement = DBManager.getInstance().getConnection().prepareStatement(LIKE_COMMENT);
				statement.setInt(1, commentId);
				statement.setString(2, userEmail);
				statement.executeUpdate();
				
				Comment comment = getComment(commentId);

				if(!commentLikes.containsKey(commentId)){
					commentLikes.put(commentId, new HashSet<>());
				}
				commentLikes.get(commentId).add(userEmail);
				
				comment.addCommentLike(userEmail);
				

				
				System.out.println("like comment");
			} catch (SQLException e) {
				throw new DBException("The post cannot be liked right now", e);

			} finally {
				try {
					if (statement != null) {
						statement.close();
					}

				} catch (SQLException e) {
					throw new DBException(DBException.ERROR_MESSAGE_CLOSE_CONN, e);

				}
			}
		}

	}
	
	@Override
	public void unlikeComment(int commentId, String userEmail) throws DBException{
		PreparedStatement statement = null;
		if((commentLikes.containsKey(commentId) && commentLikes.get(commentId).contains(userEmail))){
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(UNLIKE_COMMENT);
			statement.setInt(1, commentId);
			statement.setString(2, userEmail);
			statement.executeUpdate();
			Comment comment = getComment(commentId);
			comment.removeCommentLike(userEmail);
		} catch (SQLException e) {
			throw new DBException("Cannot unlike comment right now", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

			} catch (SQLException e) {
				throw new DBException(DBException.ERROR_MESSAGE_CLOSE_CONN, e);
			}
		}
	}
	}
	
	private Set<String> getAllLikesForComment(int commentId) {	
		HashSet<String> likesByComment = new HashSet<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_COMMENT_LIKES);
			statement.setInt(1, commentId);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				likesByComment.add(resultSet.getString("user_email"));
			}
		} catch (SQLException e) {
			System.out.println("The likes for the comment cannot be selected right now");
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
		
		return likesByComment;
	}
	
	/**
	 * get all comments for a comment from db
	 * @param comment id
	 * @return list with comments for this comment
	 */
	public List<Comment> getAllCommentsByComment(int commentId) {
		// comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date FROM
		// post_comments
		List<Comment> commentComments = new ArrayList<>();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_COMMENTS_BY_COMMENT);
			statement.setInt(1, commentId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ArrayList<Comment> commentCommentscom = (ArrayList<Comment>) getAllCommentsByComment(resultSet.getInt("comment_id"));
				Set<String> commentLikes = getAllLikesForComment(resultSet.getInt("comment_id"));
				commentComments.add(new Comment(resultSet.getInt("comment_id"), 
												resultSet.getInt("post_id"),
												resultSet.getString("user_email"), 
												resultSet.getInt("parent_comment_id"),
												resultSet.getString("comment_text"),
												resultSet.getTimestamp("comment_date"),
												commentCommentscom,
												commentLikes

				));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get comment's comments right now");
			e.printStackTrace();
			return commentComments;

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.out.println("Can not get all comments by comment right now!");
				e.printStackTrace();
			}
		}
		
		return commentComments;
	}
	
	/**
	 * get all comments from db
	 * @return map with comment id -> comment 
	 */
	public Map<Integer, Comment> getAllComments() {
		TreeMap<Integer, Comment> allComments = new TreeMap<Integer, Comment>((commentId1, commentId2) -> commentId2 - commentId1);
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().createStatement();
			//comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date
			resultSet = statement.executeQuery(SELECT_ALL_COMMENTS);
			while (resultSet.next()) {
				ArrayList<Comment> commentComments = (ArrayList<Comment>) getAllCommentsByComment(resultSet.getInt("comment_id"));
				Set<String> commentLikes = getAllLikesForComment(resultSet.getInt("comment_id"));
				allComments.put(resultSet.getInt("comment_id"), new Comment(resultSet.getInt("comment_id"), 
																			resultSet.getInt("post_id"),
																			resultSet.getString("user_email"),
																			(int) resultSet.getLong("parent_comment_id"),
																			resultSet.getString("comment_text"),
																			resultSet.getTimestamp("comment_date"),
																			commentComments, 
																			commentLikes

																));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get comments right now");
			e.printStackTrace();
			return allComments;

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.out.println("Can not get all comments right now!");
				e.printStackTrace();
			}
		}

		return allComments;
	}
	
	/**
	 * get all comments for a post from db
	 * @param comment id
	 * @return list with comments for this post
	 */	
	public List<Comment> getAllCommentsByPost(int postId) {
		// comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date FROM
		// post_comments
		List<Comment> postComments = new ArrayList<>();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_COMMENTS_BY_POST);
			statement.setInt(1, postId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ArrayList<Comment> commentComments = (ArrayList<Comment>) getAllCommentsByComment(resultSet.getInt("comment_id"));
				Set<String> commentLikes = getAllLikesForComment(resultSet.getInt("comment_id"));

				postComments.add(new Comment(resultSet.getInt("comment_id"), 
											resultSet.getInt("post_id"),
											resultSet.getString("user_email"), 
											(int) resultSet.getLong("parent_comment_id"),
											resultSet.getString("comment_text"),
											resultSet.getTimestamp("comment_date"),
											commentComments, 
											commentLikes
					
								));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get post's comments right now");
			e.printStackTrace();
			return postComments;

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.out.println("Can not get all comments by post right now!");
				e.printStackTrace();
			}
		}
		
		return postComments;
		
	}

	@Override
	public boolean isItLiked(int commentId, String userEmail) {
		if (CommentDAO.getInstance().getComment(commentId).getCommentLikes().contains(userEmail)) {
				return true;
			}
		return false;
	}
	

}
