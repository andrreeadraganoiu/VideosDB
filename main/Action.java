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

public class Action {

    /**
     * @param input
     * @param arrayResult
     * @param MovieArray
     * @param SerialArray
     * @param UserArray
     * @return
     * @throws IOException
     */
    public static JSONArray doAction(final Input input,
                                      final JSONArray arrayResult,
                                      final ArrayList<Movie> MovieArray,
                                      final ArrayList<Serial> SerialArray,
                                      final ArrayList<User> UserArray,
                                      final ArrayList<Actor> ActorArray
                                      ) throws IOException {

        for(ActionInputData action : input.getCommands()) {
            if (action.getActionType().equals("command")) {
                if (action.getType().equals("favorite")) {
                    arrayResult.add(Favorite.addToFavorite(action, MovieArray, SerialArray, UserArray));
                } else if (action.getType().equals("view")) {
                    arrayResult.add(View.addToViewed(action, UserArray));
                } else if (action.getType().equals("rating")) {
                    arrayResult.add(Rating.giveRating(action, MovieArray, SerialArray, UserArray));
                }
            } else if (action.getActionType().equals("query")) {
                if (action.getCriteria().equals("average")) {
                    arrayResult.add(ActorQuery.average(action, MovieArray, SerialArray, ActorArray));
                } else if (action.getCriteria().equals("awards")) {
                    arrayResult.add(ActorQuery.awards(action, ActorArray));
                } else if (action.getCriteria().equals("filter_description")) {
                    arrayResult.add(ActorQuery.filterDescription(action, ActorArray));
                } else if (action.getCriteria().equals("ratings")) {
                    arrayResult.add(VideoQuery.rating(action, MovieArray, SerialArray));
                } else if (action.getCriteria().equals("favorite")) {
                    arrayResult.add(VideoQuery.favorite(action, MovieArray, SerialArray, UserArray));
                } else if (action.getCriteria().equals("longest")) {
                    arrayResult.add(VideoQuery.longest(action, MovieArray, SerialArray));
                } else if (action.getCriteria().equals("most_viewed")) {
                    arrayResult.add(VideoQuery.mostViewed(action, MovieArray, SerialArray, UserArray));
                } else if (action.getCriteria().equals("num_ratings")) {
                    arrayResult.add(UserQuery.noRatings(action, UserArray));
                }
            } else if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard")) {
                    arrayResult.add(Basic.standard(action, MovieArray, SerialArray, UserArray));
                }
                else if (action.getType().equals("best_unseen")) {
                    arrayResult.add(Basic.bestUnseen(action, MovieArray, SerialArray, UserArray));
                }
                else if (action.getType().equals("popular")) {
                    arrayResult.add(Premium.popular(action, MovieArray, SerialArray, UserArray));
                }
                else if (action.getType().equals("favorite")) {
                    arrayResult.add(Premium.favorite(action, MovieArray, SerialArray, UserArray));
                }
                else if (action.getType().equals("search")) {
                    arrayResult.add(Premium.search(action, MovieArray, SerialArray, UserArray));
                }
            }
        }
        return arrayResult;
    }
}
