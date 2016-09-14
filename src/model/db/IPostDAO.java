package model.db;

import java.util.Set;

import model.pojo.Post;
import model.pojo.User;

public interface IPostDAO {

	void addPost(User user, Post post);

	void editPost(Post post);

	void removePost(User user, Post post);

	void likePost(Post post);

	void dislikePost(Post post);

	Set<Post> getAllPostsByUser(User user);

	Set<Post> getAllPostsByTag(String tag);


}
