package com.mywif.model.pojo;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Comment {

	private int commentId;
	private int postId;
	private String userEmail;
	private Integer parentCommentId;
	private String text;
	private String createdOn;
	private List<Comment> commentComments;
	private Set<String> commentLikes;

	public Comment(int commentId, int postId, String userEmail, Integer parentCommentId, String text, Timestamp time, List<Comment> commentComments, Set<String> commentLikes) {
		this.commentId = commentId;
		this.postId = postId;
		this.userEmail = userEmail;
		this.parentCommentId = parentCommentId;
		this.text = text;
		this.createdOn = time.toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		this.commentComments = commentComments;
		this.commentLikes = commentLikes;
	}
	 //add comment like
	public void addCommentLike(String userEmail){
		commentLikes.add(userEmail);
	}
	//add comment to comment
	public void addCommentComment(Comment comment){
		commentComments.add(comment);
	}
	
	public void removeCommentComment(Comment comment){
		commentComments.remove(comment);
	}
	
	public void removeCommentLike(String userEmail){
		commentLikes.remove(userEmail);
	}
	
	public Set<String> getCommentLikes() {
		return Collections.unmodifiableSet(commentLikes);
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

	public Integer getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Integer parentCommentId) {
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

	public List<Comment> getCommentComments() {
		Collections.sort(commentComments, (Comment o1, Comment o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()));
		return Collections.unmodifiableList(commentComments);
	}
	
	public void setCommentComments(ArrayList<Comment> commentComments) {
		this.commentComments = commentComments;
	}

}
