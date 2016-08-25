package posts;

import java.util.ArrayList;

import users.User;

public class Post implements IPost{

	public int id = 0;

	private User owner;
	private String title;
	private String tag;
	private int like;
	private int dislike;
	ArrayList<Comment> comments;

	public Post(User owner, String title, String tag) {
		this.owner = owner;
		this.title = title;
		this.tag = tag;
		this.like = 0;
		this.dislike = 0;
		this.comments = new ArrayList<Comment>();
		++id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getTag() {
		return tag;
	}
	@Override
	public void setLike() {
		this.like++;
	}

	@Override
	public void setDislike() {
		this.dislike++;
	}

	@Override
	public void setComment(Comment postComment) {
		comments.add(postComment);
	}

	@Override
	public void reviewComments() {
		for (Comment comment : comments) {
			System.out.println(comment);
		}

	}
	@Override
	public String toString() {
		return "Post: " + title + " - " + tag;
	}
	
}
