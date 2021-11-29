package commands;

import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;


import java.util.ArrayList;

public final class View {

    private View() {
    }

    /**
     * Se marcheaza un videoclip ca vazut. Se actualizeaza numarul de vizionari din HashMap.
     * Daca un videoclip nu a fost vizualizat anterior se adauga un nou element in istoric.
     *
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
                    file.put("message", "success -> " + action.getTitle()
                                                      + " was viewed with total views of " + 1);
                } else {
                    user.getUser().getHistory().put(action.getTitle(),
                                   user.getUser().getHistory().get(action.getTitle()) + 1);
                    file.put("message", "success -> " + action.getTitle()
                                                      + " was viewed with total views of "
                                                      + user.getUser().getHistory()
                                                                      .get(action.getTitle()));
                }
                break;
            }
        }

        return file;
    }
}
