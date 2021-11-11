package entertainment;



import java.util.List;


/**
 * Contains general information about a video, whether movie or tv show
 */
public class Video {
    /**
     * Name of the video
     */
    protected final String name;

    /**
     * Year of the video
     */
    protected final int year;
    /**
     * The genres of the video
     */
    protected List<Genre> genres;
    /**
     * Actors in the movie
     */
    protected List<String> actors;

    public Video() {
        this.name = null;
        this.year = 0;
        this.genres = null;
        this.actors = null;
    }

    public Video(final String name, final int year, final List<Genre> genres,
                 final List<String> actors) {
        this.name = name;
        this.year = year;
        this.genres = genres;
        this.actors = actors;
    }

    /**
     * getter for name attribute
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getter for year attribute
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * getter for genres attribute
     * @return
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * setter for genres attribute
     * @param genres
     */
    public void setGenres(final List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * getter for actors attribute
     * @return
     */
    public List<String> getActors() {
        return actors;
    }

    /**
     * setter for actors attribute
     * @param actors
     */
    public void setActors(final List<String> actors) {
        this.actors = actors;
    }
}
