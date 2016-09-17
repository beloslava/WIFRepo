package model.db;

import java.util.Set;

import model.pojo.Comment;
import model.pojo.Post;

public interface ICommentDAO {

	void addComment(Post post, Comment c);

	void removeComment(Comment c);

	Set<Comment> getAllCommentsByPost(Post p);

}
