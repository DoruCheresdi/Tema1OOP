package entertainment;

import user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Cntains information about a movie
 */
public class Movie extends Video {
    /**
     * Duration of the movie
     */
    private int duration;
    /**
     * Ratings of the users about the movie:
     */
    private List<Double> ratings;

    public Movie(final String name, final int year, final List<Genre> genres,
                 final List<String> actors, final int duration) {
        super(name, year, genres, actors);
        this.duration = duration;
        ratings = new ArrayList<>();
    }

    public Movie(final String name, final int year, final List<Genre> genres,
                 final List<String> actors, final int duration,
                 final List<Double> ratings) {
        super(name, year, genres, actors);
        this.duration = duration;
        this.ratings = ratings;
    }

    /**
     * Method to add a rating to a movie
     * @param rating rating to be added
     * @param season unused parameter
     */
    @Override
    public void addRating(final Double rating, final int season) {
        ratings.add(rating);
    }

    /**
     * Method that calculates the rating of a movie
     * @return rating of the movie
     */
    @Override
    public Double getRating() {
        return ratings.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Method that determines if a movie has been rated by a
     * certain user
     * @param user which we want to know if they have rated
     * @param season that user would have rated
     * @return
     */
    public boolean hasBeenRated(final User user, final int season) {
        if (user.getRatedVideos().containsKey(name)) {
            return true;
        }
        return false;
    }

    /**
     * getter for duration attribute
     * @return duration attribute
     */
    public int getDuration() {
        return duration;
    }

    /**
     * setter for duration attribute
     * @param duration attribute to be set
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * getter for ratings attribute
     * @return
     */
    public List<Double> getRatings() {
        return ratings;
    }

    /**
     * setter for ratings attribute
     * @param ratings
     */
    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }
}
