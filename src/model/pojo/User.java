package model.pojo;

import java.util.Collections;
import java.util.List;

public class User {
	// email, password, name, avatarPath, age, gender, personalDescription

	private String email;
	private String password;
	private String name;
	private String avatarPath;
	private int age;
	private String gender;
	private String about;
	private List<Post> posts; // posts

	public User(String email, String password, String name, int age, String gender, String about, String avatarPath,
			List<Post> posts) {

		this.email = email;
		this.password = password;
		this.name = name;
		this.avatarPath = avatarPath;
		this.age = age;
		this.gender = gender;
		this.about = about;
		this.posts = posts;
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

	public int getAge() {
		return age;
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

	public void setAge(int age) {
		this.age = age;
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

}
