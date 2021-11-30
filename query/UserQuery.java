package query;

import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Collections;

public final class UserQuery {

    private UserQuery() {
    }
    /**
     * Sorteaza primii n useri care au dat cele mai multe ratinguri folosind
     * campul numberOfRatings din clasa User
     * @param action
     * @param users
     * @return
     */
    public static JSONObject noRatings(final ActionInputData action,
                                       final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        ArrayList<User> activeUsers = new ArrayList<>();
        for (User user : users) {
            activeUsers.add(user);
        }

        if (action.getSortType().equals("asc")) {
            Collections.sort(activeUsers, User.getAscRatings().thenComparing(User.getAscName()));
        } else {
            Collections.sort(activeUsers, User.getDescRatings().thenComparing(User.getDescName()));
        }

        for (int i = 0; i < activeUsers.size(); i++) {
            if (activeUsers.get(i).getNumberOfRatings() == 0.0) {
                activeUsers.remove(i);
                i--;
            }
        }
        if (action.getNumber() != 0) {
            for (int i = activeUsers.size() - 1; i >= action.getNumber(); i--) {
                activeUsers.remove(i);
            }
        }
        file.put("message", "Query result: " + activeUsers);
        return file;
    }
}
