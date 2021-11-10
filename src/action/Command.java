package action;

/**
 * Class storing information and implementing logic for
 * the command action
 */
public class Command extends Action {
    private String type;
    private String user;
    private String title;
    private Double grade;

    public Command(final int actionID, final String type, final String user,
                   final String title, final Double grade) {
        super(actionID);
        this.type = type;
        this.user = user;
        this.title = title;
        this.grade = grade;
    }
}
