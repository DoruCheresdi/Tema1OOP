package data;

import action.Action;
import actor.Actor;
import entertainment.Video;
import fileio.Writer;
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
    /**
     * List of all video objects
     */
    private List<Video> videos;
    /**
     * List of movie objects
     */
    private List<Video> movies;
    /**
     * List of all show objects
     */
    private List<Video> shows;
    /**
     * List of all actor objects
     */
    private List<Actor> actors;
    /**
     * List of all action objects
     */
    private List<Action> actions;
    /**
     * List of all users objects
     */
    private List<User> users;
    /**
     * JSON array where action output is stored
     */
    private JSONArray dbJSONArray;
    /**
     * writer used to populate dbJSONArray
     */
    private Writer writer;

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

    /**
     * getter for writer attribute
     * @return
     */
    public Writer getWriter() {
        return writer;
    }

    /**
     * setter for writer attribute
     * @param writer
     */
    public void setWriter(final Writer writer) {
        this.writer = writer;
    }
}
