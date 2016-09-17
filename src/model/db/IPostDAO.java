package model.db;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import model.pojo.Comment;
import model.pojo.Post;
import model.pojo.User;

public interface IPostDAO {

	void addPost(String userEmail, String tag, String picture, int like, int dislike,
			Timestamp time, TreeSet<Comment> comments);

	void removePost(String userEmail, Post post);

	void likePost(Post post);

	void dislikePost(Post post);

	Set<Post> getAllPostsByUser(String userEmail);

	Set<Post> getAllPostsByTag(String tag);
	
	Map<Integer, Post> getAllPosts();
	
	Set<Post> getTopTenPosts();


}
