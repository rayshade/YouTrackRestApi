package youtrack.commands.util;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class QueryParameters {
    private final String query;
    private final int max;
    private final int start;

    public QueryParameters(String query, int start, int max) {
        this.query = query;
        this.max = max;
        this.start = start;
    }

    public int getMax() {
        return max;
    }

    public int getStart() {
        return start;
    }

    public String getQuery() {
        return query;
    }
}