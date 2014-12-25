package youtrack.commands;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class QueryParameters {
    private final String filter;
    private final int max;
    private final int start;

    public QueryParameters(String filter, int max, int start) {
        this.filter = filter;
        this.max = max;
        this.start = start;
    }

    public int getMax() {
        return max;
    }

    public int getStart() {
        return start;
    }

    public String getFilter() {
        return filter;
    }
}