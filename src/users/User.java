package users;

import java.util.Scanner;
import java.util.TreeMap;

import posts.Comment;
import posts.Post;
import site.Site;

public class User implements IUser {
	
	public static final Scanner sc = new Scanner(System.in);

	private static String NAME_MESSAGE = "Enter your name";
	private static String NEW_PASSWORD_MESSAGE = "Enter your new password";
	private static String REPEAT_PASSWORD_MESSAGE = "Repeat password";
	private static String EMAIL_MESSAGE = "Enter your email address";
	private static String PASSWORD_MESSAGE = "Enter password";
	
	private String password;
	private String email;
	private ProfileSettings settings;
	private Site site;
	private TreeMap<Integer, Post> posts; // id post -> post

	@Override
	public void createProfile() {
		this.settings = new ProfileSettings();
		settings.setOwner(this);

	}
	private static boolean isPaswordStrong(String password) {
		boolean upper = false; // upper case letter
		boolean lower = false; // lower case letter
		boolean number = false; // number
		int length = 6; // number of symbols in password

		if (password.length() < length) {
			System.out.println("Your password must be at least 6 symbols");
			return false;
		}

		for (int i = 0; i < password.length(); i++) {
			if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
				upper = true;
			}
			if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') {
				lower = true;
			}
			if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
				number = true;
			}

			if (number && upper && lower) {
				return true;
			}
		}
		System.out.println("Your password is weak! It must cointains upper letter, lower letter and number");
		return false;
	}
	
	@Override
	public void writeComment(Post post) {
		post.setOwner(this);
		post.setComment(new Comment(post));
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
	public void changePassword() {
		String pass;
		do{
		System.out.println(PASSWORD_MESSAGE);
		pass=sc.nextLine();
		}
		while(!pass.equals(this.password));
		String newPassword="";
		String newPassword2="";
		do {
			System.out.println(NEW_PASSWORD_MESSAGE);
			newPassword = sc.next();
			System.out.println(REPEAT_PASSWORD_MESSAGE);
			newPassword2 = sc.next();
		} while (!newPassword.equals(newPassword2) || !isPaswordStrong(newPassword));

		this.password=newPassword;
		System.out.println("Your password is changed!");

	}

	@Override
	public void deletePost(Post post) {
		post = null;

	}

	@Override
	public void uploadPost() {
		

	}

	@Override
	public void makePost(Post post, String title, String tag) {
		

	}


	@Override
	public void changeName(String newName) {
		String name = "";
		do {
			System.out.println(NAME_MESSAGE);
			name = sc.nextLine();
		} while (name == null);

		this.settings.setName(name);
		
	}

	@Override
	public void changeSettings() {
		int ch;
		System.out.println("Choose:");
		System.out.println("1 for change your name");
		System.out.println("2 for change your password");
		System.out.println("3 for change your country");
		System.out.println("4 for change your description");
		System.out.println("0 for exit");
		do{
			System.out.println("Enter your choice");
			ch=sc.nextInt();
			switch (ch) {
			case 1:
				System.out.println("Enter new name");
				String newName=sc.nextLine();
				this.changeName(newName);
				break;
			case 2:
				this.changePassword();
				break;
				case 3:
					this.settings.addCountry();
					break;
				case 4:
					this.settings.addPersonalDescription();
					break;
				case 0:
					return;
			default:
				break;
			}
		}while(ch!=0);
		
	}
	@Override
	public void addFeatures() {
		this.settings.addCountry();
		this.settings.addGender();
		this.settings.addPersonalDescription();
	}

	public String getEmail() {
		return email;
	}
}
