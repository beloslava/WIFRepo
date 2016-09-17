package model.db;

import java.util.List;


import model.pojo.Comment;
import model.pojo.Post;

public interface ICommentDAO {

	void addComment(Post post, Comment c);

	void removeComment(Comment c);

	List<Comment> getAllCommentsByPost(int postId);

}
