package data;

import action.Action;
import actor.Actor;
import entertainment.Video;
import org.json.simple.JSONArray;
import user.User;

import java.util.List;

/**
 * Class storing all information about users, videos, actors and actions.
 */
public class Database {
    /**
     * Database instance, follows Singleton Pattern
     */
    private static Database database;
    private List<Video> videos;
    private List<Video> movies;
    private List<Video> shows;
    private List<Actor> actors;
    private List<Action> actions;
    private List<User> users;
    private JSONArray dbJSONArray;

    /**
     * Method to return instance
     * @return the unique instance of the class
     */
    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
            return database;
        }
        return database;
    }

    /**
     * getter for videos attribute
     * @return videos
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     * setter for videos attribute
     * @param videos
     */
    public void setVideos(final List<Video> videos) {
        this.videos = videos;
    }

    /**
     * getter for movies attribute
     * @return movies
     */
    public List<Video> getMovies() {
        return movies;
    }

    /**
     * setter for movies attribute
     * @param movies
     */
    public void setMovies(final List<Video> movies) {
        this.movies = movies;
    }

    /**
     * getter for shows attribute
     * @return shows
     */
    public List<Video> getShows() {
        return shows;
    }

    /**
     * setter for shows attribute
     * @param shows
     */
    public void setShows(final List<Video> shows) {
        this.shows = shows;
    }

    /**
     * getter for actors attribute
     * @return actors
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * setter for actors attribute
     * @param actors
     */
    public void setActors(final List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * getter for actions attribute
     * @return actions
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * setter for actions attribute
     * @param actions
     */
    public void setActions(final List<Action> actions) {
        this.actions = actions;
    }

    /**
     * getter for users attribute
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * setter for users attribute
     * @param users
     */
    public void setUsers(final List<User> users) {
        this.users = users;
    }

    /**
     * getter for dbJSONArray attribute
     * @return dbJSON
     */
    public JSONArray getDbJSONArray() {
        return dbJSONArray;
    }

    /**
     * setter for dbJSONArray attribute
     * @param dbJSONArray
     */
    public void setDbJSONArray(final JSONArray dbJSONArray) {
        this.dbJSONArray = dbJSONArray;
    }
}
