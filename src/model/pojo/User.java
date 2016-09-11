package model.pojo;

import java.util.TreeMap;

public class User {
	//email, password, name, avatarPath, age, gender, personalDescription

	private String email;
	private String password;
	private String name;
	private String avatarPath;
	private int age;
	private String gender;
	private String personalDescription;
	private TreeMap<Integer, Post> posts; // id post -> post
	
	
	public User(String email, String password, String name, String avatarPath, int age, String gender,
			String personalDescription) {
		
		this.email = email;
		this.password = password;
		this.name = name;
		this.avatarPath = avatarPath;
		this.age = age;
		this.gender = gender;
		this.personalDescription = personalDescription;
		this.posts = new TreeMap<>();
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


	public String getPersonalDescription() {
		return personalDescription;
	}


	public TreeMap<Integer, Post> getPosts() {
		return (TreeMap<Integer, Post>) posts.clone();
	}
	
}
