package listeners;

import courses.Course;
import exceptions.NameException;
import exceptions.NullObjectException;

public abstract class CourseListener extends CommandListener {

	private static final int VIEW_PARTICIPANTS = 2;

	private static final int VIEW_COURSE = 1;

	private static Course course;

	public static void setCourse(Course course) {
		CourseListener.course = course;
	}

	static Course getCourse() {
		return CourseListener.course;
	}

	@Override
	public void execute(int command) throws NullObjectException, NameException {

		switch (command) {
		case VIEW_COURSE:
			CourseListener.course.view();
			return;
		case VIEW_PARTICIPANTS:
			CourseListener.course.viewParticipants();
			return;
		default:
			System.out.println("Invalid command!");
			return;
		}
	}

}
