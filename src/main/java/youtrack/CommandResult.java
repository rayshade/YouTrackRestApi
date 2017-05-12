package youtrack;

import org.apache.http.StatusLine;
import youtrack.exceptions.CommandExecutionException;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class CommandResult<T> {
    private final Command<?, T> resultOf;
    protected T result;
    private int resultCode = -1;
    private String description = null;
    private CommandExecutionException exception;
    private Error error;

    public CommandResult(Command<?, T> resultOf, T result) {
        this.result = result;
        this.resultOf = resultOf;
    }

    CommandResult(Command<?, T> resultOf) {
        this.resultOf = resultOf;
    }

    public Error getError() {
        return error;
    }

    public Command<?, T> getResultOf() {
        return resultOf;
    }

    public CommandExecutionException getException() {
        return exception;
    }

    void setException(CommandExecutionException exception) {
        this.exception = exception;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getDescription() {
        return description;
    }

    void setStatus(Error error) {
        this.error = error;
    }

    public boolean success() {
        return resultCode < 400 && (error == null || error.getCode() < 400);
    }

    public T getResult() {
        return result;
    }

    void setResult(T result) {
        this.result = result;
    }

    void setStatus(StatusLine statusLine) {
        description = statusLine.getReasonPhrase();
        resultCode = statusLine.getStatusCode();
    }
}