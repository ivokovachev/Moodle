package listeners;

import exceptions.NameException;
import exceptions.NullObjectException;
import system.UsersStorage;
import system.WebSystem2;

public class UserCommandListener extends ActiveProfileListener {

	private static final int DEACTIVATE_PROFILE = 5;

	private static final int LOG_OUT = 6;

	private static ActiveProfileListener instance = null;

	public static ActiveProfileListener getInstance() {
		if (UserCommandListener.instance == null) {
			UserCommandListener.instance = new UserCommandListener();
		}
		return UserCommandListener.instance;
	}

	@Override
	public void showMenu() {

		System.out.println("Choose option:\n");
		System.out.println("1 - View profile");
		System.out.println("2 - Edit profile");
		System.out.println("3 - View my courses");
		System.out.println("4 - Choose course");
		System.out.println("5 - Deactivate profile");
		System.out.println("6 - Log out");
		System.out.println();
		System.out.println("0 - Exit");

	}

	@Override
	public void execute(int command) throws NullObjectException, NameException {

		switch (command) {
		case UserCommandListener.DEACTIVATE_PROFILE:
			UsersStorage.getInstance().remove(ActiveProfileListener.getUser());
			ActiveProfileListener.setUser(null);
			WebSystem2.getInstance().setListener(GuestCommandListener.getInstance());
			return;
		case UserCommandListener.LOG_OUT:
			ActiveProfileListener.getUser().setOffline();
			ActiveProfileListener.setUser(null);
			WebSystem2.getInstance().setListener(GuestCommandListener.getInstance());
			return;
		default:
			super.execute(command);
		}

	}
}
