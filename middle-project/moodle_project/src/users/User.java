package users;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import courses.Course;
import courses.Viewable;
import helper.Helper;
import system.CoursesStorage;
import system.WebSystem2;

public class User {

	private static final int FIRST_NAME_CHANGE = 1;
	private static final int SURNAME_CHANGE = 2;
	private static final int PASSWORD_CHANGE = 3;
	private static final int COUNTRY_CHANGE = 4;
	private static final int CITY_CHANGE = 5;

	static int MIN_LENGTH_PASSWORD = 6;

	private boolean isAdmin;
	private final String username; // required
	private String password; // required
	private String firstName; // required
	private String surname; // required
	private Address address; // optional
	private boolean isOnline; // automatically generated
	protected Set<Course> courses; // sorted by day of creation from new to old

	User(String username, String password, String firstName, String surname, Address address) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.surname = surname;
		this.address = address;
		this.courses = new TreeSet<Course>((c2, c1) -> {
			if (c1.getStart().equals(c2.getStart())) {
				return c1.getTitle().compareTo(c2.getTitle());
			}
			return c1.getStart().compareTo(c2.getStart());
		});
	}

	public void viewProfileInfo() {

		System.out.println("\nProfile Info:\n");

		System.out.println("Username: " + this.username);
		System.out.println("Name: " + this.firstName + " " + this.surname);
		if (this.address != null) {
			System.out.println("Address: " + this.address);
		}

		System.out.println();
	}

	public void listCourses() {

		System.out.println("\nMy courses: ");

		for (Viewable course : this.courses) {
			System.out.println(course);
		}

		System.out.println();
	}

	public void viewCourseInfo(String course) {

		System.out.println("Course");

		if (isThereCourse(course)) {

			Course c = CoursesStorage.getInstance().getCourse(course);

			for (Viewable cs : this.courses) {
				if (cs.equals(c)) {
					cs.view();
					return;
				}
			}
		}

	}

	public void viewParticipantsInCourse(String course) {

		if (isThereCourse(course)) {
			Course c = CoursesStorage.getInstance().getCourse(course);

			for (Viewable cs : this.courses) {
				if (cs.equals(c)) {
					cs.viewParticipants();
					return;
				}
			}
		}
	}

	public void viewCourseGrade(String course) {

		if (isThereCourse(course)) {
			Course c = CoursesStorage.getInstance().getCourse(course);

			for (Viewable cs : this.courses) {
				if (cs.equals(c)) {
					cs.viewGrade(this);
					return;
				}
			}
		}
	}

	public boolean isThereCourse(String course) {
		Course c = CoursesStorage.getInstance().getCourse(course);

		if (c == null) {
			return false;
		}

		if (!this.courses.contains(c)) {
			System.out.println("You are not allowed to view this course!");
			return false;
		}

		return true;
	}

	public void removeCourse(Course course) {
		for (Iterator<Course> it = this.courses.iterator(); it.hasNext();) {
			Course currentCourse = it.next();
			if (currentCourse.equals(course)) {
				it.remove();
				return;
			}
		}

	}

	@Override
	public String toString() {
		return this.firstName + " " + this.surname;
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return Helper.isValid(obj) && obj instanceof User ? this.username.equals(((User) obj).username) : false;
	}

	public static int getMinPasswordLength() {
		return User.MIN_LENGTH_PASSWORD;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setOnline() {
		this.isOnline = true;
	}

	public void setOffline() {
		this.isOnline = false;
	}

	public void addCourse(Course course) {
		this.courses.add(course);
	}

	public String getName() {
		return this.firstName + " " + this.surname;
	}

	public boolean isAdmin() {
		return this.isAdmin;
	}

	protected void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}

	public Address getAddress() {
		return address;
	}

	// you need to update this method
	public void update() {

		showEditAccountMenu();

		int command = WebSystem2.getScanner().nextInt();

		String update = null;

		switch (command) {
		case FIRST_NAME_CHANGE:
			System.out.println("Enter new first name:");
			update = WebSystem2.getScanner().next();

			if (Helper.isValid(update)) {
				this.firstName = update;
				System.out.println("You have successfully edited your profile!");
				return;
			}

			System.out.println("Invalid input!");
			return;

		case SURNAME_CHANGE:
			System.out.println("Enter new surname:");
			update = WebSystem2.getScanner().next();

			if (Helper.isValid(update)) {
				this.surname = update;
				System.out.println("You have successfully edited your profile!");
				return;
			}

			System.out.println("Invalid input!");
			return;

		case PASSWORD_CHANGE:
			System.out.println("Enter new password:");
			update = WebSystem2.getScanner().next();

			if (Helper.isValid(update) && update.length() >= User.MIN_LENGTH_PASSWORD) {
				this.password = update;
				System.out.println("You have successfully edited your profile!");
				return;
			}

			System.out.println("Invalid input!");
			return;

		case COUNTRY_CHANGE:
			System.out.println("Enter new country:");
			WebSystem2.getScanner().nextLine();
			update = WebSystem2.getScanner().nextLine();

			if (update != null) {
				this.address.setCountry(update);
				System.out.println("You have successfully edited your profile!");
				return;
			}

			System.out.println("Invalid input!");
			return;

		case CITY_CHANGE:
			System.out.println("Enter new city:");
			WebSystem2.getScanner().nextLine();
			update = WebSystem2.getScanner().nextLine();

			if (update != null) {
				this.address.setCity(update);
				System.out.println("You have successfully edited your profile!");
				return;
			}

			System.out.println("Invalid input!");
			return;

		default:
			System.out.println("Invalid input!");
			return;
		}

	}

	Set<Course> getCourses() {
		return this.courses;
	}

	void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
	private void showEditAccountMenu() {
		System.out.println("Choose option:\n");
		System.out.println("1 - Change first name");
		System.out.println("2 - Change surname");
		System.out.println("3 - Change password");
		System.out.println("4 - Change country");
		System.out.println("5 - Change city");
	}
}
