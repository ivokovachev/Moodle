package users;

import helper.Helper;
import system.UsersStorage;

public abstract class UserBuilder {

	private static final String ADMIN_CREATION_COMMAND = "yes";
	private static final String COMMAND_SKIP_OPTIONAL_FIELDS_REGISTRATION = "\n";

	public static User createUser(String isAdmin, String username, String password, String firstName, String surname,
			String country, String city) {

		if (!UserBuilder.areRequiredFieldsValid(username, password, firstName, surname)) {
			return null;
		}

		if (UsersStorage.searchUser(username)) {
			System.out.println("There is user with this username");
			return null;
		}

		Address userAddress = UserBuilder.generateAddress(country, city);

		if (isAdmin.equals(ADMIN_CREATION_COMMAND)) {
			return new Admin(username, password, firstName, surname, userAddress);
		}

		return new User(username, password, firstName, surname, userAddress);
	}

	private static Address generateAddress(String country, String city) {

		Address address = null;

		if (country.equals(COMMAND_SKIP_OPTIONAL_FIELDS_REGISTRATION)) {
			country = "";
		}

		if (city.equals(COMMAND_SKIP_OPTIONAL_FIELDS_REGISTRATION)) {
			city = "";
		}

		return Address.getAddress(country, city);
	}

	private static boolean areRequiredFieldsValid(String username, String password, String firstName, String surname) {
		if (!Helper.isValid(username)) {
			System.out.println("Invalid username!");
			return false;
		}

		if (!Helper.isValid(password) || password.length() < User.getMinPasswordLength()) {
			System.out.println("Invalid password!");
			return false;
		}

		if (!Helper.isValid(firstName)) {
			System.out.println("Invalid first name!");
			return false;
		}

		if (!Helper.isValid(surname)) {
			System.out.println("Invalid surname!");
			return false;
		}

		return true;
	}

	public static User cast(User user) {
		if (!user.isAdmin()) {
			return user;
		}

		User u = new Admin(user.getUsername(), user.getPassword(), user.getFirstName(), user.getSurname(),
				user.getAddress());
		u.setCourses(user.getCourses());
		UsersStorage.getInstance().addUser(u);
		return u;
	}

}
