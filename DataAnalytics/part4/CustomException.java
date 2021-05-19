package part4;

/*
 * This part is totally unnecessary, but I just
 * wanted to learn how to make my own exception :)
 */

public class CustomException extends Exception {
	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message);
	}
}
