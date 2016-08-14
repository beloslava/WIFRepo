package posts;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import users.User;

public class Post implements IPost {

	static int id = 0;

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
}
