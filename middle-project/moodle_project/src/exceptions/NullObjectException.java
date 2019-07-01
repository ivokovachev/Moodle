package exceptions;

public class NullObjectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullObjectException() {
		super();
	}

	public NullObjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullObjectException(String message) {
		super(message);
	}

	public NullObjectException(Throwable cause) {
		super(cause);
	}

}
