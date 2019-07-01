package system;

import java.util.HashMap;
import java.util.Map;

import exceptions.NullObjectException;
import helper.Helper;
import users.User;

public class UsersStorage {

	private static UsersStorage storage = null;
	private Map<String, User> users;
	private JSONWriter writer = JSONWriter.getInstance();
	private JSONReader reader = JSONReader.getInstance();

	private UsersStorage() {
		this.users = new HashMap<String, User>();
	}

	public static UsersStorage getInstance() {
		if (UsersStorage.storage == null) {
			UsersStorage.storage = new UsersStorage();
		}
		return UsersStorage.storage;
	}

	public User getUser(String username) {

		if (!this.users.containsKey(username)) {
			System.out.println("Such user does not exist!");
			return null;
		}

		User user = this.users.get(username);
		return user;
	}

	public User getUser(String username, String password) {

		if (!this.users.containsKey(username)) {
			System.out.println("Such user does not exist!");
			return null;
		}

		User user = this.users.get(username);

		if (!user.getPassword().equals(password)) {
			System.out.println("Wrong password!");
			return null;
		}

		user.setOnline();
		return user;
	}

	public void addUser(User newUser) {
		if (!Helper.isValid(newUser)) {
			return;
		}

		this.users.put(newUser.getUsername(), newUser);
		System.out.println("Your registration was successful!");
	}

	public void saveUsersDataToJSONFile() {
		try {
			this.writer.writeUsersToJSONFile(this.users);
		} catch (NullObjectException e) {
			System.out.println("Something's gone wrong!");
			return;
		}
	}

	public void loadUSersDataFromJSONFile() {
		try {
			this.users = this.reader.loadUsersData();
		} catch (NullObjectException e) {
			System.out.println("Something's gone wrong!");
			return;
		}
	}

	public void remove(User user) {
		if (user == null) {
			System.out.println("Invalid input!");
			return;
		}

		if (!this.users.containsKey(user.getUsername())) {
			System.out.println("You try to delete user that doesn't exist!");
			return;
		}

		this.users.remove(user.getUsername());
	}

	public static boolean searchUser(String username) {
		return UsersStorage.storage.users.containsKey(username);
	}

}
