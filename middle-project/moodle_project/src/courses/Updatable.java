package courses;

import exceptions.NameException;
import exceptions.NullObjectException;

public interface Updatable extends Viewable {

	void addMember(String username) throws NullObjectException;

	void removeMember(String username);

	void addSection(String sectionTitle) throws NameException;

	void removeSection(String sectionTitle);

	void addDocument(String sectionTitle, String document) throws NameException;

	void removeDocument(String sectionTitle, String document);

}
