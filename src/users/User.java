package users;

import java.util.Scanner;
import java.util.TreeMap;

import posts.Comment;
import posts.Post;
import site.Site;

public class User implements IUser {

	public static final Scanner sc = new Scanner(System.in);

	private static String NAME_MESSAGE = "Enter new name";
	private static String NEW_PASSWORD_MESSAGE = "Enter your new password";
	private static String REPEAT_PASSWORD_MESSAGE = "Repeat password";
	private static String PASSWORD_MESSAGE = "Enter password";

	private String password;
	private ProfileSettings settings;
	private Site site;
	private TreeMap<Integer, Post> posts; // id post -> post

	public ProfileSettings getSettings() {
		return settings;
	}

	public User(){
		this.posts = new TreeMap<Integer, Post>();
	}
	
	@Override
	public void createProfile() {
		this.settings = new ProfileSettings();
		settings.setOwner(this);
		this.password = settings.getPassword();

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
		do {
			System.out.println(PASSWORD_MESSAGE);
			pass = sc.nextLine();
		} while (!pass.equals(this.password));
		String newPassword = "";
		String newPassword2 = "";
		do {
			System.out.println(NEW_PASSWORD_MESSAGE);
			newPassword = sc.next();
			System.out.println(REPEAT_PASSWORD_MESSAGE);
			newPassword2 = sc.next();
		} while (!newPassword.equals(newPassword2) || !ProfileSettings.isPaswordStrong(newPassword));

		this.password = newPassword;
		System.out.println("Your password is changed!");

	}

	@Override
	public void deletePost(Post post) {
		post = null;

	}

	// upload the post on the site
	@Override
	public void uploadPost() {

	}

	@Override
	public void makePost(Post post) {
		this.posts.put(post.id, post);

	}

	// change name
	@Override
	public void changeName(String newName) {
		String name = "";
		do {
			name = sc.nextLine();
		} while (name == null);

		this.settings.setName(name);
		// System.out.println(this.settings.getName());
	}

	// change the profile settings - name, pass, country, description
	@Override
	public void changeSettings() {
		int ch;
		System.out.println("Choose:");
		System.out.println("1 for change your name");
		System.out.println("2 for change your password");
		System.out.println("3 for change your country");
		System.out.println("4 for change your description");
		System.out.println("5 for adding your gender");
		System.out.println("0 for exit");
		do {
			System.out.println("Enter your choice");
			ch = sc.nextInt();
			switch (ch) {
			case 1:
				System.out.println(NAME_MESSAGE);
				String newName = sc.nextLine();
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
			case 5:
				this.settings.addGender();
				break;
			case 0:
				return;
			default:
				break;
			}
		} while (ch != 0);

	}

}
