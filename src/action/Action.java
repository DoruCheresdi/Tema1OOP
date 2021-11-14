package action;

import actor.Actor;
import data.Database;
import entertainment.Video;
import org.json.simple.JSONObject;
import user.User;

import java.util.List;

/**
 * Class that stores general action information and methods
 */
public abstract class Action {
    /**
     * ID of the action
     */
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

    /**
     * message to add the message of the current action to output
     * @param message
     */
    protected void addMessageToJson(final String message) {
        Database database = Database.getDatabase();
        JSONObject jsonObject = database.getWriter()
                .writeFile(actionID, message);
        database.getDbJSONArray().add(jsonObject);
    }

    /**
     * Creates message string from a video object list from their names
     * @param list
     * @param initialMessage
     * @return
     */
    protected String getMessageFromVideos(final List<Video> list,
                                        final String initialMessage) {
        String message = new String(initialMessage);
        int firstElement = 1;
        for (Video video
                : list) {
            if (firstElement == 1) {
                firstElement = 0;
                message = message + video.getName();
            } else {
                message = message + ", " + video.getName();
            }
        }
        message = message + "]";
        return message;
    }

    /**
     * Creates message string from a user object list from their names
     * @param list
     * @param initialMessage
     * @return
     */
    protected String getMessageFromUsers(final List<User> list,
                                       final String initialMessage) {
        String message = new String(initialMessage);
        int firstElement = 1;
        for (User user
                : list) {
            if (firstElement == 1) {
                firstElement = 0;
                message = message + user.getUsername();
            } else {
                message = message + ", " + user.getUsername();
            }
        }
        message = message + "]";
        return message;
    }

    /**
     * Creates message string from an actor object list from their names
     * @param list
     * @param initialMessage
     * @return
     */
    protected String getMessageFromActors(final List<Actor> list,
                                        final String initialMessage) {
        String message = new String(initialMessage);
        int firstElement = 1;
        for (Actor actor
                : list) {
            if (firstElement == 1) {
                firstElement = 0;
                message = message + actor.getName();
            } else {
                message = message + ", " + actor.getName();
            }
        }
        message = message + "]";
        return message;
    }
}
