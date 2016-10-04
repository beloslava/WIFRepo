package com.mywif.model.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mywif.model.pojo.Comment;

public interface ICommentDAO {

	void addComment(int postId, String userEmail, Integer parentCommentId, String text, 
			Timestamp time, ArrayList<Comment> commentComments, Set<String> likes);

	void removeComment(int commentId);

	List<Comment> getAllCommentsByPost(int postId);
	
	List<Comment> takeAllCommentsByPost(int postId);

}
