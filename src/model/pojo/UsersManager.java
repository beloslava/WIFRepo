package model.pojo;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import model.db.UserDAO;

public class UsersManager implements IUserManager {

	private ConcurrentHashMap<String, User> registerredUsers;// username -> user
	private HashMap<String, Set<String>> followers; //users that follow the user 
	private HashMap<String, Set<String>> followed; //users that the user follows

	
	private static UsersManager instance;

	private UsersManager() {
		followers = new HashMap<>();
		followed = new HashMap<>();

		registerredUsers = new ConcurrentHashMap<>();
		for (User u : UserDAO.getInstance().getAllUsers()) {
			registerredUsers.put(u.getEmail(), u);
			
			for(String userEmail : registerredUsers.keySet()){
				followers.put(userEmail, UserDAO.getInstance().getAllFollowersForUser(userEmail));
				followed.put(userEmail, UserDAO.getInstance().getAllFollowedForUser(userEmail));
			}
		}
		
	}

	public synchronized static UsersManager getInstance() {
		if (instance == null) {
			instance = new UsersManager();
		}
		return instance;
	}

	/**
	 * check if user exist in registerredUsers
	 * @param user email
	 * @return if user exists or not in the collection
	 */
	@Override
	public boolean isUserExists(String email) {
		System.out.println(registerredUsers.containsKey(email) + " " + email);
		return (registerredUsers.containsKey(email));
	}

	/**
	 * get user by user email
	 * @param user email
	 * @return user
	 */
	@Override
	public User getUser(String email) {
		return registerredUsers.get(email);
	}

	/**
	 * check if there is such user in reggisteredUsers
	 * @param user email
	 * @param user pass
	 * @return if the log in is valid or not
	 */
	@Override
	public boolean validLogin(String email, String password) throws UnsupportedEncodingException {
		if (!registerredUsers.containsKey(email)) {
			return false;
		}
		System.out.println(registerredUsers.get(email).getPassword());
		System.out.println(convertToMd5(password));
		return registerredUsers.get(email).getPassword().equals(convertToMd5(password));
	}
	/**
	 * put the user in reggisteredUsers and in db
	 * @param user email, pass, name, avatarPath and posts
	 */
	@Override
	public void regUser(String email, String password, String name, String avatarPath, List<Post> posts, Set<String> followers, Set<String> followed) {
		User user = new User(email, password, name, null, null, avatarPath, posts, followers, followed);
		registerredUsers.put(email, user);
		try {
			registerredUsers.get(email).setPassword(convertToMd5(password));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Something went wrong with crypting the password");
			e.printStackTrace();
		}
		UserDAO.getInstance().saveUser(user);
	}

	/**
	 * change the users fields in reggisteredUsers and db
	 * @param user email, pass, name, gender, about, avatarPath
	 */
	@Override
	public void changeSettings(String name, String password, String gender, String about, String email) 
			throws UnsupportedEncodingException {
		User user = registerredUsers.get(email);
		registerredUsers.get(email).setEmail(email);
		registerredUsers.get(email).setName(name);
		registerredUsers.get(email).setPassword(convertToMd5(password));
		registerredUsers.get(email).setGender(gender);
		registerredUsers.get(email).setAbout(about);

		UserDAO.getInstance().updateUser(user);
	}
	
	public void changeAvatar(String avatarPath, String email) 
			throws UnsupportedEncodingException {
		User user = registerredUsers.get(email);
		//registerredUsers.get(email).setEmail(email);
		registerredUsers.get(email).setAvatarPath(avatarPath);
		UserDAO.getInstance().updateAvatar(user);
	}
	
	public void follow(String userEmail, String followerEmail){
		getUser(userEmail).getFollowers().add(followerEmail); //add follower email in user's set of followers
		getUser(followerEmail).getFollowed().add(userEmail); //add followed email in user's set of users that user follows
		followers.get(userEmail).add(followerEmail); // add follower email in the set of followers
		followed.get(followerEmail).add(userEmail);  // add user email in the set of followed
	}
	
	/**
	 * get all followers for a user
	 * @param user email
	 * @return a set from users emails that follow the user
	 */
	public Set<String> getFollowersByUser(String userEmail){
		return followers.get(userEmail);
	}
	
	/**
	 * get all followed users by user
	 * @param follower email
	 * @return a set from users emails that the user follows
	 */
	public Set<String> getFollowedByUser(String followerEmail){
		return followed.get(followerEmail);
	}
	
	/**
	 * convert the pass in md5
	 * @param user pass
	 * @return crypted pass
	 */
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
