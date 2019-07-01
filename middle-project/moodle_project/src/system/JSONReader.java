package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import courses.Course;
import exceptions.NullObjectException;

import users.User;

public class JSONReader {
	
	private static final String PATH_TO_USERS_FILE = ".\\users_json_files\\users.json";
	private static final String PATH_TO_COURSES_FILE = ".\\courses_json_files\\courses.json";
	
	private static JSONReader instance = null;
	private Gson gson;
	
	private JSONReader() {
		this.gson = new GsonBuilder().create();
	}
	
	public static JSONReader getInstance() {
		if(JSONReader.instance == null) {
			JSONReader.instance = new JSONReader();
		}
		
		return JSONReader.instance;
	}
	
	public Map<String, User> loadUsersData() throws NullObjectException {
		
		File file = new File(PATH_TO_USERS_FILE);
		if(!file.exists()) {
			return null;
		}
		
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(
				new FileInputStream(file))))
		{	
			Type type = new TypeToken<Map<String, User>>(){}.getType();
			return gson.fromJson(buffer, type);
			
		} catch (IOException e1) {
			System.out.println("Something's gone wrong with file!");
			return null;
		}	
		
	}
	
	public Map<String, Course> loadCoursesData() throws NullObjectException {
		
		File file = new File(PATH_TO_COURSES_FILE);
		if (!file.exists()) {
			return null;
		}

		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {

			Type type = new TypeToken<Map<String, Course>>() {}.getType();
			return gson.fromJson(buffer, type);
		} catch (IOException e1) {
			System.out.println("Something's gone wrong with file!");
			return null;
		}
	}
	
}
