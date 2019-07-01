package system;

import java.util.HashMap;
import java.util.Map;

import courses.Course;
import exceptions.NullObjectException;

public class CoursesStorage {

	private static CoursesStorage instance = null;
	private Map<String, Course> courses;
	private JSONWriter writer = JSONWriter.getInstance();
	private JSONReader reader = JSONReader.getInstance();

	private CoursesStorage() {
		this.courses = new HashMap<String, Course>();
	}

	public static CoursesStorage getInstance() {
		if (CoursesStorage.instance == null) {
			CoursesStorage.instance = new CoursesStorage();
		}

		return CoursesStorage.instance;
	}

	public void saveCoursesDataToJSONFile() {
		try {
			this.writer.writeCoursesToJSONFile(this.courses);
		} catch (NullObjectException e) {
			e.printStackTrace();
		}
	}

	public void loadCoursesDataFromJSONFile() {	
		try {
			this.courses = this.reader.loadCoursesData();
		} catch (NullObjectException e) {
			e.printStackTrace();
		}
	}

	public Course getCourse(String course) {
		if (!this.courses.containsKey(course)) {
			System.out.println("There is no such course in system!");
			return null;
		}

		return this.courses.get(course);

	}

	public void listCourses() {
		this.courses.keySet().stream().forEach(System.out::println);
	}

	public void add(Course course) {
		if (course == null) {
			System.out.println("Invalid course");
			return;
		}

		if (!this.courses.containsKey(course.getTitle())) {
			this.courses.put(course.getTitle(), course);
		} else {
			System.out.println("Course with that name already exists!");
			return;
		}

	}

	public void remove(Course course) {
		if (course == null) {
			System.out.println("Invalid course input!");
			return;
		}

		if (!this.courses.containsKey(course.getTitle())) {
			System.out.println("You try to delete course that doesn't exist!");
			return;
		}

		this.courses.remove(course.getTitle());
	}

}
