package exceptions;

public class NameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameException() {
		super();
	}

	public NameException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameException(String message) {
		super(message);
	}

	public NameException(Throwable cause) {
		super(cause);
	}

}
