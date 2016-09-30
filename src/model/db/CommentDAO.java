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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.pojo.Comment;
import model.pojo.Post;

public class CommentDAO implements ICommentDAO {

	private static final String SELECT_COMMENTS_BY_POST = "SELECT comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date FROM post_comments WHERE post_id = ? ORDER BY comment_date DESC;";
	private static final String DELETE_COMMENT_BY_ID = "DELETE FROM post_comments WHERE comment_id = ?;";
	private static final String INSERT_COMMENT = "INSERT INTO post_comments (post_id, user_email, parent_comment_id, comment_text) VALUES (?,?,?,?);";
	private static final String SELECT_COMMENTS_BY_COMMENT = "SELECT comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date FROM post_comments WHERE parent_comment_id = ? ORDER BY comment_date DESC;";
	private static final String SELECT_ALL_COMMENTS = "SELECT comment_id, post_id, user_email, parent_comment_id, comment_text, comment_date FROM post_comments ORDER BY comment_date DESC;";
	
	private TreeMap<Integer, Comment> allComments; // comment id -> comment
	
	private static CommentDAO instance;

	private CommentDAO() {
		allComments = (TreeMap<Integer, Comment>) getAllComments();
	}

	public synchronized static CommentDAO getInstance() {
		if (instance == null) {
			instance = new CommentDAO();
		}
		return instance;
	}

	public Comment getComment(int commentId){
		return allComments.get(commentId);
	}
	
	/**
	 * add comment in db, allComments collection and post's comments or comment comments collection
	 */
	@Override
	public void addComment(int postId, String userEmail, Integer parentCommentId, String text, Timestamp time, ArrayList<Comment> commentComments) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_COMMENT,
					Statement.RETURN_GENERATED_KEYS);
			// post_id, user_email, parent_comment_id, comment_text
			statement.setInt(1, postId);
			statement.setString(2, userEmail);
			statement.setObject(3, parentCommentId);
			//statement.setNull(3, java.sql.Types.INTEGER);
			statement.setString(4, text);
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			long commentId = resultSet.getLong(1);

			Comment comment = new Comment((int) commentId, postId, userEmail, parentCommentId, text, time, commentComments);
			
			allComments.put((int)commentId, comment);
			if(parentCommentId == null){			
				PostDAO.getInstance().getPost(postId).getComments().add(comment);
			}
			else{
				allComments.get(parentCommentId).getCommentComments().add(comment);	
			}

		} catch (SQLException e) {
			System.out.println("Cannot add comment right now");
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
	public void removeComment(int commentId) {
		//TODO
		PreparedStatement statement;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(DELETE_COMMENT_BY_ID);
			statement.setInt(1, commentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Cannot delete comment right now");
			e.printStackTrace();
		}

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
				allComments.put(resultSet.getInt("comment_id"), new Comment( resultSet.getInt("comment_id"), 
						resultSet.getInt("post_id"),
						resultSet.getString("user_email"),
						(int) resultSet.getLong("parent_comment_id"),
						resultSet.getString("comment_text"),
						resultSet.getTimestamp("comment_date"),
						commentComments

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
				// TODO Auto-generated catch block
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
	@Override
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
				postComments.add(new Comment(resultSet.getInt("comment_id"), 
						resultSet.getInt("post_id"),
						resultSet.getString("user_email"), 
				(int) resultSet.getLong("parent_comment_id"),
						resultSet.getString("comment_text"),
						resultSet.getTimestamp("comment_date"),
						commentComments

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return postComments;

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
				commentComments.add(new Comment(resultSet.getInt("comment_id"), 
						resultSet.getInt("post_id"),
						resultSet.getString("user_email"), 
						resultSet.getInt("parent_comment_id"),
						resultSet.getString("comment_text"),
						resultSet.getTimestamp("comment_date"),
						commentCommentscom

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return commentComments;

	}
	
	/**
	 * get all comments for a post from the post 
	 * @param post id
	 * @return list with comments for this post
	 */
	@Override
	public List<Comment> takeAllCommentsByPost(int postId) { 
		Post post = PostDAO.getInstance().getPost(postId);
		ArrayList<Comment> commentsByPost = (ArrayList<Comment>) post.getComments();
		Collections.sort(commentsByPost, (Comment o1, Comment o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()));
		return commentsByPost;
	}
	
	/**
	 * get all comments for comment from the comment 
	 * @param comment id
	 * @return list with comments for this comment
	 */
	public List<Comment> takeAllCommentsByComment(int commentId) { 
		Comment comment = getComment(commentId);
		ArrayList<Comment> commentsByComment = (ArrayList<Comment>) comment.getCommentComments();
		Collections.sort(commentsByComment, (Comment o1, Comment o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()));
		return commentsByComment;
	}
	
}
