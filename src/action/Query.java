package action;

import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import data.Database;
import entertainment.Video;
import org.json.simple.JSONObject;
import user.User;
import utils.PairStringInteger;
import utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class storing information and implementing logic for
 * the query action
 */
public class Query extends Action {
    private String objectType;
    private Integer number;
    private List<List<String>> filters;
    private String sortType;
    private String criteria;

    public Query(final int actionID, final String objectType, final int number,
                 final List<List<String>> filters, final String sortType,
                 final String criteria) {
        super(actionID);
        this.objectType = objectType;
        this.number = number;
        this.filters = filters;
        this.sortType = sortType;
        this.criteria = criteria;
    }

    /**
     * Method that identifies the type of query that
     * needs to be solved and calls the appropriate
     * function
     */
    @Override
    public void solveAction() {
        switch (criteria) {
            case Constants.AVERAGE:
                averageQuery();
                break;
            case Constants.AWARDS:
                awardsQuery();
                break;
            case Constants.FILTER_DESCRIPTIONS:
                filterQuery();
                break;
            case Constants.RATINGS:
                ratingQuery();
                break;
            case Constants.FAVORITE:
                favoriteQuery();
                break;
            case Constants.LONGEST:
                longestQuery();
                break;
            case Constants.MOST_VIEWED:
                mostViewedQuery();
                break;
            case Constants.NUM_RATINGS:
                numRatingsQuery();
                break;
            default:
                break;
        }
    }

