package model.pojo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {

	private int id;
	private String userEmail;
	private String tag;
	private String photoPath;
	private int like;
	private int dislike;
	private LocalDateTime dateAndTime;
	ArrayList<Comment> comments;
	


	public Post(int id, String userEmail, String tag, String photoPath) {
		
		this.id = id;
		this.userEmail = userEmail;
		this.tag = tag;
		this.photoPath = photoPath;
		this.dateAndTime = LocalDateTime.now();
		this.comments = new ArrayList<>();
	}



	public int getId() {
		return id;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public String getTag() {
		return tag;
	}



	public String getPhotoPath() {
		return photoPath;
	}



	public int getLike() {
		return like;
	}



	public int getDislike() {
		return dislike;
	}



	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}



	public ArrayList<Comment> getComments() {
		return (ArrayList<Comment>) comments.clone();
	}


	
	
	
	
	
}
