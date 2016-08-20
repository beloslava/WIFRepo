package users;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileSettings {

	public static final Scanner sc = new Scanner(System.in);

	private static final String EMAIL_MESSAGE = "Enter your email address";
	private static final String NAME_MESSAGE = "Enter your name";
	private static final String USERNAME_MESSAGE = "Enter username";
	static final String PASSWORD_MESSAGE = "Enter password";
	static final String REPEAT_PASSWORD_MESSAGE = "Repeat password";
	private static final String AGE_MESSAGE = "Enter your age";
	private static final String GENDER_MESSAGE = "Enter your gender - male or female";
	static final String COUNTRY_MESSAGE = "Enter your country";
	static final String DESCRIPTION_MESSAGE = "Enter your personal description";

	private String email;
	private String name;
	private String username;
	private String password;
	private int age;
	private String gender;
	private String country;
	private String personalDescription;
	private User owner;

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

		// try {
		int age = 0;
		boolean validAge = false;
		do {
			try {
				System.out.println(AGE_MESSAGE);
				age = sc.nextInt();
				if (age > 0 && age < 200) {
					validAge = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Your age must be a number");
				validAge = true;
				break;
			}
		} while (!validAge);

		this.age = age;

		// } catch (InputMismatchException e) {
		// System.out.println("Invalid data");
		// return;
		// }

		String sex;
		do {
			System.out.println(GENDER_MESSAGE);
			sex = sc.next();
		} while (!(sex.equals("female") || sex.equals("male")));
		this.gender = sex;

		do {
			System.out.println(COUNTRY_MESSAGE);
			this.country = sc.nextLine();
		} while (country.isEmpty() || country == null);

		do {
			System.out.println(DESCRIPTION_MESSAGE);
			this.personalDescription = sc.nextLine();
		} while (this.personalDescription.isEmpty() || this.personalDescription == null);

		System.out.println("Email address: " + email + "\nName: " + name + "\nUsername: " + username + "\nPassword: "
				+ password + "\nAge: " + age + "\nGender: " + gender + "\nCountry: " + country
				+ "\nPersonal Description: " + personalDescription);
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

	public void setPersonalDescription(String personalDescription) {
		this.personalDescription = personalDescription;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPersonalDescription() {
		return personalDescription;
	}

	public String getCountry() {
		return country;
	}
}
