package main;

import commands.Favorite;
import commands.Rating;
import commands.View;
import entertainment.Actor;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.ActionInputData;
import fileio.Input;
import org.json.simple.JSONArray;
import query.ActorQuery;
import query.UserQuery;
import query.VideoQuery;
import recommendations.Premium;
import recommendations.Basic;

import java.io.IOException;
import java.util.ArrayList;

public final class Action {

    private Action() {
    }
    /**
     * Se executa actiunile corespunzatoare.
     * @param input
     * @param arrayResult
     * @param movieArray
     * @param serialArray
     * @param userArray
     * @param actorArray
     * @return
     * @throws IOException
     */
    public static JSONArray doAction(final Input input,
                                      final JSONArray arrayResult,
                                      final ArrayList<Movie> movieArray,
                                      final ArrayList<Serial> serialArray,
                                      final ArrayList<User> userArray,
                                      final ArrayList<Actor> actorArray
                                      ) throws IOException {

        for (ActionInputData action : input.getCommands()) {
            if (action.getActionType().equals("command")) {
                if (action.getType().equals("favorite")) {
                    arrayResult.add(Favorite.addToFavorite(action, movieArray,
                                                           serialArray, userArray));
                } else if (action.getType().equals("view")) {
                    arrayResult.add(View.addToViewed(action, userArray));
                } else if (action.getType().equals("rating")) {
                    arrayResult.add(Rating.giveRating(action, movieArray,
                                                      serialArray, userArray));
                }
            } else if (action.getActionType().equals("query")) {
                if (action.getCriteria().equals("average")) {
                    arrayResult.add(ActorQuery.average(action, movieArray,
                                                       serialArray, actorArray));
                } else if (action.getCriteria().equals("awards")) {
                    arrayResult.add(ActorQuery.awards(action, actorArray));
                } else if (action.getCriteria().equals("filter_description")) {
                    arrayResult.add(ActorQuery.filterDescription(action, actorArray));
                } else if (action.getCriteria().equals("ratings")) {
                    arrayResult.add(VideoQuery.rating(action, movieArray, serialArray));
                } else if (action.getCriteria().equals("favorite")) {
                    arrayResult.add(VideoQuery.favorite(action, movieArray,
                                                        serialArray, userArray));
                } else if (action.getCriteria().equals("longest")) {
                    arrayResult.add(VideoQuery.longest(action, movieArray, serialArray));
                } else if (action.getCriteria().equals("most_viewed")) {
                    arrayResult.add(VideoQuery.mostViewed(action, movieArray,
                                                          serialArray, userArray));
                } else if (action.getCriteria().equals("num_ratings")) {
                    arrayResult.add(UserQuery.noRatings(action, userArray));
                }
            } else if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard")) {
                    arrayResult.add(Basic.standard(action, movieArray,
                                                   serialArray, userArray));
                } else if (action.getType().equals("best_unseen")) {
                    arrayResult.add(Basic.bestUnseen(action, movieArray,
                                                     serialArray, userArray));
                } else if (action.getType().equals("popular")) {
                    arrayResult.add(Premium.popular(action, movieArray,
                                                    serialArray, userArray));
                } else if (action.getType().equals("favorite")) {
                    arrayResult.add(Premium.favorite(action, movieArray,
                                                     serialArray, userArray));
                } else if (action.getType().equals("search")) {
                    arrayResult.add(Premium.search(action, movieArray,
                                                   serialArray, userArray));
                }
            }
        }
        return arrayResult;
    }
}
