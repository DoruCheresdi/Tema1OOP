package action;

import java.util.List;

/**
 * Class storing information and implementing logic for
 * the query action
 */
public class Query extends Action {
    private String objectType;
    private int number;
    private List<List<String>> filters;
    private String sortType;
    private String criteria;

    public Query(final int actionID, final String objectType, final int number,
                 final List<List<String>> filters, final String sortType,
                 final String criteria) {
        super(actionID);
        this.objectType = objectType;
        this.number = number;
        this.filters = filters;
        this.sortType = sortType;
        this.criteria = criteria;
    }
}
