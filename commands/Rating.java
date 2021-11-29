package commands;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;
import utils.Utils;

import java.util.ArrayList;

public final class Rating {

    private Rating() {
    }

    /**
     * Da rating unui film/serial vazut. In Movie si Season se retine lista cu utizatorii
     * care au dat deja rating pentru a nu a avea posibilitatea de a da de doua ori.
     * In User se incrementeaza valoarea campului numberOfRatings care va fi folosit
     * la task-urile urmatoare.
     *
     * @param action
     * @param movies
     * @param serials
     * @param users
     * @return
     */
    public static JSONObject giveRating(final ActionInputData action,
                                        final ArrayList<Movie> movies,
                                        final ArrayList<Serial> serials,
                                        final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        User user = Utils.returnUser(users, action);
        Movie movie = Utils.returnMovie(movies, action);
        Serial serial = Utils.returnSerial(serials, action);

        if (user != null) {
            if (user.getUser().getHistory().get(action.getTitle()) == null) {
                file.put("message", "error -> " + action.getTitle() + " is not seen");
                return file;
            }
            if (movie != null) {
                if (movie.getUsersThatRated().contains(user.getUser().getUsername())) {
                    file.put("message", "error -> " + action.getTitle()
                                                    + " has been already rated");
                } else {
                    movie.getRatings().add(action.getGrade());
                    movie.getUsersThatRated().add(user.getUser().getUsername());
                    user.updateNoRatings();
                    file.put("message", "success -> " + action.getTitle() + " was rated with "
                             + action.getGrade() + " by " + user.getUser().getUsername());
                }
            }
            if (serial != null) {
                if (serial.getSerial().getSeasons().get(action.getSeasonNumber() - 1)
                                      .getUsersThatRated().contains(user.getUser()
                                      .getUsername())) {
                    file.put("message", "error -> " + action.getTitle()
                                                    + " has been already rated");
                } else {
                    serial.getSerial().getSeasons().get(action.getSeasonNumber() - 1)
                                      .getRatings().add(action.getGrade());
                    serial.getSerial().getSeasons().get(action.getSeasonNumber() - 1)
                                      .getUsersThatRated().add(action.getUsername());
                    user.updateNoRatings();
                    file.put("message", "success -> " + action.getTitle() + " was rated with "
                            + action.getGrade() + " by " + user.getUser().getUsername());
                }
            }
        }
            return file;
    }
}

