package queries;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.ActionInputData;
import org.json.simple.JSONObject;
import utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class VideoQuery {

    public static final int YEAR = 0;
    public static final int GENRE = 1;

    private VideoQuery() {
    }
    /**
     * Creeaza o noua lista cu filmele care sunt din anul dat ca input
     * @param movies
     * @param years
     * @return Lista cu filmele care sunt din anul dat in lista de input
     */
    public static ArrayList<Movie> filterMovieYear(final ArrayList<Movie> movies,
                                                   final List<String> years) {
        ArrayList<Movie> movieList = new ArrayList<>();
        for (Movie movie : movies) {
            int ok = 0;
            for (int i = 0; i < years.size(); i++) {
                if (movie.getMovie().getYear() == Integer.valueOf(years.get(i))) {
                    ok = 1;
                }
            }
            if (ok == 1) {
                movieList.add(movie);
            }
        }
        return movieList;
    }
    /**
     * Creeaza o noua lista cu serialele care sunt din anul dat ca input
     * @param serials
     * @param years
     * @return Lista cu serialele care sunt din anul dat in lista de input
     */
    public static ArrayList<Serial> filterSerialYear(final ArrayList<Serial> serials,
                                                     final List<String> years) {
        ArrayList<Serial> serialList = new ArrayList<>();
        for (Serial serial : serials) {
            int ok = 0;
            for (int i = 0; i < years.size(); i++) {
                if (serial.getSerial().getYear() == Integer.valueOf(years.get(i))) {
                    ok = 1;
                }
            }
            if (ok == 1) {
                serialList.add(serial);
            }
        }
        return serialList;
    }
    /**
     * Creeaza o noua lista cu filmele care contin genul dat ca input
     * @param movies
     * @param genre
     * @return Lista cu filmele care au genul dat in lista de input
     */
    public static ArrayList<Movie> filterMovieGenre(final ArrayList<Movie> movies,
                                                    final List<String> genre) {

        ArrayList<Movie> movieList = new ArrayList<>();
        for (Movie movie : movies) {
            int cnt = 0;
            for (int i = 0; i < genre.size(); i++) {
                for (int j = 0; j < movie.getMovie().getGenres().size(); j++) {
                    if (genre.get(i).equals(movie.getMovie().getGenres().get(j))) {
                        cnt++;
                    }
                }
            }
            if (cnt == genre.size()) {
                movieList.add(movie);
            }
        }
        return movieList;
    }
    /**
     * Creeaza o noua lista cu serialele care contin genurile date ca input
     * @param serials
     * @param genre
     * @return Lista cu serialele care au genurile date in lista de input
     */
    public static ArrayList<Serial> filterSerialGenre(final ArrayList<Serial> serials,
                                                      final List<String> genre) {

        ArrayList<Serial> serialList = new ArrayList<>();
        for (Serial serial : serials) {
            int cnt = 0;
            for (int i = 0; i < genre.size(); i++) {
                for (int j = 0; j < serial.getSerial().getGenres().size(); j++) {
                    if (genre.get(i).equals(serial.getSerial().getGenres().get(j))) {
                        cnt++;
                    }
                }
            }
            if (cnt == genre.size()) {
                serialList.add(serial);
            }
        }
        return serialList;
    }
    /**
     * Verifica daca lista de ani sau de genuri este null pentru a nu genera eroare
     * la apelarea functiile de mai sus. Se creeaza o noua lista cu filmele care
     * indeplinesc criteriile cerute.
     * @param action
     * @param movies
     * @return Lista de filme filtrata dupa ani si genuri
     */
    public static ArrayList<Movie> checkMovieFilters(final ActionInputData action,
                                                     final ArrayList<Movie> movies) {

        ArrayList<Movie> filteredMovies = new ArrayList<>();

        if (action.getFilters().get(YEAR).get(0) != null) {
            filteredMovies = filterMovieYear(movies, action.getFilters().get(YEAR));
            if (action.getFilters().get(GENRE).get(0) != null) {
                filteredMovies = filterMovieGenre(filteredMovies, action.getFilters().get(GENRE));
            }
        } else if (action.getFilters().get(GENRE).get(0) != null) {
            filteredMovies = filterMovieGenre(movies, action.getFilters().get(GENRE));
            if (action.getFilters().get(YEAR).get(0) != null) {
                filteredMovies = filterMovieYear(filteredMovies, action.getFilters().get(YEAR));
            }
        } else {
            for (Movie movie : movies) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
    /**
     * Verifica daca lista de ani sau de genuri este null pentru a nu genera eroare
     * la apelarea functiile de mai sus. Se creeaza o noua lista cu serialele care
     * indeplinesc criteriile cerute.
     * @param action
     * @param serials
     * @return Lista de seriale filtrata dupa ani si genuri
     */
    public static ArrayList<Serial> checkSerialFilters(final ActionInputData action,
                                                       final ArrayList<Serial> serials) {
        ArrayList<Serial> filteredSerials = new ArrayList<>();

        if (action.getFilters().get(YEAR).get(0) != null) {
            filteredSerials = filterSerialYear(serials, action.getFilters().get(YEAR));
            if (action.getFilters().get(GENRE).get(0) != null) {
                filteredSerials = filterSerialGenre(filteredSerials,
                                                    action.getFilters().get(GENRE));
            }
        } else if (action.getFilters().get(GENRE).get(0) != null) {
            filteredSerials = filterSerialGenre(serials, action.getFilters().get(GENRE));
            if (action.getFilters().get(YEAR).get(0) != null) {
                filteredSerials = filterSerialYear(filteredSerials,
                                                   action.getFilters().get(YEAR));
            }
        } else {
            for (Serial serial : serials) {
                filteredSerials.add(serial);
            }
        }
        return filteredSerials;
    }
    /**
     * Sorteaza primele n videoclipuri in functie de ratingul obtinut
     * de la utilizatori
     * @param action
     * @param movies
     * @param serials
     * @return
     */
    public static JSONObject rating(final ActionInputData action,
                                    final ArrayList<Movie> movies,
                                    final ArrayList<Serial> serials) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        if (action.getObjectType().equals("movies")) {

           ArrayList<Movie> filteredMovies = checkMovieFilters(action, movies);

            if (action.getSortType().equals("asc")) {
                Collections.sort(filteredMovies, Movie.getAscRating()
                                                .thenComparing(Movie.getAscName()));
            } else {
                Collections.sort(filteredMovies, Movie.getDescRating()
                                                .thenComparing(Movie.getDescName()));
            }

            for (int i = 0; i < filteredMovies.size(); i++) {
                if (filteredMovies.get(i).getRatingGrade() == 0.0) {
                    filteredMovies.remove(i);
                    i--;
                }
            }

            if (action.getNumber() != 0) {
                for (int i = filteredMovies.size() - 1; i >= action.getNumber(); i--) {
                    filteredMovies.remove(i);
                }
            }

            file.put("message", "Query result: " + filteredMovies);
        } else if (action.getObjectType().equals("shows")) {

            ArrayList<Serial> filteredSerials = checkSerialFilters(action, serials);

            if (action.getSortType().equals("asc")) {
                Collections.sort(filteredSerials, Serial.getAscRating()
                                                 .thenComparing(Serial.getAscName()));
            } else {
                Collections.sort(filteredSerials, Serial.getDescRating()
                                                 .thenComparing(Serial.getDescName()));
            }

            for (int i = 0; i < filteredSerials.size(); i++) {
                if (filteredSerials.get(i).getRatingGrade() == 0.0) {
                    filteredSerials.remove(i);
                    i--;
                }
            }

            if (action.getNumber() != 0) {
                for (int i = filteredSerials.size() - 1; i >= action.getNumber(); i--) {
                    filteredSerials.remove(i);
                }
            }

            file.put("message", "Query result: " + filteredSerials);
        }
        return file;
    }
    /**
     * Calculeaza pentru fiecare video numarul utilizatorilor care l-au adaugat
     * la favorite. Se sorteaza dupa acest criteriu.
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

        if (action.getObjectType().equals("movies")) {

            ArrayList<Movie> filteredMovies = checkMovieFilters(action, movies);

            for (Movie movie : filteredMovies) {
                for (User user : users) {
                    for (String fav : user.getUser().getFavoriteMovies()) {
                        if (fav.equals(movie.getMovie().getTitle())) {
                            movie.updateNoFavoriteAdd();
                        }
                    }
                }
            }

            if (action.getSortType().equals("asc")) {
                Collections.sort(filteredMovies, Movie.getAscFav()
                                                .thenComparing(Movie.getAscName()));
            } else {
                Collections.sort(filteredMovies, Movie.getDescFav()
                                                .thenComparing(Movie.getDescName()));
            }

            for (int i = 0; i < filteredMovies.size(); i++) {
                if (filteredMovies.get(i).getNoFavoriteAdd() == 0) {
                    filteredMovies.remove(i);
                    i--;
                }
            }
            if (action.getNumber() != 0) {
                for (int i = filteredMovies.size() - 1; i >= action.getNumber(); i--) {
                    filteredMovies.remove(i);
                }
            }
            file.put("message", "Query result: " + filteredMovies);
        } else if (action.getObjectType().equals("shows")) {

            ArrayList<Serial> filteredSerials = checkSerialFilters(action, serials);

            for (Serial serial : filteredSerials) {
                for (User user : users) {
                    for (String fav : user.getUser().getFavoriteMovies()) {
                        if (fav.equals(serial.getSerial().getTitle())) {
                            serial.updateNoFavoriteAdd();
                        }
                    }
                }
            }

            if (action.getSortType().equals("asc")) {
                Collections.sort(filteredSerials, Serial.getAscFav()
                                                 .thenComparing(Serial.getAscName()));
            } else {
                Collections.sort(filteredSerials, Serial.getDescFav()
                                                 .thenComparing(Serial.getDescName()));
            }

            for (int i = 0; i < filteredSerials.size(); i++) {
                if (filteredSerials.get(i).getNoFavoriteAdd() == 0) {
                    filteredSerials.remove(i);
                    i--;
                }
            }

            if (action.getNumber() != 0) {
                for (int i = filteredSerials.size() - 1; i >= action.getNumber(); i--) {
                    filteredSerials.remove(i);
                }
            }
            file.put("message", "Query result: " + filteredSerials);
        }
        return file;
    }
    /**
     * Sorteaza in functie de durata, pentru seriale suma duratelor
     * sezoanelor.
     * @param action
     * @param movies
     * @param serials
     * @return
     */
    public static JSONObject longest(final ActionInputData action,
                                     final ArrayList<Movie> movies,
                                     final ArrayList<Serial> serials) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        if (action.getObjectType().equals("movies")) {

            ArrayList<Movie> filteredMovies = checkMovieFilters(action, movies);

            if (action.getSortType().equals("asc")) {
                Collections.sort(filteredMovies, Movie.getAscDuration()
                                                .thenComparing(Movie.getAscName()));
            } else {
                Collections.sort(filteredMovies, Movie.getDescDuration()
                                                .thenComparing(Movie.getDescName()));
            }

            if (action.getNumber() != 0) {
                for (int i = filteredMovies.size() - 1; i >= action.getNumber(); i--) {
                    filteredMovies.remove(i);
                }
            }

            file.put("message", "Query result: " + filteredMovies);

        } else if (action.getObjectType().equals("shows")) {

            ArrayList<Serial> filteredSerials = checkSerialFilters(action, serials);

            for (Serial serial : serials) {
                for (int i = 0; i < serial.getSerial().getSeasons().size(); i++) {
                    serial.setDuration(serial.getSerial().getSeasons().get(i).getDuration());
                }
            }

            if (action.getSortType().equals("asc")) {
                Collections.sort(filteredSerials, Serial.getAscDuration()
                                                 .thenComparing(Serial.getAscName()));
            } else {
                Collections.sort(filteredSerials, Serial.getDescDuration()
                                                 .thenComparing(Serial.getDescName()));
            }

            if (action.getNumber() != 0) {
                for (int i = filteredSerials.size() - 1; i >= action.getNumber(); i--) {
                    filteredSerials.remove(i);
                }
            }
            file.put("message", "Query result: " + filteredSerials);
        }
        return file;
    }
    /**
     * Calculeaza de cate ori a fost vizionat un film/serial.
     * @param action
     * @param movies
     * @param serials
     * @param users
     * @return
     */
    public static JSONObject mostViewed(final ActionInputData action,
                                        final ArrayList<Movie> movies,
                                        final ArrayList<Serial> serials,
                                        final ArrayList<User> users) {

        JSONObject file = new JSONObject();
        file.put("id", action.getActionId());

        if (action.getObjectType().equals("movies")) {

            ArrayList<Movie> filteredMovies = checkMovieFilters(action, movies);

            for (User user : users) {
                for (Map.Entry<String, Integer> entry: user.getUser().getHistory().entrySet()) {
                    Movie movie = Utils.findMovieByString(movies, entry.getKey());
                    if (movie != null) {
                        movie.updateNoViews(entry.getValue());
                    }
                }
            }

            if (action.getSortType().equals("asc")) {
                Collections.sort(filteredMovies, Movie.getAscViews()
                                                .thenComparing(Movie.getAscName()));
            } else {
                Collections.sort(filteredMovies, Movie.getDescViews()
                                                .thenComparing(Movie.getDescName()));
            }

            for (int i = 0; i < filteredMovies.size(); i++) {
                if (filteredMovies.get(i).getNoViews() == 0) {
                    filteredMovies.remove(i);
                    i--;
                }
            }

            if (action.getNumber() != 0) {
                for (int i = filteredMovies.size() - 1; i >= action.getNumber(); i--) {
                    filteredMovies.remove(i);
                }
            }

            file.put("message", "Query result: " + filteredMovies);
        } else if (action.getObjectType().equals("shows")) {

            ArrayList<Serial> filteredSerials = checkSerialFilters(action, serials);

            for (User user : users) {
                for (Map.Entry<String, Integer> entry: user.getUser().getHistory().entrySet()) {
                    Serial serial = Utils.findSerialByString(serials, entry.getKey());
                    if (serial != null) {
                        serial.updateNoViews(entry.getValue());
                    }
                }
            }
            if (action.getSortType().equals("asc")) {
                Collections.sort(filteredSerials, Serial.getAscViews()
                                                 .thenComparing(Serial.getAscName()));
            } else {
                Collections.sort(filteredSerials, Serial.getDescViews()
                                                 .thenComparing(Serial.getDescName()));
            }

            for (int i = 0; i < filteredSerials.size(); i++) {
                if (filteredSerials.get(i).getNoViews() == 0) {
                    filteredSerials.remove(i);
                    i--;
                }
            }

            if (action.getNumber() != 0) {
                for (int i = filteredSerials.size() - 1; i >= action.getNumber(); i--) {
                    filteredSerials.remove(i);
                }
            }
            file.put("message", "Query result: " + filteredSerials);
        }
        return file;
    }
}
