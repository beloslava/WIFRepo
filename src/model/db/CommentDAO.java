package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.pojo.Comment;
import model.pojo.Post;

public class CommentDAO implements ICommentDAO {

	private static final String SELECT_COMMENTS_BY_POST = "SELECT comment_id, post_id, user_email, comment_text, comment_date FROM post_comments WHERE post_id = ? ORDER BY comment_date DESC;";
	private static final String DELETE_COMMENT_BY_ID = "DELETE FROM post_comments WHERE comment_id = ?;";
	private static final String INSERT_COMMENT = "INSERT INTO post_comments (post_id, user_email, comment_text) VALUES (?,?,?);";

	private static CommentDAO instance;

	private CommentDAO() {
	}

	public synchronized static CommentDAO getInstance() {
		if (instance == null) {
			instance = new CommentDAO();
		}
		return instance;
	}

	@Override
	public void addComment(Post post, Comment c) {
		//TODO
		PreparedStatement statement;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS);
			//post_id, user_email, comment_text
			statement.setInt(1, post.getId());
			statement.setString(2, c.getUserEmail());
			statement.setString(3, c.getText());
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			c.setComment_id((int)id);
			
		} catch (SQLException e) {
			System.out.println("Cannot add comment right now");
			e.printStackTrace();
		}

	}

	@Override
	public void removeComment(Comment c) {
		
		PreparedStatement statement;
		
			try {
				statement = DBManager.getInstance().getConnection().prepareStatement(DELETE_COMMENT_BY_ID);
				statement.setInt(1, c.getComment_id());
				statement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Cannot delete comment right now");
				e.printStackTrace();
			}
		

	}

	@Override
	public Set<Comment> getAllCommentsByPost(Post p) {
		// comment_id, post_id, user_email, comment_text, comment_date FROM
		// post_comments
		HashSet<Comment> postComments = new HashSet<>();

		PreparedStatement statement;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(SELECT_COMMENTS_BY_POST);
			statement.setInt(1, p.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				postComments.add(new Comment(resultSet.getInt("comment_id"),
											resultSet.getInt("post_id"),
											resultSet.getString("user_email"), 
											resultSet.getString("comment_text"),
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

}
