package users;

import posts.Post;
import site.Site;

public class Demo {

	public static void main(String[] args) {
		Site s = new Site();

		User ross = new User();
		User mary = new User();
		

//		user.createProfile();
//		user.changePassword();

//		user.changeSettings();
		
		Post maryPost1 = mary.makePost();
		Post rossPost1 = ross.makePost();
		Post maryPost2 = mary.makePost();
		Post rossPost2 = ross.makePost();
		
//		mary.writeComment(rossPost2);
//		ross.reviewPostComments(rossPost2);
		
		mary.searchPost("nature");



		
	}

	
}
