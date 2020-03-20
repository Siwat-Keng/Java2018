package Exception;

@SuppressWarnings("serial")
public class MyInvalidException extends Exception{
	public MyInvalidException(String message) {
		super(message);
	}
}
