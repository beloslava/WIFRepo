package model.pojo;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Comment {

	private int comment_id;
	private int post_id;
	private String userEmail;
	private String text;
	private String createdOn;

	public Comment(int comment_id, int post_id, String userEmail, String text, Timestamp time) {

		this.comment_id = comment_id;
		this.post_id = post_id;
		this.userEmail = userEmail;
		this.text = text;
		this.createdOn = time.toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
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

	public String getCreatedOn() {
		return createdOn;
	}

}
