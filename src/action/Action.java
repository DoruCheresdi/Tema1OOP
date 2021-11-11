package action;

/**
 * Class that stores general action information and methods
 */
public abstract class Action {
    protected int actionID;

    public Action(final int actionID) {
        this.actionID = actionID;
    }

    /**
     * Method called to solve an action, this method is
     * meant to be overridden by child classes with full
     * functionality.
     */
    public abstract void solveAction();
}
