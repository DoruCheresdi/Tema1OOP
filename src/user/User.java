package user;

import entertainment.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String username;
    private String subscription;
    private Map<String, Integer> history;
    private List<String> favourite;
    private Map<String, Integer> ratedVideos;

    public User(final String username, final String subscription,
                final Map<String, Integer> history, final List<String> favourite) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        this.favourite = favourite;
        this.ratedVideos = new HashMap<>();
    }

    /**
     * Method that calculates the number of ratings that the user has given
     * @return number of ratings
     */
    public Integer getNumberOfRatings() {
        return ratedVideos.size();
    }

    /**
     * getter for username attribute
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username attribute
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * getter for subscription attribute
     * @return
     */
    public String getSubscription() {
        return subscription;
    }

    /**
     * setter for subscription attribute
     * @param subscription
     */
    public void setSubscription(final String subscription) {
        this.subscription = subscription;
    }

    /**
     * getter for history attribute
     * @return
     */
    public Map<String, Integer> getHistory() {
        return history;
    }

    /**
     * setter for history attribute
     * @param history
     */
    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    /**
     * getter for favourite attribute
     * @return
     */
    public List<String> getFavourite() {
        return favourite;
    }

    /**
     * setter for favourite attribute
     * @param favourite
     */
    public void setFavourite(final List<String> favourite) {
        this.favourite = favourite;
    }

    /**
     * getter for ratedVideos attribute
     * @return
     */
    public Map<String, Integer> getRatedVideos() {
        return ratedVideos;
    }
}
