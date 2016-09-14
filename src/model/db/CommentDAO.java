package model.db;

import java.util.Set;

import model.pojo.Comment;
import model.pojo.Post;

public class CommentDAO implements ICommentDAO {

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
		// TODO Auto-generated method stub
		return null;
	}
	
}
