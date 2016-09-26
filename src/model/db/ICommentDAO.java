package model.db;

import java.sql.Timestamp;
import java.util.List;


import model.pojo.Comment;

public interface ICommentDAO {

	void addComment(int postId, String userEmail, int parentCommentId, String text, Timestamp time);

	void removeComment(int commentId);

	List<Comment> getAllCommentsByPost(int postId);
	
	List<Comment> takeAllCommentsByPost(int postId);

}