    /**
     * Method that implements a query of actors sorted
     * by the ratings of the videos they have played in.
     * If two actors are
     */
    private void averageQuery() {
        Database database = Database.getDatabase();
        for (Actor actor
                : database.getActors()) {
            actor.updateRating();
        }

        if (sortType.equals("asc")) {
            List<Actor> sortList = database.getActors().stream()
                    .filter(actor -> actor.getRating() > 0).sorted((o1, o2) -> {
                if (o1.getRating() > o2.getRating()) {
                    return 1;
                } else if (o1.getRating() < o2.getRating()) {
                    return -1;
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            }).limit(number).collect(Collectors.toList());

            addActorQueryToJson(sortList);
        } else {
            List<Actor> sortList = database.getActors().stream()
                    .filter(actor -> actor.getRating() > 0).sorted((o1, o2) -> {
                if (o1.getRating() < o2.getRating()) {
                    return 1;
                } else if (o1.getRating() > o2.getRating()) {
                    return -1;
                } else {
                    return o2.getName().compareTo(o1.getName());
                }
            }).limit(number).collect(Collectors.toList());

            addActorQueryToJson(sortList);
        }
    }

    private void addActorQueryToJson(final List<Actor> list) {
        Database database = Database.getDatabase();
        JSONObject jsonObject = new JSONObject();

        String message = new String("Query result: [");
        int firstElement = 1;
        for (Actor actor
                : list) {
            if (firstElement == 1) {
                firstElement = 0;
                message = message + actor.getName();
            } else {
                message = message + ", " + actor.getName();
            }
        }
        message = message + "]";

        jsonObject.put(Constants.MESSAGE, message);
        jsonObject.put("id", actionID);
        database.getDbJSONArray().add(jsonObject);
    }

    private void awardsQuery() {
        Database database = Database.getDatabase();
        List<Actor> sortList = database.getActors().stream()
                .filter(actor -> containsAllAwards(actor))
                .sorted((actor1, actor2) -> {
                    int awards1 = actor1.getAwardsNumber();
                    int awards2 = actor2.getAwardsNumber();
                    if (sortType.equals("asc")) {
                        if (awards1 > awards2) {
                            return 1;
                        } else if (awards1 < awards2) {
                            return -1;
                        } else {
                            return actor1.getName()
                                    .compareTo(actor2.getName());
                        }
                    } else {
                        if (awards1 < awards2) {
                            return 1;
                        } else if (awards1 > awards2) {
                            return -1;
                        } else {
                            return actor2.getName()
                                    .compareTo(actor1.getName());
                        }
                    }
                }).limit(number).collect(Collectors.toList());

        addActorQueryToJson(sortList);
    }

    private boolean containsAllAwards(final Actor actor) {
        for (String awardString
                : filters.get(Constants.FILTER_AWARDS_NR)) {
            ActorsAwards award = Utils.stringToAwards(awardString);
            if (!actor.getAwards().containsKey(award)) {
                return false;
            }
        }
        return true;
    }

    private void filterQuery() {
        Database database = Database.getDatabase();
        List<Actor> sortList = database.getActors().stream()
                .filter(actor -> hasAllKeywords(actor))
                .sorted((actor1, actor2) -> {
                    if (sortType.equals("asc")) {
                        return actor1.getName().compareTo(actor2.getName());
                    } else {
                        return actor2.getName().compareTo(actor1.getName());
                    }
                }).limit(number).collect(Collectors.toList());

        addActorQueryToJson(sortList);
    }

    /**
     * checks if an author's description contains all the keywords of the
     * query
     * @param actor
     * @return
     */
    private boolean hasAllKeywords(final Actor actor) {
        String description = actor.getDescription()
                                     .toLowerCase();



        for (String keyword
                : filters.get(Constants.FILTER_WORDS_NR)) {
            int isFound = 0;
            if (description.contains(" " + keyword.toLowerCase() + " ")) {
                isFound = 1;
            }
            if (description.contains("-" + keyword.toLowerCase() + " ")) {
                isFound = 1;
            }
            if (description.contains(" " + keyword.toLowerCase() + "-")) {
                isFound = 1;
            }
            if (description.contains("-" + keyword.toLowerCase() + "-")) {
                isFound = 1;
            }
            if (description.contains(" " + keyword.toLowerCase() + ",")) {
                isFound = 1;
            }
            if (description.contains(" " + keyword.toLowerCase() + ".")) {
                isFound = 1;
            }
            if (isFound == 0) {
                return false;
            }
        }
        return true;
    }

    private void ratingQuery() {
        Database database = Database.getDatabase();

        List<Video> listToSort = null;
        if (objectType.equals(Constants.MOVIES)) {
            listToSort = database.getMovies();
        } else if (objectType.equals(Constants.SHOWS)) {
            listToSort = database.getShows();
        }

        List<Video> sortList = listToSort.stream()
                .filter(video -> videoHasYear(video))
                .filter(video -> videoHasGenre(video))
                .filter(video -> video.getRating() > 0)
                .sorted((video1, video2) -> {
            if (sortType.equals("asc")) {
                if (video1.getRating() > video2.getRating()) {
                    return 1;
                } else if (video1.getRating() < video2.getRating()) {
                    return -1;
                } else {
                    return video1.getName().compareTo(video2.getName());
                }
            } else {
                if (video1.getRating() < video2.getRating()) {
                    return 1;
                } else if (video1.getRating() > video2.getRating()) {
                    return -1;
                } else {
                    return video2.getName().compareTo(video1.getName());
                }
            }
        }).limit(number).collect(Collectors.toList());

        addVideoQueryToJson(sortList);
    }

    private boolean videoHasYear(final Video video) {
        if (filters.get(0).get(0) != null) {
            return video.getYear() == Integer.parseInt(filters.get(0).get(0));
        }
        return true;
    }

    private boolean videoHasGenre(final Video video) {
        if (filters.get(1).get(0) != null) {
            return video.getGenres()
                    .contains(Utils.stringToGenre(filters.get(1).get(0)));
        }
        return true;
    }

    private void addVideoQueryToJson(final List<Video> list) {
        Database database = Database.getDatabase();
        JSONObject jsonObject = new JSONObject();

        String message = new String("Query result: [");
        int firstElement = 1;
        for (Video video
                : list) {
            if (firstElement == 1) {
                firstElement = 0;
                message = message + video.getName();
            } else {
                message = message + ", " + video.getName();
            }
        }
        message = message + "]";

        jsonObject.put(Constants.MESSAGE, message);
        jsonObject.put("id", actionID);
        database.getDbJSONArray().add(jsonObject);
    }

    private void favoriteQuery() {
        Database database = Database.getDatabase();
        List<PairStringInteger> listToSort = new ArrayList<>();

        if (objectType.equals(Constants.MOVIES)) {
            for (Video movie
                    : database.getMovies()) {
                if (videoHasYear(movie) && videoHasGenre(movie)) {
                    listToSort.add(new PairStringInteger(movie.getName(), 0));
                }
            }
        } else {
            for (Video show
                    : database.getShows()) {
                if (videoHasYear(show) && videoHasGenre(show)) {
                    listToSort.add(new PairStringInteger(show.getName(), 0));
                }
            }
        }

        for (User user
                : database.getUsers()) {
            for (String videoName
                    : user.getFavourite()) {
                if (listToSort.stream()
                        .anyMatch(pair -> pair.getKey().equals(videoName))) {
                    PairStringInteger pair = listToSort.stream()
                            .filter(pair2 -> pair2.getKey().equals(videoName))
                            .findAny().orElse(null);
                    pair.setValue(pair.getValue() + 1);
                }
            }
        }

        List<Video> videoSortedList = listToSort.stream()
                .filter(pair -> pair.getValue() > 0)
                .sorted((pair1, pair2) -> {
            if (sortType.equals(Constants.ASC)) {
                if (pair1.getValue() > pair2.getValue()) {
                    return 1;
                } else if (pair1.getValue() < pair2.getValue()) {
                    return -1;
                } else {
                    return pair1.getKey().compareTo(pair2.getKey());
                }
            } else {
                if (pair1.getValue() < pair2.getValue()) {
                    return 1;
                } else if (pair1.getValue() > pair2.getValue()) {
                    return -1;
                } else {
                    return pair2.getKey().compareTo(pair1.getKey());
                }
            }
                }).map(pair -> database.getVideos().stream()
                        .filter(video -> video.getName().equals(pair.getKey()))
                        .findAny().orElse(null))
                .limit(number).collect(Collectors.toList());

        addVideoQueryToJson(videoSortedList);
    }

    private void longestQuery() {
        Database database = Database.getDatabase();

        List<Video> listToSort = null;
        if (objectType.equals(Constants.MOVIES)) {
            listToSort = database.getMovies();
        } else if (objectType.equals(Constants.SHOWS)) {
            listToSort = database.getShows();
        }

        List<Video> sortList = listToSort.stream()
                .filter(video -> videoHasYear(video))
                .filter(video -> videoHasGenre(video))
                .sorted((video1, video2) -> {
                    if (sortType.equals(Constants.ASC)) {
                        if (video1.getDuration() > video2.getDuration()) {
                            return 1;
                        } else if (video1.getDuration() < video2.getDuration()) {
                            return -1;
                        } else {
                            return video1.getName().compareTo(video2.getName());
                        }
                    } else {
                        if (video1.getDuration() < video2.getDuration()) {
                            return 1;
                        } else if (video1.getDuration() > video2.getDuration()) {
                            return -1;
                        } else {
                            return video2.getName().compareTo(video1.getName());
                        }
                    }
                }).limit(number).collect(Collectors.toList());

        addVideoQueryToJson(sortList);
    }

    private void mostViewedQuery() {
        Database database = Database.getDatabase();
        List<PairStringInteger> listToSort = new ArrayList<>();

        if (objectType.equals(Constants.MOVIES)) {
            for (Video movie
                    : database.getMovies()) {
                if (videoHasYear(movie) && videoHasGenre(movie)) {
                    listToSort.add(new PairStringInteger(movie.getName(), 0));
                }
            }
        } else {
            for (Video show
                    : database.getShows()) {
                if (videoHasYear(show) && videoHasGenre(show)) {
                    listToSort.add(new PairStringInteger(show.getName(), 0));
                }
            }
        }

        for (User user
                : database.getUsers()) {
            for (Map.Entry<String, Integer> entry
                    : user.getHistory().entrySet()) {
                if (listToSort.stream()
                        .anyMatch(pair -> pair.getKey()
                                .equals(entry.getKey()))) {
                    PairStringInteger pair = listToSort.stream()
                            .filter(pair2 -> pair2.getKey()
                                    .equals(entry.getKey()))
                            .findAny().orElse(null);
                    pair.setValue(pair.getValue() + entry.getValue());
                }
            }
        }

        List<Video> videoSortedList = listToSort.stream()
                .filter(pair -> pair.getValue() > 0)
                .sorted((pair1, pair2) -> {
                    if (sortType.equals(Constants.ASC)) {
                        if (pair1.getValue() > pair2.getValue()) {
                            return 1;
                        } else if (pair1.getValue() < pair2.getValue()) {
                            return -1;
                        } else {
                            return pair1.getKey().compareTo(pair2.getKey());
                        }
                    } else {
                        if (pair1.getValue() < pair2.getValue()) {
                            return 1;
                        } else if (pair1.getValue() > pair2.getValue()) {
                            return -1;
                        } else {
                            return pair2.getKey().compareTo(pair1.getKey());
                        }
                    }
                }).map(pair -> database.getVideos().stream()
                        .filter(video -> video.getName().equals(pair.getKey()))
                        .findAny().orElse(null))
                .limit(number).collect(Collectors.toList());

        addVideoQueryToJson(videoSortedList);
    }

    private void numRatingsQuery() {
        Database database = Database.getDatabase();

        List<User> sortedUserList = database.getUsers().stream()
                .filter(user -> user.getNumberOfRatings() > 0)
                .sorted((user1, user2) -> {
            if (sortType.equals(Constants.ASC)) {
                if (user1.getNumberOfRatings() > user2.getNumberOfRatings()) {
                    return 1;
                } else if (user1.getNumberOfRatings()
                        < user2.getNumberOfRatings()) {
                    return -1;
                } else {
                    return user1.getUsername().compareTo(user2.getUsername());
                }
            } else {
                if (user1.getNumberOfRatings() < user2.getNumberOfRatings()) {
                    return 1;
                } else if (user1.getNumberOfRatings()
                        > user2.getNumberOfRatings()) {
                    return -1;
                } else {
                    return user2.getUsername().compareTo(user1.getUsername());
                }
            }
        }).limit(number).collect(Collectors.toList());

        addUserQueryToJson(sortedUserList);
    }

    private void addUserQueryToJson(final List<User> list) {
        Database database = Database.getDatabase();
        JSONObject jsonObject = new JSONObject();

        String message = new String("Query result: [");
        int firstElement = 1;
        for (User user
                : list) {
            if (firstElement == 1) {
                firstElement = 0;
                message = message + user.getUsername();
            } else {
                message = message + ", " + user.getUsername();
            }
        }
        message = message + "]";

        jsonObject.put(Constants.MESSAGE, message);
        jsonObject.put("id", actionID);
        database.getDbJSONArray().add(jsonObject);
    }
}
