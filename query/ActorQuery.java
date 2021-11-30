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

public final class ActorQuery {

    public static final int WORDS = 2;
    public static final int AWARDS = 3;

    private ActorQuery() {
    }

    /**
     * Calculeaza ratingul unui actor facand media dntre filmele si serialele in care
     * a jucat actorul si care au rating( videoclipurile fara rating sunt setate cu 0).
     *
     * @param filmography
     * @param movies
     * @param serials
     * @return
     */
    public static Double getActorRating(final ArrayList<String> filmography,
                                        final ArrayList<Movie> movies,
                                        final ArrayList<Serial> serials) {
        Double grade = 0.0;
        int size = 0;
        for (Movie movie : movies) {
            for (String i : filmography) {
                if (i.equals(movie.getMovie().getTitle()) && movie.getRatingGrade() > 0.0) {
                    grade += movie.getRatingGrade();
                    size++;
                }
            }
        }
        for (Serial serial : serials) {
            for (String i : filmography) {
                if (i.equals(serial.getSerial().getTitle()) && serial.getRatingGrade() > 0.0) {
                    grade += serial.getRatingGrade();
                    size++;
                }
            }
        }
        if (size == 0) {
            return 0.0;
        }
        return grade / size;
    }
    /**
     * Sorteaza crescator/descrescator primii n actori care au rating diferit de 0.
     *
     * @param action
     * @param movies
     * @param serials
     * @param actors
     * @return
     */
    public static JSONObject average(final ActionInputData action,
                                     final ArrayList<Movie> movies,
                                     final ArrayList<Serial> serials,
                                     final ArrayList<Actor> actors) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        ArrayList<Actor> bestActors = new ArrayList<>();

        for (Actor actor : actors) {
            Double ratingActor = getActorRating(actor.getActor().getFilmography(), movies, serials);
            actor.setRating(ratingActor);
            if (ratingActor != 0.0) {
                bestActors.add(actor);
            }
        }
        if (action.getSortType().equals("asc")) {
            Collections.sort(bestActors, Actor.getAscRating().thenComparing(Actor.getAscName()));
        } else {
            Collections.sort(bestActors, Actor.getDescRating().thenComparing(Actor.getDescName()));
        }

        for (int i = bestActors.size() - 1; i >= action.getNumber(); i--) {
            bestActors.remove(i);
        }
        file.put("message", "Query result: " + bestActors);
        return file;
    }
    /**
     * Sorteaza crescator/descrescator dupa numarul total de premii doar actorii care contin
     * toate premiile mentionate.
     *
     * @param action
     * @param actors
     * @return
     */
    public static JSONObject awards(final ActionInputData action,
                                    final ArrayList<Actor> actors) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        List<Actor> bestActors = new ArrayList<>();
        List<String> awards = action.getFilters().get(AWARDS);

        for (Actor actor : actors) {
            int ok = 1;
            for (String award : awards) {
                if (!actor.getActor().getAwards().containsKey(Utils
                                     .stringToAwards(award.toUpperCase()))) {
                    ok = 0;
                }
            }
            if (ok == 1) {
                bestActors.add(actor);
                int noAwards = 0;
                for (Map.Entry<ActorsAwards, Integer> entry: actor.getActor()
                                                            .getAwards().entrySet()) {
                    noAwards += entry.getValue();
                }
                actor.setNoAwards(noAwards);
            }
        }
        if (action.getSortType().equals("asc")) {
            Collections.sort(bestActors, Actor.getAscNoAwards()
                                        .thenComparing(Actor.getAscName()));
        } else {
            Collections.sort(bestActors, Actor.getDescNoAwards()
                                        .thenComparing(Actor.getDescName()));
        }
        if (action.getNumber() != 0) {
            for (int i = bestActors.size() - 1; i >= action.getNumber(); i--) {
                bestActors.remove(i);
            }
        }
        file.put("message", "Query result: " + bestActors);
        return file;
    }
    /**
     * Sorteaza crescator/descrescator doar actorii care contin in descriere cuvintele
     * mentionate.
     *
     * @param action
     * @param actors
     * @return
     */
    public static JSONObject filterDescription(final ActionInputData action,
                                               final ArrayList<Actor> actors) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        List<Actor> bestActors = new ArrayList<>();
        List<String> words = action.getFilters().get(WORDS);

        for (Actor actor : actors) {
            int ok = 1;
            for (int i = 0; i < words.size(); i++) {
                Pattern pattern = Pattern.compile("[ ,.-]" + words.get(i) + "[ ,.-]",
                                                                   Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(actor.getActor().getCareerDescription());
                if (!matcher.find()) {
                    ok = 0;
                }
            }
            if (ok == 1) {
                bestActors.add(actor);
            }
        }

        if (action.getSortType().equals("asc")) {
            Collections.sort(bestActors, Actor.getAscName());
        } else {
            Collections.sort(bestActors, Actor.getDescName());
        }
        if (action.getNumber() != 0) {
            for (int i = bestActors.size() - 1; i >= action.getNumber(); i--) {
                bestActors.remove(i);
            }
        }
        file.put("message", "Query result: " + bestActors);
        return file;
    }
}
