package users;

import posts.Post;

public interface IUser {

	void createProfile();

	void makePost(Post post, String title, String tag);

	void uploadPost();
	
	void deletePost(Post post);

	void writeComment(Post post);

	void reviewPostComments(Post post);//?

	void likePost(Post post); 

	void dislikePost(Post post); 

	void changePassword();

	void changeName(String name);

	void addFeatures();

	void changeSettings();
}
