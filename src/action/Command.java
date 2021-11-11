package action;

import common.Constants;
import data.Database;
import org.json.simple.JSONObject;
import user.User;

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

    @Override
    public void solveAction() {
        Database database = Database.getDatabase();

        switch (type) {
            case Constants.FAVORITE:
                solveFavoriteCommand();
                break;
            case Constants.VIEW:
                solveViewCommand();
                break;
            case Constants.RATING:
                solveRatingCommand();
                break;
            default:
                break;
        }

    }

    private User findUser() {
        Database database = Database.getDatabase();
        User soughtUser = database.getUsers().stream()
                .filter(user1 -> user1.getUsername().equals(user))
                .findAny().get();
        return soughtUser;
    }
    /**
     * Method to solve commands of the favorite type,
     * adds the film with the title = this.title to the
     * list of favorite videos of the user
     */
    private void solveFavoriteCommand() {
        Database database = Database.getDatabase();
        User soughtUser = findUser();
        soughtUser.getFavourite().add(title);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.MESSAGE, "success -> " + title + " was added as favourite");
        jsonObject.put("id", actionID);
        database.getDbJSONArray().add(jsonObject);
    }

    /**
     * Method to mark a video as viewed. If already
     * viewed, it will increment the number of views
     * in the user's history
     */
    private void solveViewCommand() {
        Database database = Database.getDatabase();
        User soughtUser = findUser();
        if (soughtUser.getHistory().containsKey(user)) {
            soughtUser.getHistory()
                    .put(user, soughtUser.getHistory().get(user) + 1);
        } else {
            soughtUser.getHistory().put(user, 1);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.MESSAGE,
                "success -> " + title + " was viewed with total views of 1");
        jsonObject.put("id", actionID);
        database.getDbJSONArray().add(jsonObject);
    }

    private void solveRatingCommand() {

    }
}
