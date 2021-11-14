package solve;

import action.Action;
import data.Database;

/**
 * Class that contains functions for solving actions
 */
public class ActionSolver {

    /**
     * Method to solve the actions from the database;
     */
    public void solveActions() {
        Database db = Database.getDatabase();
        for (Action action
                : db.getActions()) {
            action.solveAction();
        }
    }
}
