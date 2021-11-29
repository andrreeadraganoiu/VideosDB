package commands;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Favorite {

    /**
     * @param action
     * @param movies
     * @param serials
     * @param users
     * @return
     */
    public static JSONObject addToFavorite(final ActionInputData action,
                                           final ArrayList<Movie> movies,
                                           final ArrayList<Serial> serials,
                                           final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());
        User newUser = null;

        for (User user : users) {
            if (user.getUser().getUsername().equals(action.getUsername())) {
                newUser = user;
                break;
            }
        }
        if (!newUser.getUser().getHistory().containsKey(action.getTitle())) {
            file.put("message", "error -> " + action.getTitle() + " is not seen");
            return file;
        }
        if (newUser.getUser().getFavoriteMovies().contains(action.getTitle())) {
            file.put("message", "error -> " + action.getTitle() + " is already in favourite list");
            return file;
        }

        for (Movie m : movies) {
            if (m.getMovie().getTitle().equals(action.getTitle())) {
                newUser.getUser().getFavoriteMovies().add(m.getMovie().getTitle());
                m.updateNoFavoriteAdd();
            }
        }

//        for (Serial s : serials) {
//            if (s.getSerial().getTitle().equals(action.getTitle())) {
//                newUser.getFavoriteSerials().add(s.getSerial().getTitle());
//            }
//        }
        for (Serial s : serials) {
            if (s.getSerial().getTitle().equals(action.getTitle())) {
                newUser.getUser().getFavoriteMovies().add(s.getSerial().getTitle());
                s.updateNoFavoriteAdd();
            }
        }

        file.put("message", "success -> " + action.getTitle() + " was added as favourite");
        return file;
    }
}
