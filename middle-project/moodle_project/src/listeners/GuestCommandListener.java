package listeners;

import exceptions.NameException;
import exceptions.NullObjectException;
import helper.Helper;
import system.WebSystem2;
import users.User;
import users.UserBuilder;

public class GuestCommandListener extends CommandListener {

	private static final int LOG_IN_COMMAND = 1;
	private static final int CREATE_NEW_USER_COMMAND = 2;
	private static final int LIST_COURSES_BY_CATEGORIES_COMMAND = 3;

	private static CommandListener instance = null;

	public static CommandListener getInstance() {
		if (GuestCommandListener.instance == null) {
			GuestCommandListener.instance = new GuestCommandListener();
		}
		return GuestCommandListener.instance;
	}

	@Override
	public void showMenu() {

		System.out.println("Choose option:\n");
		System.out.println("1 - Log in");
		System.out.println("2 - Create new account");
		System.out.println("3 - View courses\n");
		System.out.println("0 - Exit");

	}

	@Override
	public void execute(int command) throws NullObjectException, NameException {

		switch (command) {
		case GuestCommandListener.LOG_IN_COMMAND:
			User user = WebSystem2.getInstance().logUser();
			
			if (Helper.isValid(user)) {
				user = UserBuilder.cast(user);
				ActiveProfileListener.setUser(user);
				if (user.isAdmin()) {
					WebSystem2.getInstance().setListener(AdminCommandListener.getInstance());
				} else {
					WebSystem2.getInstance().setListener(UserCommandListener.getInstance());
				}
			}
			return;
		case GuestCommandListener.CREATE_NEW_USER_COMMAND:
			WebSystem2.getInstance().createNewUser();
			return;
		case GuestCommandListener.LIST_COURSES_BY_CATEGORIES_COMMAND:
			WebSystem2.getInstance().showCourses();
			return;
		default:
			System.out.println("Invalid command!");
			return;
		}
	}

}
