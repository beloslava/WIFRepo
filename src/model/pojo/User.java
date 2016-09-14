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
	private String about;
	private TreeMap<Integer, Post> posts; // id post -> post
	
	
	public User(String email, String password, String name,int age, String gender,
			String personalDescription) {
		
		this.email = email;
		this.password = password;
		this.name = name;
		//this.avatarPath = avatarPath;
		this.age = age;
		this.gender = gender;
		this.about = personalDescription;
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


	public String getAbout() {
		return about;
	}


	public TreeMap<Integer, Post> getPosts() {
		return (TreeMap<Integer, Post>) posts.clone();
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


	public void setPosts(TreeMap<Integer, Post> posts) {
		this.posts = posts;
	}
	
	
	
}
