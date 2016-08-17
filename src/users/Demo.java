package users;

import posts.Post;

public class Demo {

	public static void main(String[] args) {

		User ross = new User();
		
		Post post = new Post(ross, "demo", "");

		User user = new User();
		user.writeComment(post);
		//ross.reviewPostComments(post);
//		user.createProfile();
//		user.changePassword();
		

	}

}
