package youtrack.commands;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class CommandResult {

	private final Object data;
	private final int responseCode;

	public CommandResult(Object data, int responseCode) {
		this.data = data;
		this.responseCode = responseCode;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public Object getData() {
		return data;
	}

	public boolean success() {
		return (this.responseCode == 200);
	}
}
