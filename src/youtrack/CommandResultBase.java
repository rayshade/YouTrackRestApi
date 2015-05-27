package youtrack;
/**
 * Created by egor.malyshev on 31.03.2014.
 */
public abstract class CommandResultBase<T> {
    protected final YouTrack youTrack;
    protected final int responseCode;
    protected T result;
    public CommandResultBase(YouTrack youTrack, int responseCode) {
        this.youTrack = youTrack;
        this.responseCode = responseCode;
    }
    public int getResponseCode() {
        return responseCode;
    }
    public boolean success() {
        return (this.responseCode == 200);
    }
    public T getResult() {
        return result;
    }
}