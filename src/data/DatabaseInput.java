package data;

import action.Action;
import action.Command;
import action.Query;
import action.Recommendation;
import actor.Actor;
import common.Constants;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.*;
import user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the initial input of data into the database
 */
public class DatabaseInput {
    public DatabaseInput() {}

    /**
     * fills the database with data about videos,
     * users, actors and actions.
     * @param input where the data is extracted from
     */
    public void populateDatabase(final Input input) {
        Database database = Database.getDatabase();
        List<Video> dbVideos = new ArrayList<>();
        List<Movie> dbMovies = new ArrayList<>();
        List<Show> dbShows = new ArrayList<>();
        List<User> dbUsers = new ArrayList<>();
        List<Actor> dbActors = new ArrayList<>();
        List<Action> dbActions = new ArrayList<>();

        for (MovieInputData movieData
                : input.getMovies()) {
            Movie movie = new Movie(movieData.getTitle(), movieData.getYear(),
                                    movieData.getGenres(), movieData.getCast(),
                                    movieData.getDuration());
            dbMovies.add(movie);
            dbVideos.add(movie);
        }

        for (SerialInputData showData
                : input.getSerials()) {
            Show show = new Show(showData.getTitle(), showData.getYear(),
                    showData.getGenres(), showData.getCast(),
                    showData.getNumberSeason(), showData.getSeasons());
            dbShows.add(show);
            dbVideos.add(show);
        }

        for (UserInputData userData
                : input.getUsers()) {
            User user = new User(userData.getUsername(),
                    userData.getSubscriptionType(),
                    userData.getHistory(),
                    userData.getFavoriteMovies());
            dbUsers.add(user);
        }

        for (ActorInputData actorData
                : input.getActors()) {
            Actor actor = new Actor(actorData.getName(),
                    actorData.getCareerDescription(), actorData.getAwards(),
                    actorData.getFilmography());
            dbActors.add(actor);
        }

        for (ActionInputData actionData :
                input.getCommands()) {
            if (actionData.getActionType().equals(Constants.COMMAND)) {
                Command command = new Command(actionData.getActionId(),
                        actionData.getType(), actionData.getUsername(),
                        actionData.getTitle(), actionData.getGrade());
                dbActions.add(command);
            } else if (actionData.getActionType().equals(Constants.QUERY)) {
                Query query = new Query(actionData.getActionId(),
                        actionData.getObjectType(), actionData.getNumber(),
                        actionData.getFilters(), actionData.getSortType(),
                        actionData.getCriteria());
                dbActions.add(query);
            } else if (actionData.getActionType()
                    .equals(Constants.RECOMMENDATION)) {
                Recommendation recommendation = new Recommendation(
                        actionData.getActionId(), actionData.getType(),
                        actionData.getUsername(), actionData.getGenre());
                dbActions.add(recommendation);
            }
        }
    }
}