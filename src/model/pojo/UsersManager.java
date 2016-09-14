package model.pojo;

import java.util.concurrent.ConcurrentHashMap;

import model.db.UserDAO;

public class UsersManager {

	private ConcurrentHashMap<String, User> registerredUsers;//username -> user
	private static UsersManager instance;
	private UsersManager(){
		registerredUsers = new ConcurrentHashMap<>();
		for(User u : UserDAO.getInstance().getAllUsers()){
			registerredUsers.put(u.getEmail(), u);
		}
	}
	
	public synchronized static UsersManager getInstance(){
		if(instance == null){
			instance = new UsersManager();
		}
		return instance;
	}
	
	public boolean validLogin(String email, String password){
		if(!registerredUsers.containsKey(email)){
			return false;
		}
		return registerredUsers.get(email).getPassword().equals(password);
	}
	
	public void regUser(String email, String password, String name, int age, String gender,
			String personalDescription){
		User user = new User(email, password, name,  age, gender, personalDescription);
		registerredUsers.put(email, user);
		UserDAO.getInstance().saveUser(user);
	}
	
}
