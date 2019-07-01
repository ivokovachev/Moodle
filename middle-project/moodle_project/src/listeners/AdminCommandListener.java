package listeners;

import exceptions.NameException;
import exceptions.NullObjectException;
import system.UsersStorage;
import system.WebSystem2;
import users.Admin;

public class AdminCommandListener extends ActiveProfileListener {

	private static final int CREATE_COURSE = 5;
	private static final int DEACTIVATE_PROFILE = 6;
	private static final int LOG_OUT = 7;

	private static ActiveProfileListener instance = null;

	public static ActiveProfileListener getInstance() {
		if (AdminCommandListener.instance == null) {
			AdminCommandListener.instance = new AdminCommandListener();
		}
		return AdminCommandListener.instance;
	}

	@Override
	public void showMenu() {

		System.out.println("Choose option:\n");
		System.out.println("1 - View profile");
		System.out.println("2 - Edit profile");
		System.out.println("3 - View my courses");
		System.out.println("4 - Choose course");
		System.out.println("5 - Create course");
		System.out.println("6 - Deactivate profile");
		System.out.println("7 - Log out");
		System.out.println();
		System.out.println("0 - Exit");

	}

	@Override
	public void execute(int command) throws NullObjectException, NameException {

		switch (command) {
		case AdminCommandListener.CREATE_COURSE:
			((Admin) ActiveProfileListener.getUser()).createCourse();
			return;
		case AdminCommandListener.DEACTIVATE_PROFILE:
			UsersStorage.getInstance().remove(ActiveProfileListener.getUser());
			ActiveProfileListener.setUser(null);
			WebSystem2.getInstance().setListener(GuestCommandListener.getInstance());
			return;
		case AdminCommandListener.LOG_OUT:
			ActiveProfileListener.getUser().setOffline();
			ActiveProfileListener.setUser(null);
			WebSystem2.getInstance().setListener(GuestCommandListener.getInstance());
			return;
		default:
			super.execute(command);
		}

	}
}