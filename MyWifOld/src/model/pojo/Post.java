package model.pojo;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post implements Searchable {

	private int id;
	private String userEmail;
	private Integer albumId;
	private String category;
	private String picture;
	private String name;
	private String keyWords;
	private String createdOn;
	private List<Comment> comments;
	private Set<String> likes; // user's emails of users that liked the post
	private Set<String> dislikes; // user's emails of users that liked the post

	public Post(int id, String userEmail, Integer albumId, String category, String picture, String name,
			String keyWords, Timestamp time, List<Comment> comments, Set<String> likes, Set<String> dislikes) {
		this.id = id;
		this.userEmail = userEmail;
		this.albumId = albumId;
		this.category = category;
		this.picture = picture;
		this.name = name;
		this.keyWords = keyWords;
		this.createdOn = time.toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		this.comments = comments;
		this.likes = likes;
		this.dislikes = dislikes;
	}

	// add comment in comments collection
	public void addComent(Comment comment) {
		comments.add(comment);
	}

	// add like in post likes
	public void addLike(String userEmail) {
		likes.add(userEmail);
	}

	// add dislike in post dislikes
	public void addDislike(String userEmail) {
		dislikes.add(userEmail);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public List<Comment> getComments() {
		Collections.sort(comments, (Comment o1, Comment o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn()));
		return Collections.unmodifiableList(comments);
	}

	public Set<String> getLikes() {
		return Collections.unmodifiableSet(likes);
	}

	public Set<String> getDislikes() {
		return Collections.unmodifiableSet(dislikes);
	}

	@Override
	public String getSearchableId() {
		return Integer.toString(getId());
	}

}