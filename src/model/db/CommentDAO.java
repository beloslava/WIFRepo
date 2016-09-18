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
import java.util.TreeMap;

import model.pojo.Comment;
import model.pojo.Post;

public class CommentDAO implements ICommentDAO {

	private static final String SELECT_COMMENTS_BY_POST = "SELECT comment_id, post_id, user_email, comment_text, comment_date FROM post_comments WHERE post_id = ? ORDER BY comment_date DESC;";
	private static final String DELETE_COMMENT_BY_ID = "DELETE FROM post_comments WHERE comment_id = ?;";
	private static final String INSERT_COMMENT = "INSERT INTO post_comments (post_id, user_email, comment_text) VALUES (?,?,?);";

	HashMap<Integer, ArrayList<Comment>> allComments; // post id -> comment

	private static CommentDAO instance;

	private CommentDAO() {

		allComments = new HashMap<>();

		for (Post p : PostDAO.getInstance().takeAllPosts().values()) {
			allComments.put(p.getId(), (ArrayList<Comment>) p.getComments());
		}

	}

	public synchronized static CommentDAO getInstance() {
		if (instance == null) {
			instance = new CommentDAO();
		}
		return instance;
	}

	@Override
	public void addComment(int postId, String userEmail, String text, Timestamp time) {
		PreparedStatement statement;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_COMMENT,
					Statement.RETURN_GENERATED_KEYS);
			// post_id, user_email, comment_text
			statement.setInt(1, postId);
			statement.setString(2, userEmail);
			statement.setString(3, text);
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			long commentId = rs.getLong(1);

			Comment comment = new Comment((int) commentId, postId, userEmail, text, time);
			PostDAO.getInstance().getPost(postId).getComments().add(comment);
			if (!allComments.containsKey(postId)) {
				allComments.put(postId, new ArrayList<>());
			}
			allComments.get(postId).add(comment);

		} catch (SQLException e) {
			System.out.println("Cannot add comment right now");
			e.printStackTrace();
		}

	}

	@Override
	public void removeComment(int commentId) {
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

	@Override
	public List<Comment> getAllCommentsByPost(int postId) {
		// comment_id, post_id, user_email, comment_text, comment_date FROM
		// post_comments
		List<Comment> postComments = new ArrayList<>();

		PreparedStatement statement;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_COMMENTS_BY_POST);
			statement.setInt(1, postId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				postComments.add(new Comment(resultSet.getInt("comment_id"), resultSet.getInt("post_id"),
						resultSet.getString("user_email"), resultSet.getString("comment_text"),
						resultSet.getTimestamp("comment_date")

				));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get user's posts right now");
			e.printStackTrace();
			return postComments;

		}

		return postComments;

	}

	@Override
	public List<Comment> selectAllCommentsByPost(int postId) { 

		ArrayList<Comment> commentsByPost = allComments.get(postId);
		Collections.sort(commentsByPost, (Comment o1, Comment o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()));
		return commentsByPost;
	}
}
