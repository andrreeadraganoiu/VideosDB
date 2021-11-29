package query;
import actor.ActorsAwards;
import entertainment.Actor;
import entertainment.Movie;
import entertainment.Serial;
import fileio.ActionInputData;
import org.json.simple.JSONObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorQuery {

    public final static int WORDS = 2;
    public final static int AWARDS = 3;

    public static Double getActorRating(final ArrayList<String> filmography,
                                        final ArrayList<Movie> movies,
                                        final ArrayList<Serial> serials) {
        Double grade = 0.0;
        int size = 0;
        for(Movie movie : movies) {
            for(String i : filmography) {
                if(i.equals(movie.getMovie().getTitle()) && movie.getRatingGrade() > 0.0) {
                    grade += movie.getRatingGrade();
                    size++;
                }
            }
        }
        for(Serial serial : serials) {
            for(String i : filmography) {
                if(i.equals(serial.getSerial().getTitle()) && serial.getRatingGrade() > 0.0) {
                    grade += serial.getRatingGrade();
                    size++;
                }
            }
        }
        if(size == 0)
            return 0.0;
        return grade / size;
    }

    public static JSONObject average(final ActionInputData action,
                                     final ArrayList<Movie> movies,
                                     final ArrayList<Serial> serials,
                                     final ArrayList<Actor> actors) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        ArrayList<Actor> bestActors = new ArrayList<>();

        for(Actor actor : actors) {
            Double ratingActor = getActorRating(actor.getActor().getFilmography(), movies, serials);
            actor.setRating(ratingActor);
            if(ratingActor != 0.0) {
                bestActors.add(actor);
            }
        }
        if (action.getSortType().equals("asc")) {
            Collections.sort(bestActors, Actor.ascActorRating.thenComparing(Actor.ascActorName));
        } else {
            Collections.sort(bestActors, Actor.descActorRating.thenComparing(Actor.descActorName));
        }

        for(int i = bestActors.size() - 1 ; i >= action.getNumber(); i --) {
            bestActors.remove(i);
        }

        file.put("message", "Query result: " + bestActors);

        return file;
    }

    public static JSONObject awards(final ActionInputData action,
                                    final ArrayList<Actor> actors) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        List<Actor> bestActors = new ArrayList<>();
        List<String> awards = action.getFilters().get(AWARDS);

        for(Actor actor : actors) {
            int ok = 1;
            for(String award : awards) {
                if(!actor.getActor().getAwards().containsKey(Utils.stringToAwards(award.toUpperCase()))) {
                    ok = 0;
                }
            }
            if(ok == 1) {
                bestActors.add(actor);
                for (Map.Entry<ActorsAwards, Integer> entry: actor.getActor().getAwards().entrySet()) {
                    actor.setNoAwards(entry.getValue());
                }
            }
        }

        if (action.getSortType().equals("asc")) {
            Collections.sort(bestActors, Actor.ascNoAwards.thenComparing(Actor.ascActorName));
        } else {
            Collections.sort(bestActors, Actor.descNoAwards.thenComparing(Actor.descActorName));
        }

        if(action.getNumber() != 0) {
            for(int i = bestActors.size() - 1 ; i >= action.getNumber(); i --) {
                bestActors.remove(i);
            }
        }

        file.put("message", "Query result: " + bestActors);

        return file;
    }

    public static JSONObject filterDescription(final ActionInputData action,
                                               final ArrayList<Actor> actors) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        List<Actor> bestActors = new ArrayList<>();
        List<String> words = action.getFilters().get(WORDS);

        //Pattern pattern = Pattern.compile("[ ]w3schools", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher("Visit W3Schools!");
//        boolean matchFound = matcher.find();
//        if(matchFound) {
//            System.out.println("Match found");
//        } else {
//            System.out.println("Match not found");
//        }

//        for(Actor actor : actors) {
//            int ok = 1;
//            for(String word : words) {
//                for(int i = 0; i < words.size(); i++) {
//                    if(actor.getActor().getCareerDescription().toLowerCase().indexOf(" "+words.get(i).toLowerCase()+ " ") == -1) {
//                        ok = 0;
//                    }
//                }
//            }
//            if(ok == 1) {
//                bestActors.add(actor);
//            }
//        }

        for(Actor actor : actors) {

            int ok = 1;
            for(String word : words) {
                for(int i = 0; i < words.size(); i++) {
                    Pattern pattern = Pattern.compile("[ -]"+words.get(i) + "[ -]", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(actor.getActor().getCareerDescription());
                    //boolean matchFound = matcher.find();
//                    if(actor.getActor().getCareerDescription().toLowerCase().indexOf(" "+words.get(i).toLowerCase()+ " ") == -1) {
//                        ok = 0;
//                    }
                    if(!matcher.find()) {
                        ok = 0;
                    }
                }
            }
            if(ok == 1) {
                bestActors.add(actor);
            }
        }

        if (action.getSortType().equals("asc")) {
            Collections.sort(bestActors, Actor.ascActorName);
        } else {
            Collections.sort(bestActors, Actor.descActorName);
        }

        if(action.getNumber() != 0) {
            for(int i = bestActors.size() - 1 ; i >= action.getNumber(); i --) {
                bestActors.remove(i);
            }
        }

        file.put("message", "Query result: " + bestActors);

        return file;
    }
}
