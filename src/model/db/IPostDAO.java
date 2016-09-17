package model.db;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


import model.pojo.Comment;
import model.pojo.Post;


public interface IPostDAO {

	void addPost(String userEmail, String tag, String picture, int like, int dislike,
			Timestamp time, List<Comment> comments);

	void removePost(String userEmail, Post post);

	void likePost(Post post);

	void dislikePost(Post post);

	List<Post> getAllPostsByUser(String userEmail);

	List<Post> getAllPostsByTag(String tag);
	
	Map<Integer, Post> getAllPosts();
	
	List<Post> getTopTenPosts();


}
