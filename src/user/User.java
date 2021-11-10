package user;

import java.util.List;
import java.util.Map;

public class User {
    private String username;
    private String subscription;
    private Map<String, Integer> history;
    private List<String> favourite;

    public User(final String username, final String subscription,
                final Map<String, Integer> history, final List<String> favourite) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        this.favourite = favourite;
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
    public void setUsername(String username) {
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
    public void setSubscription(String subscription) {
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
    public void setHistory(Map<String, Integer> history) {
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
    public void setFavourite(List<String> favourite) {
        this.favourite = favourite;
    }
}
