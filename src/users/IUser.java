package users;

import posts.Post;

public interface IUser {

	void createProfile();

	void makePost(Post post, String title, String tag);

	void uploadPost();

	void writeComment(Post post);

	void reviewPostComments(Post post);

	void likePost(Post post);

	void dislikePost(Post post);

	void changePassword(String password);

	void deletePost(Post post);

}
