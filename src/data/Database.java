package data;

import actor.Actor;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;

import java.util.List;

/**
 * Class storing all information about users, videos, actors and actions.
 */
public final class Database {
    /**
     * Database instance, follows Singleton Pattern
     */
    private static Database database;
    private List<Video> videos;
    private List<Movie> movies;
    private List<Show> shows;
    private List<Actor> actors;

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

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(final List<Video> videos) {
        this.videos = videos;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final List<Movie> movies) {
        this.movies = movies;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(final List<Show> shows) {
        this.shows = shows;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(final List<Actor> actors) {
        this.actors = actors;
    }
}
