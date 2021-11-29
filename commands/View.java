package commands;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;


import java.util.ArrayList;

public class View {

    /**
     * @param action
     * @param users
     * @return
     */
    public static JSONObject addToViewed(final ActionInputData action,
                                         final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        for (User user : users) {
            if (user.getUser().getUsername().equals(action.getUsername())) {
                if (!user.getUser().getHistory().containsKey(action.getTitle())) {
                    user.getUser().getHistory().put(action.getTitle(), 1);
                    file.put("message", "success -> " + action.getTitle() + " was viewed with total views of " + 1);
                } else {
                    user.getUser().getHistory().put(action.getTitle(), user.getUser().getHistory().get(action.getTitle()) + 1);
                    file.put("message", "success -> " + action.getTitle() + " was viewed with total views of " + user.getUser().getHistory().get(action.getTitle()));
                }
                break;
            }
        }

        return file;
    }
}
