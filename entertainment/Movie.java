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

    public Movie(MovieInputData movie) {
        this.movie = movie;
        this.ratings = new ArrayList<>();
        this.usersThatRated = new ArrayList<>();
    }

    public MovieInputData getMovie() {
        return movie;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public ArrayList<String> getUsersThatRated() {
        return usersThatRated;
    }

    public int getNoFavoriteAdd() {
        return noFavoriteAdd;
    }

    public void updateNoFavoriteAdd() {
        this.noFavoriteAdd += 1;
    }

    public int getNoViews() {
        return noViews;
    }

    public void updateNoViews(int noViews) {
        this.noViews += noViews;
    }

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

    public static Comparator<Movie> ascMovieName = (o1, o2) -> {
        String movieName1 = o1.getMovie().getTitle();
        String movieName2 = o2.getMovie().getTitle();

        return movieName1.compareTo(movieName2);
    };

    public static Comparator<Movie> descMovieName = (o1, o2) -> {
        String movieName1 = o1.getMovie().getTitle();
        String movieName2 = o2.getMovie().getTitle();

        return movieName2.compareTo(movieName1);
    };

    public static Comparator<Movie> ascMovieRating = (o1, o2) -> {
        Double movieRating1 = o1.getRatingGrade();
        Double movieRating2 = o2.getRatingGrade();

        return Double.compare(movieRating1, movieRating2);
    };

    public static Comparator<Movie> descMovieRating = (o1, o2) -> {
        Double movieRating1 = o1.getRatingGrade();
        Double movieRating2 = o2.getRatingGrade();

        return Double.compare(movieRating2, movieRating1);
    };

    public static Comparator<Movie> ascMovieFav = (o1, o2) -> {
        int noFavoriteAdd1 = o1.getNoFavoriteAdd();
        int noFavoriteAdd2 = o2.getNoFavoriteAdd();

        return Integer.compare(noFavoriteAdd1, noFavoriteAdd2);
    };

    public static Comparator<Movie> descMovieFav = (o1, o2) -> {
        int noFavoriteAdd1 = o1.getNoFavoriteAdd();
        int noFavoriteAdd2 = o2.getNoFavoriteAdd();

        return Integer.compare(noFavoriteAdd2, noFavoriteAdd1);
    };

    public static Comparator<Movie> ascMovieDuration = (o1, o2) -> {
        int movieDuration1 = o1.getMovie().getDuration();
        int movieDuration2 = o2.getMovie().getDuration();

        return Integer.compare(movieDuration1, movieDuration2);
    };

    public static Comparator<Movie> descMovieDuration = (o1, o2) -> {
        int movieDuration1 = o1.getMovie().getDuration();
        int movieDuration2 = o2.getMovie().getDuration();

        return Integer.compare(movieDuration2, movieDuration1);
    };

    public static Comparator<Movie> ascMovieViews = (o1, o2) -> {
        int movieViews1 = o1.getNoViews();
        int movieViews2 = o2.getNoViews();

        return Integer.compare(movieViews1, movieViews2);
    };

    public static Comparator<Movie> descMovieViews = (o1, o2) -> {
        int movieViews1 = o1.getNoViews();
        int movieViews2 = o2.getNoViews();

        return Integer.compare(movieViews2, movieViews1);
    };

    @Override
    public String toString() {
        return movie.getTitle();
    }
}
