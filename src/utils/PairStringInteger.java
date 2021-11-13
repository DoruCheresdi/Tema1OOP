package utils;

/**
 * Class containing information about a pair of a String and
 * an Integer
 */
public class PairStringInteger {
    private String key;
    private Integer value;

    public PairStringInteger(final String key, final Integer value) {
        this.key = key;
        this.value = value;
    }

    /**
     * getter for key attribute
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * setter for key attribute
     * @param key
     */
    public void setKey(final String key) {
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
