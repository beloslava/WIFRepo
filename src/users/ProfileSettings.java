package users;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileSettings {

	public static final Scanner sc = new Scanner(System.in);

	private static String EMAIL_MESSAGE = "Enter your email address";
	private static String NAME_MESSAGE = "Enter your name";
	private static String USERNAME_MESSAGE = "Enter username";
	private static String PASSWORD_MESSAGE = "Enter password";
	private static String REPEAT_PASSWORD_MESSAGE = "Repeat password";
	private static String AGE_MESSAGE = "Enter your age";
	private static String GENDER_MESSAGE = "Enter your gender";
	private static String COUNTRY_MESSAGE = "Enter your country";
	private static String DESCRIPTION_MESSAGE = "Enter your personal description";
	

	private String email;
	private String name;
	private String username;
	private String password;
	private int age;
	private String gender;
	private String country;
	private String personalDescription;
	private User owner;

	
	public String getPassword() {
		return password;
	}
	
	// profile with email, name, username, password, age
	public ProfileSettings() {

		String email = "";
		do {
			System.out.println(EMAIL_MESSAGE);
			email = sc.nextLine();
		} while (!ProfileSettings.validateMail(email));

		this.email = email;

		String name = "";
		do {
			System.out.println(NAME_MESSAGE);
			name = sc.nextLine();
		} while (name == null);

		this.name = name;

		String username = "";
		do {
			System.out.println(USERNAME_MESSAGE);
			username = sc.nextLine();
		} while (username == null);

		this.username = username;

		String password = "";
		String password2 = "";
		do {
			System.out.println(PASSWORD_MESSAGE);
			password = sc.next();
			System.out.println(REPEAT_PASSWORD_MESSAGE);
			password2 = sc.next();
		} while (!password.equals(password2) || !ProfileSettings.isPaswordStrong(password));

		this.password = password;

		try {
			int age = 0;
			do {
				System.out.println(AGE_MESSAGE);
				age = sc.nextInt();
			} while (age < 0 && age > 200);

			this.age = age;

		} catch (InputMismatchException e) {
			System.out.println("Invalid data");
			return;
		}

		System.out.println("Email address: " + email + "\nName: " + name + "\nusername: " + username + "\nPassword: "
				+ password + "\nAge: " + age);
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}

	// validation of email address
	private static boolean validateMail(String email) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_.]*@[a-zA-Z]*.[a-zA-Z]*");
		Matcher mattcher = pattern.matcher(email);
		boolean valid = mattcher.matches();
		if (valid) {
			return true;
		}
		return false;
	}

	// check if password is strong
	static boolean isPaswordStrong(String password) {
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

	//set gender
	protected void addGender() {
		String sex;
		do {
			System.out.println(GENDER_MESSAGE);
			sex = sc.nextLine();
		} while (!sex.equals("woman") || !sex.equals("man"));
		this.gender = sex;
	}

	//set country
	protected void addCountry() {
		String cntr;
		do {
			System.out.println(COUNTRY_MESSAGE);
			cntr = sc.nextLine();
		} while (cntr == null && cntr.isEmpty());
		this.country = cntr;
	}

	//add personal description
	protected void addPersonalDescription() {
		String description;
		do {
			System.out.println(DESCRIPTION_MESSAGE);
			description = sc.nextLine();
		} while (description == null && description.isEmpty());			
		this.personalDescription = description;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}
}
