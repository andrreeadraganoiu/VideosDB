package recommendations;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;
import queries.VideoQuery;
import utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public final class Premium {

    private Premium() {

    }
    /**
     * Sorteaza un HashMap descrescator dupa valoare si crescator dupa cheie.
     * @param map
     * @return un HashMap sortat
     */
    public static Map<String, Integer> sortDescByValue(final Map<String, Integer> map) {

        List<Map.Entry<String, Integer>> genreList =
                new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(genreList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1,
                               final Map.Entry<String, Integer> o2) {

                int res = o2.getValue().compareTo(o1.getValue());
                if (res == 0) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return res;
           }
        });

        Map<String, Integer> aux = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> i : genreList) {
            aux.put(i.getKey(), i.getValue());
        }
        return aux;
    }
    /**
     * Sorteaza un HashMap crescator dupa valoare si crescator dupa cheie.
     * @param map
     * @return un HashMap sortat
     */
    public static Map<String, Double> sortAscByValue(final Map<String, Double> map) {

        List<Map.Entry<String, Double>> genreList
                = new LinkedList<Map.Entry<String, Double>>(map.entrySet());

        Collections.sort(genreList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(final Map.Entry<String, Double> o1,
                               final Map.Entry<String, Double> o2) {

                int res = o1.getValue().compareTo(o2.getValue());
                if (res == 0) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return res;
            }
        });

        Map<String, Double> aux = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> i : genreList) {
            aux.put(i.getKey(), i.getValue());
        }
        return aux;
    }
    /**
     * Intoarce primul video nevazut de user din cel mai popular gen.
     * Toate genurile intalnite in filme sau seriale sunt adaugate intr-un
     * HashMap si sortate descrescator dupa valoare.
     * @param action
     * @param movies
     * @param serials
     * @param users
     * @return
     */
    public static JSONObject popular(final ActionInputData action,
                                     final ArrayList<Movie> movies,
                                     final ArrayList<Serial> serials,
                                     final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        Map<String, Integer> genreMap = new HashMap<>();

        User user = Utils.returnUser(users, action);

        if (user.getUser().getSubscriptionType().equals("BASIC")) {
            file.put("message", "PopularRecommendation cannot be applied!");
            return file;
        }

        for (Movie movie : movies) {
            for (int i = 0; i < movie.getMovie().getGenres().size(); i++) {
                if (!genreMap.containsKey(movie.getMovie().getGenres().get(i))) {
                    genreMap.put(movie.getMovie().getGenres().get(i), 1);
                } else {
                    genreMap.put(movie.getMovie().getGenres().get(i),
                                 genreMap.get(movie.getMovie().getGenres().get(i)) + 1);
                }
            }
        }
        for (Serial serial : serials) {
            for (int i = 0; i < serial.getSerial().getGenres().size(); i++) {
                if (!genreMap.containsKey(serial.getSerial().getGenres().get(i))) {
                    genreMap.put(serial.getSerial().getGenres().get(i), 1);
                } else {
                    genreMap.put(serial.getSerial().getGenres().get(i),
                                 genreMap.get(serial.getSerial().getGenres().get(i)) + 1);
                }
            }
        }
        genreMap = sortDescByValue(genreMap);

        for (Map.Entry<String, Integer> entry: genreMap.entrySet()) {
            for (Movie movie : movies) {
                if (movie.getMovie().getGenres().contains(entry.getKey())
                        && !user.getUser().getHistory().containsKey(movie.getMovie().getTitle())) {

                    file.put("message", "PopularRecommendation result: "
                                       + movie.getMovie().getTitle());
                    return file;
                }
            }
            for (Serial serial : serials) {
                if (serial.getSerial().getGenres().contains(entry.getKey())
                        && !user.getUser().getHistory()
                                          .containsKey(serial.getSerial().getTitle())) {

                    file.put("message", "PopularRecommendation result: "
                                       + serial.getSerial().getTitle());
                    return file;
                }
            }
        }
        file.put("message", "PopularRecommendation cannot be applied!");
        return file;
    }
    /**
     * Intoarce primul video nevazut de user care are cele mai multe adaugari
     * la favorite. Se calculeaza acest lucru incrementand campul noFavoriteAdd
     * pentru fiecare film sau serial.
     * @param action
     * @param movies
     * @param serials
     * @param users
     * @return
     */
    public static JSONObject favorite(final ActionInputData action,
                                      final ArrayList<Movie> movies,
                                      final ArrayList<Serial> serials,
                                      final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        User user = Utils.returnUser(users, action);

        if (user.getUser().getSubscriptionType().equals("BASIC")) {
            file.put("message", "FavoriteRecommendation cannot be applied!");
            return file;
        }

        for (Movie movie : movies) {
            for (User i : users) {
                for (String fav : i.getUser().getFavoriteMovies()) {
                    if (fav.equals(movie.getMovie().getTitle())) {
                        movie.updateNoFavoriteAdd();
                    }
                }
            }
        }

        for (Serial serial : serials) {
            for (User i : users) {
                for (String fav : i.getUser().getFavoriteMovies()) {
                    if (fav.equals(serial.getSerial().getTitle())) {
                        serial.updateNoFavoriteAdd();
                    }
                }
            }
        }

        int maxNoFavoriteAdd = -1;
        String maxVideo = null;

        for (Movie movie : movies) {
            if (movie.getNoFavoriteAdd() > maxNoFavoriteAdd
                    && !user.getUser().getHistory().containsKey(movie.getMovie().getTitle())) {
                maxNoFavoriteAdd = movie.getNoFavoriteAdd();
                maxVideo = movie.getMovie().getTitle();
            }
        }

        for (Serial serial : serials) {
            if (serial.getNoFavoriteAdd() > maxNoFavoriteAdd
                    && !user.getUser().getHistory().containsKey((serial.getSerial().getTitle()))) {
                maxNoFavoriteAdd = serial.getNoFavoriteAdd();
                maxVideo = serial.getSerial().getTitle();
            }
        }

        if (maxVideo == null) {
            file.put("message", "FavoriteRecommendation cannot be applied!");
            return file;
        }

        file.put("message", "FavoriteRecommendation result: " + maxVideo);
        return file;
    }
    /**
     * Intoarce toate videclipurile nevazute de user dintr-un anumit gen.
     * Video-urile sunt adaugate intr-un HashMap si sortate cu ajutorul
     * unui comparator crescator dupa valoare si crescator dupa cheie.
     * @param action
     * @param movies
     * @param serials
     * @param users
     * @return
     */
    public static JSONObject search(final ActionInputData action,
                                    final ArrayList<Movie> movies,
                                    final ArrayList<Serial> serials,
                                    final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        User user = Utils.returnUser(users, action);

        if (user.getUser().getSubscriptionType().equals("BASIC")) {
            file.put("message", "SearchRecommendation cannot be applied!");
            return file;
        }

        ArrayList<Movie> filteredMovies;
        ArrayList<Serial> filteredSerials;

        List<String> oneGenre = new ArrayList<>();
        oneGenre.add(action.getGenre());

        filteredMovies  = VideoQuery.filterMovieGenre(movies, oneGenre);
        filteredSerials = VideoQuery.filterSerialGenre(serials, oneGenre);

        Map<String, Double> searchedVideos = new HashMap<>();

        for (Movie movie : filteredMovies) {
            if (!user.getUser().getHistory().containsKey(movie.getMovie().getTitle())) {
                searchedVideos.put(movie.getMovie().getTitle(), movie.getRatingGrade());
            }
        }

        for (Serial serial : filteredSerials) {
            if (!user.getUser().getHistory().containsKey((serial.getSerial().getTitle()))) {
                searchedVideos.put(serial.getSerial().getTitle(), serial.getRatingGrade());
            }
        }

        searchedVideos = sortAscByValue(searchedVideos);

        ArrayList<String> filteredVideos = new ArrayList<>();

        for (Map.Entry<String, Double> entry: searchedVideos.entrySet()) {
            filteredVideos.add(entry.getKey());
        }

        if (filteredVideos.size() == 0) {
            file.put("message", "SearchRecommendation cannot be applied!");
            return file;
        }

        file.put("message", "SearchRecommendation result: " + filteredVideos);
        return file;

    }
}
