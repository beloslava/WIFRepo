package model.pojo;

import java.time.LocalDateTime;

public class Comment {

	private int comment_id;
	private int post_id;
	private String userEmail;
	private String text;
	private LocalDateTime dateAndTime;
	


	public Comment(int comment_id, int post_id, String userEmail, String text) {
		
		this.comment_id = comment_id;
		this.post_id = post_id;
		this.userEmail = userEmail;
		this.text = text;
		this.dateAndTime = LocalDateTime.now();
	}



	public int getComment_id() {
		return comment_id;
	}



	public int getPost_id() {
		return post_id;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public String getText() {
		return text;
	}



	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	
	
	
	
	
}
