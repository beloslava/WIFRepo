package site;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import posts.Post;
import users.User;

public class Site implements ISite {

	TreeMap<String, User> users; // username -> user
	HashMap<String, Post> posts; // post tag -> post

	public Site() {
		this.users = new TreeMap<>();
		this.posts = new HashMap<>();
	}

	@Override
	public void searchPost(String tagName) {
		for (Iterator<Entry<String, Post>> p = posts.entrySet().iterator(); p.hasNext();) {

			if (posts.get(p).getTag().equals(tagName)) {
				System.out.println(posts.get(p));
			}
		}

	}

	@Override
	public void addUser(User user) {
		users.put(user.getSettings().getUsername(), user);

	}

	@Override
	public void addPost(Post post) {
		posts.put(post.getTag(), post);

	}

}
