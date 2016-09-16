package model.db;

import java.util.HashSet;
import java.util.Set;

import model.pojo.Comment;
import model.pojo.Post;

public class CommentDAO implements ICommentDAO {

	private static final String SELECT_COMMENTS_BY_POST = "SELECT comment_id, post_id, user_email, comment_text, comment_date FROM post_comments WHERE post_id = ? ORDER BY comment_date DESC;";
	
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeComment(Comment c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Comment> getAllCommentsByPost(Post p) {
		//comment_id, post_id, user_email, comment_text, comment_date FROM post_comments
		HashSet<Comment> postComments = new HashSet<>();
		
		return null;
	}
	
}
