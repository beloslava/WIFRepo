package com.mywif.model.pojo;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;

public class Album {

	private int albumId;
	private String name;
	private String userEmail;
	private String createdOn;
	private List<Post> posts;

	public Album(int albumId, String name, String userEmail, Timestamp time, List<Post> posts) {
		this.albumId = albumId;
		this.name = name;
		this.userEmail = userEmail;
		this.createdOn = time.toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		this.posts = posts;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public List<Post> getPosts() {
		return Collections.unmodifiableList(posts);
	}

	// add post in posts
	public void addPost(Post post) {
		posts.add(post);
	}

	// delete post in posts
	public void deletePost(Post post) {
		posts.remove(post);
	}

}
