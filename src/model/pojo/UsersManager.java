package model.pojo;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import model.db.UserDAO;

public class UsersManager implements IUserManager {

	private ConcurrentHashMap<String, User> registerredUsers;// username -> user
	private static UsersManager instance;

	private UsersManager() {

		registerredUsers = new ConcurrentHashMap<>();
		for (User u : UserDAO.getInstance().getAllUsers()) {
			registerredUsers.put(u.getEmail(), u);
		}
	}

	public synchronized static UsersManager getInstance() {
		if (instance == null) {
			instance = new UsersManager();
		}
		return instance;
	}

	@Override
	public boolean isUserExists(String email) {
		System.out.println(registerredUsers.containsKey(email) + " " + email);
		return (registerredUsers.containsKey(email));
	}

	@Override
	public User getUser(String email) {
		return registerredUsers.get(email);
	}

	@Override
	public boolean validLogin(String email, String password) throws UnsupportedEncodingException {
		if (!registerredUsers.containsKey(email)) {
			return false;
		}
		System.out.println(registerredUsers.get(email).getPassword());
		System.out.println(convertToMd5(password));
		return registerredUsers.get(email).getPassword().equals(convertToMd5(password));
	}

	@Override
	public void regUser(String email, String password, String name, String avatarPath, List<Post> posts) {
		User user = new User(email, password, name, null, null, avatarPath, posts);
		registerredUsers.put(email, user);
		try {
			registerredUsers.get(email).setPassword(convertToMd5(password));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Something went wrong with crypting the password");
			e.printStackTrace();
		}
		UserDAO.getInstance().saveUser(user);
	}

	@Override
	public void changeSettings(String email, String password, String name, String gender, String about,
			String avatarPath) throws UnsupportedEncodingException {
		User user = registerredUsers.get(email);
		registerredUsers.get(email).setEmail(email);
		registerredUsers.get(email).setPassword(convertToMd5(password));
		registerredUsers.get(email).setName(name);
		registerredUsers.get(email).setGender(gender);
		registerredUsers.get(email).setAbout(about);
		registerredUsers.get(email).setAvatarPath(avatarPath);

		UserDAO.getInstance().updateUser(user);
	}

	private static String convertToMd5(final String md5) throws UnsupportedEncodingException {
		StringBuffer sb = null;
		try {
			final java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			final byte[] array = md.digest(md5.getBytes("UTF-8"));
			sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (final java.security.NoSuchAlgorithmException e) {
		}
		return sb.toString();
	}

}
