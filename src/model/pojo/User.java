package model.pojo;

import java.util.List;
import java.util.Set;

public class User {
	// email, password, name, avatarPath, age, gender, personalDescription

	private String email;
	private String password;
	private String name;
	private String gender;
	private String about;
	private String avatarPath;
	private List<Post> posts; // posts
	private Set<String> followers; //emails of users that follow this user
	private Set<String> followed; //emails of users that this user follows


	public User(String email, String password, String name, String gender, String about, String avatarPath,
		List<Post> posts, Set<String> followers, Set<String> followed) {
	
	this.email = email;
	this.password = password;
	this.name = name;
	this.gender = gender;
	this.about = about;
	this.avatarPath = avatarPath;
	this.posts = posts;
	this.followers = followers;
	this.followed = followed;
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

	public List<Post> getPosts() {
		return posts;
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

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Set<String> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<String> followers) {
		this.followers = followers;
	}

	public Set<String> getFollowed() {
		return followed;
	}

	public void setFollowed(Set<String> followed) {
		this.followed = followed;
	}

}
