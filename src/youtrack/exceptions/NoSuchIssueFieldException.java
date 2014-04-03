package youtrack.exceptions;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class NoSuchIssueFieldException extends Exception {

	private final String fieldName;

	public NoSuchIssueFieldException(String fieldName) {
		super("No such field: " + fieldName);

		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
}
