package model.db;

import java.util.List;


import model.pojo.Comment;
import model.pojo.Post;

public interface ICommentDAO {

	void addComment(int postId, String userEmail, String text);

	void removeComment(int commentId);

	List<Comment> getAllCommentsByPost(int postId);

}
