package users;

import courses.Course;
import exceptions.NameException;
import exceptions.NullObjectException;
import helper.Helper;
import system.CoursesStorage;
import system.WebSystem2;

public class Admin extends User {

	Admin(String username, String password, String firstName, String surname, Address address) {
		super(username, password, firstName, surname, address);
		super.setAdmin(true);
	}

	public void createCourse() {
		System.out.println("Enter name of course:");
		WebSystem2.getScanner().nextLine();
		String name = WebSystem2.getScanner().nextLine();

		if (!Helper.isValid(name)) {
			System.out.println("You entered invalid name for course!");
			return;
		}

		try {
			Course course = Course.getInstance(name, this);
			super.courses.add(course);
			CoursesStorage.getInstance().add(course);
		} catch (NullObjectException | NameException e) {
			System.out.println("You entered invalid name for course!");
			return;
		}

	}

}
