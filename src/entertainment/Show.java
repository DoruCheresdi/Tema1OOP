package entertainment;


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

    public Show(final String name, final int year, final List<String> genres,
                final List<String> actors, final int numberSeasons,
                final List<Season> seasons) {
        super(name, year, genres, actors);
        this.numberSeasons = numberSeasons;
        this.seasons = seasons;
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
