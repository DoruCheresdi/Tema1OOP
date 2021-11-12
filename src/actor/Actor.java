package actor;

import data.Database;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;

/**
 * Contains information about an actor
 */
public class Actor {
    /**
     * Name of actor
     */
    private String name;
    /**
     * Description of actor
     */
    private String description;
    /**
     * Awards the actor has received
     */
    private Map<ActorsAwards, Integer> awards;
    /**
     * List of videos the actor has played in
     */
    private List<String> videos;
    /**
     * Average of the ratings of the videos the actor
     * has played in
     */
    private Double rating;

    public Actor(final String name, final String description,
                 final Map<ActorsAwards, Integer> awards,
                 final List<String> videos) {
        this.name = name;
        this.description = description;
        this.awards = awards;
        this.videos = videos;
    }

    /**
     * Method that updates an actor's rating by averaging the
     * ratings of the videos he appeared in.
     */
    public void updateRating() {
        Database database = Database.getDatabase();

        rating = videos.stream().map(s -> database.getVideos().stream()
                .filter(video -> video.getName().equals(s)).findAny().orElse(null))
                .filter(Objects::nonNull)
                .filter(video -> video.getRating() > 0)
                .map(video -> video.getRating())
                .mapToDouble(Double::doubleValue).average().orElse(0);
    }

    /**
     * Method that returns the total number of awards of an actor
     * @return total awards of actor
     */
    public int getAwardsNumber() {
        return awards.entrySet().stream().map(entry -> entry.getValue())
                .mapToInt(Integer::intValue).sum();
    }

    /**
     * getter for name attribute
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name attribute
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * getter for description attribute
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description attribute
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * getter for awards attribute
     * @return
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     * setter for awards attribute
     * @param awards
     */
    public void setAwards(final HashMap<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    /**
     * getter for rating attribute
     * @return
     */
    public Double getRating() {
        return rating;
    }
}
