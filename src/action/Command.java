package action;

import common.Constants;
import data.Database;
import entertainment.Video;
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
    private int season;

    public Command(final int actionID, final String type, final String user,
                   final String title, final Double grade, final int season) {
        super(actionID);
        this.type = type;
        this.user = user;
        this.title = title;
        this.grade = grade;
        this.season = season;
    }

    /**
     * Method that identifies the type of command to
     * be solved and calls the appropriate function
     */
    @Override
    public void solveAction() {
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

    private void addMessageToJson(final String message) {
        Database database = Database.getDatabase();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.MESSAGE, message);
        jsonObject.put("id", actionID);
        database.getDbJSONArray().add(jsonObject);
    }

    /**
     * Method to solve commands of the favorite type,
     * adds the film to the list of favorite videos
     * of the user
     */
    private void solveFavoriteCommand() {
        User soughtUser = findUser();
        if (!soughtUser.getHistory().containsKey(title)) {
            addMessageToJson("error -> " + title + " is not seen");
            return;
        } else if (soughtUser.getFavourite().contains(title)) {
            addMessageToJson("error -> " + title
                    + " is already in favourite list");
            return;
        }

        soughtUser.getFavourite().add(title);
        addMessageToJson("success -> " + title + " was added as favourite");
    }

    /**
     * Method to mark a video as viewed. If already
     * viewed, it will increment the number of views
     * in the user's history
     */
    private void solveViewCommand() {
        User soughtUser = findUser();
        if (soughtUser.getHistory().containsKey(user)) {
            soughtUser.getHistory()
                    .put(user, soughtUser.getHistory().get(user) + 1);
        } else {
            soughtUser.getHistory().put(user, 1);
        }

        addMessageToJson("success -> " + title
                + " was viewed with total views of 1");
    }

    private void solveRatingCommand() {
        User soughtUser = findUser();
        if (!soughtUser.getHistory().containsKey(title)) {
            addMessageToJson("error -> " + title + " is not seen");
            return;
        }

        Database database = Database.getDatabase();
        Video videoToRate = database.getVideos().stream().filter(video -> video.getName()
                .equals(title)).findAny().orElse(null);

        if (videoToRate.hasBeenRated(soughtUser, season)) {
            addMessageToJson("error -> " + title + " has been already rated");
            return;
        }

        soughtUser.getRatedVideos().put(videoToRate.getName(), season);
        videoToRate.addRating(grade, season);
        addMessageToJson("success -> " + title + " was rated with "
                + grade + " by " + user);
    }
}
