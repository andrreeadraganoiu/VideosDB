package commands;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;
import utils.Utils;

import java.util.ArrayList;

public final class Favorite {

    private Favorite() {
    }

    /**
     * Adauga un film/serial in lista de favorite a unui utilizator in cazul in care
     * nu exista deja.
     * Pentru aceasta se verifica mai intai daca userul a vazut video-ul.
     *
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

        User user = Utils.returnUser(users, action);

        if (!user.getUser().getHistory().containsKey(action.getTitle())) {
            file.put("message", "error -> " + action.getTitle() + " is not seen");
            return file;
        }
        if (user.getUser().getFavoriteMovies().contains(action.getTitle())) {
            file.put("message", "error -> " + action.getTitle() + " is already in favourite list");
            return file;
        }

        for (Movie m : movies) {
            if (m.getMovie().getTitle().equals(action.getTitle())) {
                user.getUser().getFavoriteMovies().add(m.getMovie().getTitle());
            }
        }

        for (Serial s : serials) {
            if (s.getSerial().getTitle().equals(action.getTitle())) {
                user.getUser().getFavoriteMovies().add(s.getSerial().getTitle());
            }
        }

        file.put("message", "success -> " + action.getTitle() + " was added as favourite");
        return file;
    }
}
