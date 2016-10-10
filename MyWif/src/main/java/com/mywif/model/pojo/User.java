package com.mywif.model.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class User implements Searchable {
	// email, password, name, avatarPath, age, gender, personalDescription

	private String email;
	private String password;
	private String name;
	private String gender;
	private String about;
	private String avatarPath;
	private Set<String> followers; // emails of users that follow this user
	private Set<String> followed; // emails of users that this user follows
	private Map<Integer, Album> albums; // album id -> list from posts

	public User(String email, String password, String name, String gender, String about, String avatarPath,
			Set<String> followers, Set<String> followed, Map<Integer, Album> albums) {

		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.about = about;
		this.avatarPath = avatarPath;
		// this.posts = posts;
		this.followers = followers;
		this.followed = followed;
		this.albums = albums;
	}

	// add post in album
	public void addPostInAlbum(Post post) {
		albums.get(post.getAlbumId()).addPost(post);
	}

	// add album in user albums
	public void addAlbumInAlbum(Album album) {
		albums.put(album.getAlbumId(), album);
	}

	// add follower into user followers
	public void addFollower(String userEmail) {
		followers.add(userEmail);
		;
	}

	// add followed into user followed users
	public void addFollowed(String userEmail) {
		followed.add(userEmail);
		;
	}

	// remove follower from user followers
	public void removeFollower(String userEmail) {
		followers.remove(userEmail);
		
	}

	// remove followed from user followed users
	public void removeFollowed(String userEmail) {
		followed.remove(userEmail);
		
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getGender() {
		return gender;
	}

	public String getAbout() {
		return about;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Set<String> getFollowers() {
		return Collections.unmodifiableSet(followers);
	}

	public Set<String> getFollowed() {
		return Collections.unmodifiableSet(followed);
	}

	public Map<Integer, Album> getAlbums() {
		return Collections.unmodifiableMap(albums);
	}

	@Override
	public String getSearchableId() {
		return getEmail();
	}

}
