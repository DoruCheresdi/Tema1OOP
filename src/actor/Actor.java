package actor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
     * List of movies the actor has played in
     */
    private List<String> movies;

    public Actor(final String name, final String description,
                 final Map<ActorsAwards, Integer> awards,
                 final List<String> movies) {
        this.name = name;
        this.description = description;
        this.awards = awards;
        this.movies = movies;
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
}
