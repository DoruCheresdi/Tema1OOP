package action;

/**
 * Class storing information and implementing logic for
 * the recommendation action
 */
public class Recommendation extends Action {
    private String type;
    private String username;
    private String genre;

    public Recommendation(final int actionID, final String type,
                          final String username, final String genre) {
        super(actionID);
        this.type = type;
        this.username = username;
        this.genre = genre;
    }
}
