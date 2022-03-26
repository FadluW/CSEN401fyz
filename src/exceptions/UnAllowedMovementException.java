package exceptions;

public class UnAllowedMovementException extends GameActionException{

	public UnAllowedMovementException() {
		super();
	}

	public UnAllowedMovementException(String message) {
		super(message);
	}

}
