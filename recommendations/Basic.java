package recommendations;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;
import utils.Utils;

import java.util.ArrayList;

public class Basic {

    public static JSONObject standard(final ActionInputData action,
                                      final ArrayList<Movie> movies,
                                      final ArrayList<Serial> serials,
                                      final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        User user = Utils.returnUser(users, action);

        for(Movie movie : movies) {
            if(!user.getUser().getHistory().containsKey(movie.getMovie().getTitle())) {
                file.put("message", "StandardRecommendation result: " + movie.getMovie().getTitle());
                return file;
            }
        }

        for(Serial serial : serials) {
            if(!user.getUser().getHistory().containsKey(serial.getSerial().getTitle())) {
                file.put("message", "StandardRecommendation result: " + serial.getSerial().getTitle());
                return file;
            }
        }

        file.put("message", "StandardRecommendation cannot be applied!");

        return file;
    }

    public static JSONObject bestUnseen(final ActionInputData action,
                                        final ArrayList<Movie> movies,
                                        final ArrayList<Serial> serials,
                                        final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        User user = Utils.returnUser(users, action);

        Double maxRating = -1.0;
        String maxVideo = null;
        for(Movie movie : movies) {
             if(movie.getRatingGrade() > maxRating && !user.getUser().getHistory().containsKey(movie.getMovie().getTitle())) {
                 maxRating = movie.getRatingGrade();
                 maxVideo = movie.getMovie().getTitle();
             }
        }

        for(Serial serial : serials) {
            if(serial.getRatingGrade() > maxRating && !user.getUser().getHistory().containsKey((serial.getSerial().getTitle()))) {
                maxRating = serial.getRatingGrade();
                maxVideo = serial.getSerial().getTitle();
            }
        }

        if(maxVideo == null) {
            file.put("message", "BestRatedUnseenRecommendation cannot be applied!");
            return file;
        }

        file.put("message", "BestRatedUnseenRecommendation result: " + maxVideo);

        return file;

    }

}
