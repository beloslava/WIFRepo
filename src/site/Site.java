package site;

import java.util.HashMap;
import java.util.Iterator;
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
		for (Iterator p =posts.entrySet().iterator(); p.hasNext();) {
			
			if(posts.get(p).getTag().equals(tagName)){
				System.out.println(posts.get(p));
			}
		}
		

	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPost(Post post) {
		// TODO Auto-generated method stub

	}

}
