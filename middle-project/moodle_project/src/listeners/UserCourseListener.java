package listeners;

import exceptions.NameException;
import exceptions.NullObjectException;
import system.WebSystem2;

public class UserCourseListener extends CourseListener {

	private static final int VIEW_GRADE = 3;

	private static final int EXIT_COURSE_MENU = 4;

	private static CourseListener instance = null;

	public static CourseListener getInstance() {
		if (UserCourseListener.instance == null) {
			UserCourseListener.instance = new UserCourseListener();
		}

		return UserCourseListener.instance;
	}

	@Override
	public void showMenu() {

		System.out.println("Choose option:\n");

		System.out.println("1 - View course");
		System.out.println("2 - View participants");
		System.out.println("3 - View grade");
		System.out.println();
		System.out.println("4 - Exit course menu");
		System.out.println("0 - Exit");

	}

	@Override
	public void execute(int command) throws NullObjectException, NameException {

		switch (command) {
		case VIEW_GRADE:
			CourseListener.getCourse().viewGrade(ActiveProfileListener.getUser());
			return;
		case EXIT_COURSE_MENU:
			CourseListener.setCourse(null);
			WebSystem2.getInstance().setListener(UserCommandListener.getInstance());
			return;
		default:
			super.execute(command);
		}
	}

}