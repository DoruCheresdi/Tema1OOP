package action;

import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import data.Database;
import org.json.simple.JSONObject;
import utils.Utils;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Class storing information and implementing logic for
 * the query action
 */
public class Query extends Action {
    private String objectType;
    private int number;
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
                ;
                break;
            case Constants.FAVORITE:
                ;
                break;
            case Constants.LONGEST:
                ;
                break;
            case Constants.MOST_VIEWED:
                ;
                break;
            case Constants.NUM_RATINGS:
                ;
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

    private void addActorQueryToJson(List<Actor> list) {
        Database database = Database.getDatabase();
        JSONObject jsonObject = new JSONObject();

        String message = new String("Query result: [");
        int firstElement = 1;
        for (Actor actor :
                list) {
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
                .sorted((actor1,actor2) -> {
                    int awards1 = actor1.getAwardsNumber();
                    int awards2 = actor2.getAwardsNumber();
                    if (sortType.equals("asc")) {
                        if (awards1 > awards2) {
                            return 1;
                        } else if (awards1 < awards2) {
                            return -1;
                        } else return actor1.getName()
                                .compareTo(actor2.getName());
                    } else {
                        if (awards1 < awards2) {
                            return 1;
                        } else if (awards1 > awards2) {
                            return -1;
                        } else return actor2.getName()
                                .compareTo(actor1.getName());
                    }
                }).limit(number).collect(Collectors.toList());

        addActorQueryToJson(sortList);
    }

    private boolean containsAllAwards(Actor actor) {
        for (String awardString :
                filters.get(3)) {
            ActorsAwards award = Utils.stringToAwards(awardString);
            if (!actor.getAwards().containsKey(award))
                return false;
        }
        return true;
    }

    private void filterQuery() {
        Database database = Database.getDatabase();
        List<Actor> sortList = database.getActors().stream()
                .filter(actor -> hasAllKeywords(actor))
                .sorted((actor1, actor2) -> actor1.getName()
                        .compareTo(actor2.getName()))
                .limit(number).collect(Collectors.toList());

        addActorQueryToJson(sortList);
    }

    private boolean hasAllKeywords(Actor actor) {
        String lowerCaseDescription = actor.getDescription()
                                     .toLowerCase();
        for (String keyword :
                filters.get(2)) {
            if (!lowerCaseDescription.contains(keyword.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
