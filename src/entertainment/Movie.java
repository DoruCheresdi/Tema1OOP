package entertainment;

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

    public Movie(final String name, final int year, final List<String> genres,
                 final List<String> actors, final int duration) {
        super(name, year, genres, actors);
        this.duration = duration;
    }

    public Movie(final String name, final int year, final List<String> genres,
                 final List<String> actors, final int duration,
                 final List<Double> ratings) {
        super(name, year, genres, actors);
        this.duration = duration;
        this.ratings = ratings;
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
