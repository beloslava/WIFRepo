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
	public User getUser(String email) {
		return registerredUsers.get(email);
	}

	@Override
	public boolean validLogin(String email, String password) throws UnsupportedEncodingException {
		if (!registerredUsers.containsKey(email)) {
			return false;
		}
		return registerredUsers.get(email).getPassword().equals(convertToMd5(password));
	}

	@Override
	public void regUser(String email, String password, String name, int age, String gender, String about,
			String avatarPath, List<Post> posts) {
		User user = new User(email, password, name, age, gender, about, avatarPath, posts);
		registerredUsers.put(email, user);
		UserDAO.getInstance().saveUser(user);
	}

	@Override
	public void changeSettings(String email, String password, String name, int age, String gender, String about,
			String avatarPath) throws UnsupportedEncodingException {
		User user = registerredUsers.get(email);
		registerredUsers.get(email).setEmail(email);
		registerredUsers.get(email).setPassword(convertToMd5(password));
		registerredUsers.get(email).setName(name);
		registerredUsers.get(email).setAge(age);
		registerredUsers.get(email).setGender(gender);
		registerredUsers.get(email).setAbout(about);
		registerredUsers.get(email).setAvatarPath(avatarPath);

		UserDAO.getInstance().updateUser(user);
		// UserDAO.getInstance().saveUser(user);

	}
	
	private static String convertToMd5(final String md5) throws UnsupportedEncodingException {
        StringBuffer sb=null;
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
