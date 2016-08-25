package users;

import posts.Post;

public interface IUser {

	void createProfile();

	Post makePost();

	void deletePost(Post post);

	void writeComment(Post post);

	void reviewPostComments(Post post);

	void likePost(Post post);

	void dislikePost(Post post);

	void changePassword();

	void changePersonalDescription(String desc);

	void changeCountry(String country);

	void changeSettings();

	void changeName();
	
	public void searchPost(String tag);

}
