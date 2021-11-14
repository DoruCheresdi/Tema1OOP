package utils;

import entertainment.Genre;

/**
 * Class containing information about a pair of a Genre and
 * an Integer
 */
public class PairGenreInteger {
    private Genre key;
    private Integer value;

    public PairGenreInteger(final Genre key, final Integer value) {
        this.key = key;
        this.value = value;
    }

    /**
     * getter for key attribute
     * @return key
     */
    public Genre getKey() {
        return key;
    }

    /**
     * setter for key attribute
     * @param key
     */
    public void setKey(final Genre key) {
        this.key = key;
    }

    /**
     * getter for value attribute
     * @return
     */
    public Integer getValue() {
        return value;
    }

    /**
     * setter for value attribute
     * @param value
     */
    public void setValue(final Integer value) {
        this.value = value;
    }
}
