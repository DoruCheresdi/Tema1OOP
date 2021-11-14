package action;

import common.Constants;
import data.Database;
import entertainment.Genre;
import entertainment.Video;
import user.User;
import utils.PairGenreInteger;
import utils.PairStringInteger;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class storing information and implementing logic for
 * the recommendation action
 */
public class Recommendation extends Action {
    /**
     * type of recommendation to be made
     */
    private String type;
    /**
     * the user the recommendations is made for
     */
    private String username;
    /**
     * Genre by which to search for SearchRecommendation
     */
    private String genre;

    public Recommendation(final int actionID, final String type,
                          final String username, final String genre) {
        super(actionID);
        this.type = type;
        this.username = username;
        this.genre = genre;
    }

    /**
     * Method that identifies the type of command to
     * be solved and calls the appropriate function
     */
    @Override
    public void solveAction() {
        switch (type) {
            case Constants.STANDARD:
                standardRecommendation();
                break;
            case Constants.BEST_UNSEEN:
                bestUnseenRecommendation();
                break;
            case Constants.POPULAR:
                popularRecommendation();
                break;
            case Constants.FAVORITE:
                favoriteRecommendation();
                break;
            case Constants.SEARCH:
                searchRecommendation();
                break;
            default:
                break;
        }
    }

    /**
     * Method that implements a recommendation that returns the first unseen
     * video in the database
     */
    private void standardRecommendation() {
        Database database = Database.getDatabase();
        User soughtUser = findUser();
        if (soughtUser == null) {
            return;
        }

        Video recommendedVideo = database.getVideos().stream()
                .filter(video -> !soughtUser.getHistory()
                        .containsKey(video.getName()))
                .findFirst().orElse(null);

        if (recommendedVideo == null) {
            addMessageToJson("StandardRecommendation cannot be applied!");
            return;
        }

        addMessageToJson("StandardRecommendation result: " + recommendedVideo.getName());
    }

    /**
     * Method that finds the user object that is the target of the
     * recommendation
     * @return
     */
    private User findUser() {
        Database database = Database.getDatabase();
        User soughtUser = database.getUsers().stream()
                .filter(user1 -> user1.getUsername().equals(username))
                .findAny().orElse(null);
        return soughtUser;
    }

    /**
     * Method that implements recommendations that return the first unseen
     * video in the database with the highest rating
     */
    private void bestUnseenRecommendation() {
        Database database = Database.getDatabase();
        User soughtUser = findUser();
        if (soughtUser == null) {
            return;
        }

        Video recommendedVideo = database.getVideos().stream()
                .filter(video -> !soughtUser.getHistory()
                        .containsKey(video.getName()))
                .sorted((video1, video2) -> {
                    if (video1.getRating() < video2.getRating()) {
                        return 1;
                    } else if (video1.getRating() > video2.getRating()) {
                        return -1;
                    } else {
                        return getVideoIndex(video1) - getVideoIndex(video2);
                    }
                }).findFirst().orElse(null);

        if (recommendedVideo == null) {
            addMessageToJson("BestRatedUnseenRecommendation cannot be applied!");
            return;
        }

        addMessageToJson("BestRatedUnseenRecommendation result: " + recommendedVideo.getName());
    }

    /**
     * Method that returns the index of the video in the database
     * @param video
     * @return
     */
    private Integer getVideoIndex(final Video video) {
        Database database = Database.getDatabase();
        return database.getVideos().indexOf(video);
    }

