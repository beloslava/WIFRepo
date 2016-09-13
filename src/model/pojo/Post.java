package model.pojo;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Post {

	private int id;
	private String userEmail;
	private String tag;
	private String photoPath;
	private int like;
	private int dislike;
	private String createdOn;
	ArrayList<Comment> comments;
	


	public Post(int id, String userEmail, String tag, String photoPath, Timestamp time) {
		
		this.id = id;
		this.userEmail = userEmail;
		this.tag = tag;
		this.photoPath = photoPath;
		this.createdOn = time.toLocalDateTime().format(
				DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		this.comments = new ArrayList<>();
	}
	
	



	public Post(int id, String userEmail, String tag, String photoPath, int like, int dislike,
			Timestamp time) {
		
		this.id = id;
		this.userEmail = userEmail;
		this.tag = tag;
		this.photoPath = photoPath;
		this.like = like;
		this.dislike = dislike;
		this.createdOn = time.toLocalDateTime().format(
				DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
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



	public String getCreatedOn() {
		return createdOn;
	}



	public ArrayList<Comment> getComments() {
		return (ArrayList<Comment>) comments.clone();
	}





	@Override
	public String toString() {
		return "Post [id=" + id + ", userEmail=" + userEmail + ", tag=" + tag + ", photoPath=" + photoPath + ", like="
				+ like + ", dislike=" + dislike + ", createdOn=" + createdOn + ", comments=" + comments + "]";
	}


	
	
	
	
	
}
