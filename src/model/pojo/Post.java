package model.pojo;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.TreeSet;

public class Post {

	private int id;
	private String userEmail;
	private String tag;
	private String picture;
	private int like;
	private int dislike;
	private String createdOn;
	private TreeSet<Comment> comments;
	


	public Post(int id, String userEmail, String tag, String picture, int like, int dislike,
			Timestamp time, TreeSet<Comment> comments) {
		
		this.id = id;
		this.userEmail = userEmail;
		this.tag = tag;
		this.picture = picture;
		this.like = like;
		this.dislike = dislike;
		this.createdOn = time.toLocalDateTime().format(
				DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		this.comments = comments;
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



	public String getPicture() {
		return picture;
	}



	public int getLike() {
		return like;
	}



	public int getDislike() {
		return dislike;
	}



	public String getCreatedOn() {
		return createdOn;
	}



	public TreeSet<Comment> getComments() {
		return (TreeSet<Comment>) comments.clone();
	}





	public void setId(int id) {
		this.id = id;
	}





	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}





	public void setTag(String tag) {
		this.tag = tag;
	}





	public void setPicture(String picture) {
		this.picture = picture;
	}





	public void setLike(int like) {
		this.like = like;
	}





	public void setDislike(int dislike) {
		this.dislike = dislike;
	}





	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}





	public void setComments(TreeSet<Comment> comments) {
		this.comments = (TreeSet<Comment>) comments.clone();
	}








	
	
	
	
	
}
