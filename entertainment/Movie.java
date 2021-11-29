package entertainment;

import fileio.MovieInputData;
import java.util.ArrayList;
import java.util.Comparator;

public class Movie {
    private final MovieInputData movie;
    private final ArrayList<Double> ratings;
    private final ArrayList<String> usersThatRated;
    private int noFavoriteAdd = 0;
    private int noViews = 0;

    public Movie(final MovieInputData movie) {
        this.movie = movie;
        this.ratings = new ArrayList<>();
        this.usersThatRated = new ArrayList<>();
    }
    /**
     * @return
     */
    public MovieInputData getMovie() {
        return movie;
    }
    /**
     * @return
     */
    public ArrayList<Double> getRatings() {
        return ratings;
    }
    /**
     * @return
     */
    public ArrayList<String> getUsersThatRated() {
        return usersThatRated;
    }
    /**
     * @return
     */
    public int getNoFavoriteAdd() {
        return noFavoriteAdd;
    }
    /**
     * Incrementeaza numarul de adaugari la favorite
     */
    public void updateNoFavoriteAdd() {
        this.noFavoriteAdd += 1;
    }
    /**
     * Calculeaza numarul de vizualizari
     * @return
     */
    public int getNoViews() {
        return noViews;
    }
    /**
     * @param noViews
     */
    public void updateNoViews(final int noViews) {
        this.noViews += noViews;
    }
    /**
     * @return
     */
    public Double getRatingGrade() {
        Double sum = 0.0;

        if (ratings.size() == 0) {
            return 0.0;
        }
        for (Double grade : ratings) {
            sum += grade;
        }
        sum = sum / ratings.size();
        return sum;
    }

    public static Comparator<Movie> getAscName() {
        return ascName;
    }

    public static Comparator<Movie> getDescName() {
        return descName;
    }

    public static Comparator<Movie> getAscRating() {
        return ascRating;
    }

    public static Comparator<Movie> getDescRating() {
        return descRating;
    }

    public static Comparator<Movie> getAscFav() {
        return ascFav;
    }

    public static Comparator<Movie> getDescFav() {
        return descFav;
    }

    public static Comparator<Movie> getAscDuration() {
        return ascDuration;
    }

    public static Comparator<Movie> getDescDuration() {
        return descDuration;
    }

    public static Comparator<Movie> getAscViews() {
        return ascViews;
    }

    public static Comparator<Movie> getDescViews() {
        return descViews;
    }

    private static Comparator<Movie> ascName = (o1, o2) -> {
        String movieName1 = o1.getMovie().getTitle();
        String movieName2 = o2.getMovie().getTitle();
        return movieName1.compareTo(movieName2);
    };

    private static Comparator<Movie> descName = (o1, o2) -> {
        String movieName1 = o1.getMovie().getTitle();
        String movieName2 = o2.getMovie().getTitle();
        return movieName2.compareTo(movieName1);
    };

    private static Comparator<Movie> ascRating = (o1, o2) -> {
        Double movieRating1 = o1.getRatingGrade();
        Double movieRating2 = o2.getRatingGrade();
        return Double.compare(movieRating1, movieRating2);
    };

    private static Comparator<Movie> descRating = (o1, o2) -> {
        Double movieRating1 = o1.getRatingGrade();
        Double movieRating2 = o2.getRatingGrade();
        return Double.compare(movieRating2, movieRating1);
    };

    private static Comparator<Movie> ascFav = (o1, o2) -> {
        int noFavoriteAdd1 = o1.getNoFavoriteAdd();
        int noFavoriteAdd2 = o2.getNoFavoriteAdd();
        return Integer.compare(noFavoriteAdd1, noFavoriteAdd2);
    };

    private static Comparator<Movie> descFav = (o1, o2) -> {
        int noFavoriteAdd1 = o1.getNoFavoriteAdd();
        int noFavoriteAdd2 = o2.getNoFavoriteAdd();
        return Integer.compare(noFavoriteAdd2, noFavoriteAdd1);
    };

    private static Comparator<Movie> ascDuration = (o1, o2) -> {
        int movieDuration1 = o1.getMovie().getDuration();
        int movieDuration2 = o2.getMovie().getDuration();
        return Integer.compare(movieDuration1, movieDuration2);
    };

    private static Comparator<Movie> descDuration = (o1, o2) -> {
        int movieDuration1 = o1.getMovie().getDuration();
        int movieDuration2 = o2.getMovie().getDuration();
        return Integer.compare(movieDuration2, movieDuration1);
    };

    private static Comparator<Movie> ascViews = (o1, o2) -> {
        int movieViews1 = o1.getNoViews();
        int movieViews2 = o2.getNoViews();
        return Integer.compare(movieViews1, movieViews2);
    };

    private static Comparator<Movie> descViews = (o1, o2) -> {
        int movieViews1 = o1.getNoViews();
        int movieViews2 = o2.getNoViews();
        return Integer.compare(movieViews2, movieViews1);
    };
    /**
     * @return
     */
    @Override
    public String toString() {
        return movie.getTitle();
    }
}
