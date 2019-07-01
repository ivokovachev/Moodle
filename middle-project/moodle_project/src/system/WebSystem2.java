package system;

import java.util.Scanner;

import exceptions.NameException;
import exceptions.NullObjectException;
import helper.Helper;
import listeners.ActiveProfileListener;
import listeners.CommandListener;
import listeners.GuestCommandListener;
import users.User;
import users.UserBuilder;

public class WebSystem2 implements IWebSystem {

	public static final int EXIT_SYSTEM_COMMAND = 0;

	private static WebSystem2 systemInstance = null;
	private UsersStorage usersStorage = null;
	private CoursesStorage coursesStorage = null;
	private CommandListener listener = null;
	public static Scanner scanner = null;

	private WebSystem2() {
		this.usersStorage = UsersStorage.getInstance();
		this.coursesStorage = CoursesStorage.getInstance();
		this.listener = GuestCommandListener.getInstance();
		WebSystem2.scanner = new Scanner(System.in);
	}

	public static WebSystem2 getInstance() {
		if (WebSystem2.systemInstance == null) {
			WebSystem2.systemInstance = new WebSystem2();
		}

		return WebSystem2.systemInstance;
	}

	@Override
	public void start() {

		this.usersStorage.loadUSersDataFromJSONFile();
		this.coursesStorage.loadCoursesDataFromJSONFile();

		int command = -1;

		do {

			try {

				this.listener.showMenu();

				command = scanner.nextInt();

				if (command == WebSystem2.EXIT_SYSTEM_COMMAND) {
					
					User user = ActiveProfileListener.getUser();
					
					if(Helper.isValid(user)) {
						user.setOffline();
					}
					
					this.usersStorage.saveUsersDataToJSONFile();
					this.coursesStorage.saveCoursesDataToJSONFile();
					return;
				}

				try {
					this.listener.execute(command);
				} catch (NullObjectException | NameException e) {
					if (e.getMessage() != null)
						System.out.println(e.getMessage());
				}

			} catch (Exception e) {
				System.out.println("Invalid command!");
				scanner = new Scanner(System.in);
			}

		} while (true);

	}

	public void setListener(CommandListener listener) {
		this.listener = listener;
	}

	public static Scanner getScanner() {
		return WebSystem2.scanner;
	}

	public User logUser() {

		System.out.println("Entry your username:");
		String username = WebSystem2.scanner.next();
		System.out.println("Entry your password:");
		String password = WebSystem2.scanner.next();

		if (!Helper.isValid(username)) {
			System.out.println("Invalid username!");
			return null;
		}

		if (!Helper.isValid(password)) {
			System.out.println("Invalid password!");
			return null;
		}

		return this.usersStorage.getUser(username, password);

	}

	public void createNewUser() {

		System.out.println("Fields marked with * are required!");
		System.out.println("To skip optional information press enter");

		System.out.println("* Enter username:");
		String username = scanner.next();
		System.out.println("* Enter password (min 6 symbols):");
		String password = scanner.next();
		System.out.println("* Enter first name:");
		String firstName = scanner.next();
		System.out.println("* Enter surname: ");
		String surname = scanner.next();
		scanner.nextLine();
		System.out.println("Enter country:");
		String country = scanner.nextLine();
		System.out.println("Enter city:");
		String city = scanner.nextLine();
		System.out.println("Is this admin profile? Yes/No:");
		String isAdmin = scanner.next();

		User newUser = UserBuilder.createUser(isAdmin, username, password, firstName, surname, country, city);
		this.usersStorage.addUser(newUser);
	}

	public void showCourses() {
		this.coursesStorage.listCourses();
	}

}