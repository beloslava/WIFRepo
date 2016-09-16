package model.db;

import java.sql.Timestamp;
import java.util.Set;

import model.pojo.Post;
import model.pojo.User;

public interface IPostDAO {

	void addPost(String userEmail, String tag, String picture, int like, int dislike,
			Timestamp time);

	void editPost(Post post);

	void removePost(User user, Post post);

	void likePost(Post post);

	void dislikePost(Post post);

	Set<Post> getAllPostsByUser(User user);

	Set<Post> getAllPostsByTag(String tag);


}
