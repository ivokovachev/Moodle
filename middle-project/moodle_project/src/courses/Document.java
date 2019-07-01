package courses;

import exceptions.NameException;
import exceptions.ObjectCreationException;
import exceptions.PathException;
import helper.Helper;

public class Document {

	private String title; // required
	private String path; // required

	Document(String title, String path) throws ObjectCreationException {
		try {
			this.setTitle(title);
			this.setPath(path);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ObjectCreationException("Cannot create documet", e);
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Document)) {
			return false;
		}

		Document d = (Document) obj;

		return this.title.equals(d.title);
	}

	@Override
	public int hashCode() {
		return this.title.hashCode() * this.path.hashCode();
	}

	@Override
	public String toString() {
		return this.title;
	}

	public String getTitle() {
		return this.title;
	}

	private void setTitle(String title) throws NameException {
		if (!Helper.isValid(title)) {
			throw new NameException("Invalid document title!");
		}

		this.title = title;
	}

	private void setPath(String path) throws PathException {
		if (path == null) {
			throw new PathException("Invalid document path!");
		}

		this.path = path;
	}

}
