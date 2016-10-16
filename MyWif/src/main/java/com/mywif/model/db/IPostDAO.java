package com.mywif.model.db;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.Comment;
import com.mywif.model.pojo.Post;


public interface IPostDAO {

	void addPost(String userEmail, Integer albumId, String category, String picture, String name, String keyWords,
			Timestamp time, List<Comment> comments, Set<String> likes, Set<String> dislikes) throws DBException;

	void likePost(int postId, String userEmail) throws DBException;
	void unlikePost(int postId, String userEmail) throws DBException;

	void dislikePost(int postId, String userEmail) throws DBException;
	void undislikePost(int postId, String userEmail) throws DBException;

	List<Post> getAllPostsByUser(String userEmail);

	List<Post> getAllPostsByCategory(String category);
	
	Map<Integer, Post> getAllPosts();
	
	List<Post> getTopTenPosts();
	
	Set<String> getAllLikesForPost(int postId); //get all user's emails that liked the post
	
	Set<String> getAllDislikesForPost(int postId); //get all user's emails that disliked the post
	
}
