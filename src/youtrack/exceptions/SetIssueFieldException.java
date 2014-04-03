package youtrack.exceptions;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class SetIssueFieldException extends Exception {

	private final String errorMessage;

	public SetIssueFieldException(String errorMessage) {
		super("Field value cannot be set: " + errorMessage);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