    /**
     * Method that implements recommendations that return the fist video from
     * the database from the genre with the most views
     */
    private void popularRecommendation() {
        Database database = Database.getDatabase();
        User soughtUser = findUser();
        if (soughtUser == null) {
            return;
        }

        if (!soughtUser.getSubscription().equals(Constants.PREMIUM)) {
            addMessageToJson("PopularRecommendation cannot be applied!");
            return;
        }

        // calculate the number of views each genre has:
        List<PairGenreInteger> genreViewsList = new ArrayList<>();
        for (User user
                : database.getUsers()) {
            for (Map.Entry<String, Integer> entry
                    : user.getHistory().entrySet()) {
                Video videoWithGenres = database.getVideos().stream()
                        .filter(video ->
                                video.getName().equals(entry.getKey()))
                        .findAny().orElse(null);

                for (Genre genre
                        : videoWithGenres.getGenres()) {
                    if (genreViewsList.stream()
                            .anyMatch(pair -> pair.getKey().equals(genre))) {

                        // found the genre in the list, update the nr of views:
                        PairGenreInteger pair = genreViewsList.stream()
                                .filter(pair2 -> pair2.getKey().equals(genre))
                                .findAny().orElse(null);
                        pair.setValue(pair.getValue() + entry.getValue());
                    } else {

                        // if there is no such genre in the list, then add it
                        genreViewsList
                                .add(new PairGenreInteger(genre,
                                        entry.getValue()));
                    }
                }
            }
        }

        // sort the genre pair list
        List<PairGenreInteger> sortedList = genreViewsList.stream()
                .sorted((pair1, pair2) -> {
            return pair2.getValue() - pair1.getValue();
        }).collect(Collectors.toList());

        // find the video to be recommended, if it exists:
        Video recommendedVideo = null;
        for (PairGenreInteger pair
                : sortedList) {
            recommendedVideo = database.getVideos().stream()
                    .filter(video -> video.getGenres().contains(pair.getKey()))
                    .filter(video -> !soughtUser.getHistory()
                        .containsKey(video.getName()))
                    .findFirst().orElse(null);
            if (recommendedVideo != null) {
                break;
            }
        }

        if (recommendedVideo == null) {
            addMessageToJson("PopularRecommendation cannot be applied!");
            return;
        }

        addMessageToJson("PopularRecommendation result: " + recommendedVideo.getName());
    }

    /**
     * Method that implements recommendations that return the video first video
     * in the database that has been added the most times to favorites.
     */
    private void favoriteRecommendation() {
        Database database = Database.getDatabase();

        User soughtUser = findUser();
        if (soughtUser == null) {
            return;
        }

        // calculate the number of users that added a video to favorites
        List<PairStringInteger> videoPairList = new ArrayList<>();
        for (User user
                : database.getUsers()) {
            for (String videoName
                    : user.getFavourite()) {
                if (videoPairList.stream()
                        .anyMatch(pair -> pair.getKey().equals(videoName))) {
                    PairStringInteger pair = videoPairList.stream()
                            .filter(pair2 -> pair2.getKey().equals(videoName))
                            .findAny().orElse(null);
                    pair.setValue(pair.getValue() + 1);
                } else {
                    videoPairList.add(new PairStringInteger(videoName, 1));
                }
            }
        }

        // find the recommended video:
        Video recommendedVideo = videoPairList.stream()
                .filter(pair -> !soughtUser.getHistory()
                        .containsKey(pair.getKey()))
                .sorted((pair1, pair2) -> {
                    if (pair1.getValue() < pair2.getValue()) {
                        return 1;
                    } else if (pair1.getValue() > pair2.getValue()) {
                        return -1;
                    } else {
                        Video video1 = getVideoWithName(pair1.getKey());
                        Video video2 = getVideoWithName(pair2.getKey());
                        return getVideoIndex(video1) - getVideoIndex(video2);
                    }
                }).map(pair -> getVideoWithName(pair.getKey()))
                .findFirst().orElse(null);


        if (recommendedVideo == null) {
            addMessageToJson("FavoriteRecommendation cannot be applied!");
            return;
        }

        addMessageToJson("FavoriteRecommendation result: " + recommendedVideo.getName());
    }

    /**
     * Method that returns the video object with the videoName name
     * @param videoName name of the video searched for
     * @return video object searched for
     */
    private Video getVideoWithName(final String videoName) {
        Database database = Database.getDatabase();
        return database.getVideos().stream()
                .filter(video -> video.getName().equals(videoName))
                .findAny().orElse(null);
    }

    /**
     * Method that implements a recommendation that returns all unseen videos
     * from a certain genre, sorted by rating and name.
     */
    private void searchRecommendation() {
        Database database = Database.getDatabase();
        User soughtUser = findUser();

        // get list of recommended videos from database:
        List<Video> recommendedVideos = database.getVideos().stream()
                .filter(video -> video.getGenres()
                        .contains(Utils.stringToGenre(genre)))
                .filter(video -> !soughtUser.getHistory()
                        .containsKey(video.getName()))
                .sorted((video1, video2) -> {
                    if (video1.getRating() > video2.getRating()) {
                        return 1;
                    } else if (video1.getRating() < video2.getRating()) {
                        return -1;
                    } else {
                        return video1.getName().compareTo(video2.getName());
                    }
        }).collect(Collectors.toList());

        if (recommendedVideos.size() == 0) {
            addMessageToJson("SearchRecommendation cannot be applied!");
            return;
        }

        addListOfVideosToJson("SearchRecommendation result: [",
                recommendedVideos);
    }

    private void addListOfVideosToJson(final String initialMessage, final List<Video> videos) {
        String message = getMessageFromVideos(videos, initialMessage);

        addMessageToJson(message);
    }
}
