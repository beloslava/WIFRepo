package site;

import posts.Post;
import users.User;

public interface ISite {

	void searchPost(String tagName);

	void addUser(User user);

	void addPost(Post post);

}
