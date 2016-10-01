package model.pojo;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Set;

public class Comment {

	private int commentId;
	private int postId;
	private String userEmail;
	private Integer parentCommentId;
	private String text;
	private String createdOn;
	private ArrayList<Comment> commentComments;
	private Set<String> commentLikes;


	public Comment(int commentId, int postId, String userEmail, Integer parentCommentId, String text, Timestamp time, ArrayList<Comment> commentComments, Set<String> commentLikes) {
		this.commentId = commentId;
		this.postId = postId;
		this.userEmail = userEmail;
		this.parentCommentId = parentCommentId;
		this.text = text;
		this.createdOn = time.toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		this.commentComments = commentComments;
		this.commentLikes = commentLikes;
	}
	
	
	public Set<String> getCommentLikes() {
		return commentLikes;
	}

	public void setCommentLikes(Set<String> commentLikes) {
		this.commentLikes = commentLikes;
	}
	
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(int parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public ArrayList<Comment> getCommentComments() {
		return commentComments;
	}
	
	public void setCommentComments(ArrayList<Comment> commentComments) {
		this.commentComments = commentComments;
	}

}
