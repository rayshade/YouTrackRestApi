package youtrack;
/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public class CommandResultData<T> extends CommandResultBase<T> {
    public CommandResultData(YouTrack youTrack, int responseCode, T result) {
        super(youTrack, responseCode);
        this.result = result;
    }
}
