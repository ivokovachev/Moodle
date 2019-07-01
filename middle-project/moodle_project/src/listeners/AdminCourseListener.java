package listeners;

import exceptions.NameException;
import exceptions.NullObjectException;
import system.WebSystem2;

public class AdminCourseListener extends CourseListener {

	private static final int EXIT_COURSE_MENU = 9;
	private static final int REMOVE_DOCUMENT = 8;
	private static final int ADD_DOCUMENT = 7;
	private static final int ADD_PARTICIPANT = 3;
	private static final int REMOVE_PARTICIPANT = 4;
	private static final int ADD_SECTION = 5;
	private static final int REMOVE_SECTION = 6;

	private static CourseListener instance = null;

	public static CourseListener getInstance() {
		if (AdminCourseListener.instance == null) {
			AdminCourseListener.instance = new AdminCourseListener();
		}

		return AdminCourseListener.instance;
	}

	@Override
	public void showMenu() {

		System.out.println("Choose option:\n");

		System.out.println("1 - View course");
		System.out.println("2 - View participants");
		System.out.println("3 - Add participant");
		System.out.println("4 - Remove participant");
		System.out.println("5 - Add section");
		System.out.println("6 - Remove section");
		System.out.println("7 - Add document");
		System.out.println("8 - Remove document");
		System.out.println();
		System.out.println("9 - Exit course menu");
		System.out.println("0 - Exit");

	}

	@Override
	public void execute(int command) throws NullObjectException, NameException {

		String username = null;
		String section = null;
		String document = null;

		switch (command) {
		case ADD_PARTICIPANT:
			System.out.println("Enter username of member you want to add:");
			username = WebSystem2.getScanner().next();
			CourseListener.getCourse().addMember(username);
			return;
		case REMOVE_PARTICIPANT:
			System.out.println("Enter username of member you want to remove:");
			username = WebSystem2.getScanner().next();
			CourseListener.getCourse().removeMember(username);
			return;
		case ADD_SECTION:
			System.out.println("Enter title for section you want to add:");
			WebSystem2.getScanner().nextLine();
			section = WebSystem2.getScanner().nextLine();
			CourseListener.getCourse().addSection(section);
			return;
		case REMOVE_SECTION:
			System.out.println("Enter title for section you want to remove:");
			WebSystem2.getScanner().nextLine();
			section = WebSystem2.getScanner().nextLine();
			CourseListener.getCourse().removeSection(section);
			return;
		case ADD_DOCUMENT:
			System.out.println("Enter section title where you want to add document:");
			WebSystem2.getScanner().nextLine();
			section = WebSystem2.getScanner().nextLine();
			System.out.println("Enter document title:");
			document = WebSystem2.getScanner().next();
			CourseListener.getCourse().addDocument(section, document);
			return;
		case REMOVE_DOCUMENT:
			System.out.println("Enter section title from where you want to delete document:");
			WebSystem2.getScanner().nextLine();
			section = WebSystem2.getScanner().nextLine();
			System.out.println("Enter document title:");
			document = WebSystem2.getScanner().next();
			CourseListener.getCourse().removeDocument(section, document);
			return;
		case EXIT_COURSE_MENU:
			CourseListener.setCourse(null);
			WebSystem2.getInstance().setListener(AdminCommandListener.getInstance());
			return;
		default:
			super.execute(command);
		}

	}

}
