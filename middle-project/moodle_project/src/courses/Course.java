package courses;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.google.gson.annotations.Expose;

import exceptions.NameException;
import exceptions.NullObjectException;
import exceptions.ObjectCreationException;
import helper.Helper;
import system.UsersStorage;
import users.Admin;
import users.User;

public class Course implements Updatable {

	private static final int COEFF_RANDOM = 20;
	
	private String title; // required
	private final LocalDate start; // automatically generation when course is created
	private transient Admin lecturer; // required!
	private Map<String, Float> students; // elements in it are optional but memory must be allocated
	private Map<String, Set<Document>> sections; // elements in it are optional but memory must be allocated

	Course(String title, Admin lecturer) {
		this.title = title;
		this.lecturer = lecturer;
		this.start = LocalDate.now();
		this.students = new HashMap<String, Float>();
		this.sections = new LinkedHashMap<String, Set<Document>>();
	}

	@Override
	public String toString() {
		return this.title;
	}

	// it is not necessary to have factory method
	public static Course getInstance(String title, Admin lecturer) throws NullObjectException, NameException {
		if (!Helper.isValid(title)) {
			throw new NameException("Invalid course title!");
		}

		if (!Helper.isValid(lecturer)) {
			throw new NullObjectException("Invalid lecturer!");
		}

		return new Course(title, lecturer);
	}

	@Override
	public void view() {
		for (Entry<String, Set<Document>> entry : this.sections.entrySet()) {
			System.out.println(entry.getKey() + "\n");

			entry.getValue().forEach(System.out::println);

			System.out.println();
		}
	}

	@Override
	public void addMember(String username) throws NullObjectException {

		User user = UsersStorage.getInstance().getUser(username);

		if (!Helper.isValid(user)) {
			return;
		}

		if (this.students.containsKey(user.getName())) {
			System.out.println("User " + user.getUsername() + " is already in " + this.getTitle() + " course.");
			return;
		}

		this.students.put(user.getName(), 0f);
		user.addCourse(this);
	}

	@Override
	public void removeMember(String username) {

		User user = UsersStorage.getInstance().getUser(username);

		// in method add we have validation for null user but we can return if user is
		// null or collection doesn't contains it

		if (!Helper.isValid(user) || !this.students.containsKey(user.getName())) {
			return;
		}

		this.students.remove(user.getName());
		user.removeCourse(this);
		System.out.println("You removed user " + user.getUsername() + " from " + this.getTitle() + " course.");
	}

	@Override
	public void addSection(String sectionTitle) throws NameException {

		if (!Helper.isValid(sectionTitle)) {
			System.out.println("Invalid section title!");
			return;
		}

		if (this.sections.containsKey(sectionTitle)) {
			System.out.println("You already have " + sectionTitle + " section");
			return;
		}

		this.sections.put(sectionTitle, new HashSet<Document>());

	}

	@Override
	public void removeSection(String sectionTitle) {

		if (!Helper.isValid(sectionTitle)) {
			System.out.println("Invalid section title!");
			return;
		}

		if (!this.sections.containsKey(sectionTitle)) {
			System.out.println("You try to delete section that doesn't exist!");
			return;
		}

		this.sections.remove(sectionTitle);
		System.out.println("You removed section " + sectionTitle + " from " + this.getTitle() + " course.");
	}

	@Override
	public void addDocument(String sectionTitle, String document) throws NameException {
		if (!Helper.isValid(sectionTitle)) {
			System.out.println("Invalid section title!");
		}

		if (!Helper.isValid(document)) {
			System.out.println("Invalid document!");
		}

		if (!this.sections.containsKey(sectionTitle)) {
			System.out.println("There is no such section in " + this.getTitle() + " course.");
			return;
		}

		Document d = null;

		try {
			d = new Document(document, "D:\folder" + new Random().nextInt(COEFF_RANDOM));
		} catch (ObjectCreationException e) {
			System.out.println("Invalid input!");
			return;
		}

		this.sections.get(sectionTitle).add(d);
	}

	@Override
	public void removeDocument(String sectionTitle, String document) {
		if (!Helper.isValid(sectionTitle) || !Helper.isValid(document)) {
			System.out.println("You entered invalid section title or invalid document!");
			return;
		}

		if (!this.sections.containsKey(sectionTitle)) {
			System.out.println("There is no such section in " + this.getTitle() + " course.");
			return;
		}

		Document d = null;

		try {
			d = new Document(document, "");
		} catch (ObjectCreationException e) {
			System.out.println("Invalid input!");
		}

		this.sections.get(sectionTitle).remove(d);
	}

	@Override
	public int hashCode() {
		return this.title.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return Helper.isValid(obj) && obj instanceof Course ? this.title.equals(((Course) obj).title) : false;
	}

	// generate getters and setters for everything if we don't need them we will
	// remove them

	public LocalDate getStart() {
		return start;
	}

	public Admin getLecturer() {
		return this.lecturer;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public void viewParticipants() {

		System.out.println("Admin: " + this.lecturer);
		for (String student : this.students.keySet()) {
			System.out.println("Student: " + student);
		}

	}

	@Override
	public void viewGrade(User user) {
		System.out.printf("Your grade is %.2f", this.students.get(user));
	}

}
