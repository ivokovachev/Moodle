package exceptions;

public class PathException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PathException() {
		super();
	}

	public PathException(String message, Throwable cause) {
		super(message, cause);
	}

	public PathException(String message) {
		super(message);
	}

	public PathException(Throwable cause) {
		super(cause);
	}

}
