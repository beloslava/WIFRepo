package users;

import java.util.TreeMap;

import posts.Comment;
import posts.Post;
import site.Site;

public class User implements IUser {

	private ProfileSettings settings;
	private Site site;
	private TreeMap<Integer, Post> posts; // id post -> post

	@Override
	public void createProfile() {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeComment(Post post) {
		Comment comment = new Comment(post);
		post.setOwner(this);
		comment.createComment();
		post.setComment(comment);

	}

	@Override
	public void reviewPostComments(Post post) {
		post.reviewComments();

	}

	@Override
	public void likePost(Post post) {
		post.setLike();

	}

	@Override
	public void dislikePost(Post post) {
		post.setDislike();

	}

	@Override
	public void changePassword(String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePost(Post post) {
		post = null;

	}

	@Override
	public void uploadPost() {
		// TODO Auto-generated method stub

	}

	@Override
	public void makePost(Post post, String title, String tag) {
		// TODO Auto-generated method stub

	}

}
