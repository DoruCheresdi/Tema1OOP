package entertainment;


import user.User;

import java.util.List;

/**
 * Contains information about a show
 */
public class Show extends Video {
    /**
     * Number of seasons a show has
     */
    private int numberSeasons;
    /**
     * List of seasons of a show
     */
    private List<Season> seasons;

    public Show(final String name, final int year, final List<Genre> genres,
                final List<String> actors, final int numberSeasons,
                final List<Season> seasons) {
        super(name, year, genres, actors);
        this.numberSeasons = numberSeasons;
        this.seasons = seasons;
    }

    /**
     * Method to add a rating to a season
     * @param rating rating to be added
     * @param season season to which to add the rating
     */
    @Override
    public void addRating(final Double rating, final int season) {
        seasons.get(season - 1).getRatings().add(rating);
    }

    /**
     * Method that calculates the rating of a show
     * @return rating
     */
    @Override
    public Double getRating() {
        return seasons.stream().filter(season -> season.getRatings().size() > 0)
                .map(season -> season.getRatings().stream()
                .reduce(0.0, Double::sum) / season.getRatings().size())
                .reduce(0.0, Double::sum) / seasons.size();
    }

    /**
     * Method that determines if a show has been rated by a
     * certain user
     * @param user which we want to know if they have rated
     * @param season that user would have rated
     * @return
     */
    public boolean hasBeenRated(final User user, final int season) {
        if (user.getRatedVideos().containsKey(name)) {
            if (user.getRatedVideos().get(name) == season) {
                return true;
            }
        }
        return false;
    }

    /**
     * getter for numberSeasons attribute
     * @return
     */
    public int getNumberSeasons() {
        return numberSeasons;
    }

    /**
     * setter for numberSeasons attribute
     * @param numberSeasons
     */
    public void setNumberSeasons(final int numberSeasons) {
        this.numberSeasons = numberSeasons;
    }

    /**
     * getter for seasons attribute
     * @return
     */
    public List<Season> getSeasons() {
        return seasons;
    }

    /**
     * setter for seasons attribute
     * @param seasons
     */
    public void setSeasons(final List<Season> seasons) {
        this.seasons = seasons;
    }
}
