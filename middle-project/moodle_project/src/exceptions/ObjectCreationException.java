package exceptions;

public class ObjectCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectCreationException() {
		super();
	}

	public ObjectCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectCreationException(String message) {
		super(message);
	}

	public ObjectCreationException(Throwable cause) {
		super(cause);
	}

}
