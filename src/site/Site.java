package site;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import posts.Post;
import users.User;

public class Site implements ISite {

	private TreeMap<String, User> users; // username -> user
	private HashMap<Post, String> posts; // post tag -> post

	public Site() {
		this.users = new TreeMap<>();
		this.posts = new HashMap<>();
	}

	@Override
	public void searchPost(String tagName) {

		for (Entry<Post, String> entry : posts.entrySet()) {
			if (entry.getValue().equals(tagName)) {
				System.out.println(entry.getKey());
			}
		}

	}

	@Override
	public void addUser(User user) {
		users.put(user.getSettings().getUsername(), user);

	}

	@Override
	public void addPost(Post post) {
		posts.put(post, post.getTag());

	}

}
