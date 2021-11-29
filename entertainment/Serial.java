package entertainment;

import fileio.SerialInputData;
import java.util.ArrayList;
import java.util.Comparator;

public class Serial {

    private final SerialInputData serial;
    private final ArrayList<Double> ratings;
    private int noFavoriteAdd = 0;
    private int duration = 0;
    private int noViews = 0;

    public Serial(final SerialInputData serial) {
        this.serial = serial;
        this.ratings = new ArrayList<>();
    }
    /**
     * @return
     */
    public SerialInputData getSerial() {
        return serial;
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
     * @return
     */
    public int getDuration() {
        return duration;
    }
    /**
     * @param duration
     */
    public void setDuration(final int duration) {
        this.duration += duration;
    }
    /**
     * @return
     */
    public int getNoViews() {
        return noViews;
    }
    /**
     * Calculeaza numarul de vizualizari
     * @param noViews
     */
    public void updateNoViews(final int noViews) {
        this.noViews += noViews;
    }
    /**
     * Calculeaza ratingul intregului serial
     * @return
     */
    public Double getRatingGrade() {

        Double sum = 0.0, seasonGrade;
        int nr = this.getSerial().getNumberSeason();

        if (nr == 0) {
            return 0.0;
        } else {
            for (int i = 0; i < nr; i++) {
                seasonGrade = 0.0;
                if (this.getSerial().getSeasons().get(i).getRatings().size() != 0) {
                    for (int j = 0; j < this.getSerial().getSeasons().get(i)
                                                        .getRatings().size(); j++) {
                        seasonGrade += this.getSerial().getSeasons().get(i).getRatings().get(j);
                    }
                    seasonGrade /= this.getSerial().getSeasons().get(i).getRatings().size();
                }
                sum  += seasonGrade;
            }
        }
        sum /= nr;
        return sum;
    }
    /**
     * @return
     */
    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public static Comparator<Serial> getAscName() {
        return ascName;
    }

    public static Comparator<Serial> getDescName() {
        return descName;
    }

    public static Comparator<Serial> getAscRating() {
        return ascRating;
    }

    public static Comparator<Serial> getDescRating() {
        return descRating;
    }

    public static Comparator<Serial> getAscFav() {
        return ascFav;
    }

    public static Comparator<Serial> getDescFav() {
        return descFav;
    }

    public static Comparator<Serial> getAscDuration() {
        return ascDuration;
    }

    public static Comparator<Serial> getDescDuration() {
        return descDuration;
    }

    public static Comparator<Serial> getAscViews() {
        return ascViews;
    }

    public static Comparator<Serial> getDescViews() {
        return descViews;
    }

    private static Comparator<Serial> ascName = (o1, o2) -> {
        String serialName1 = o1.getSerial().getTitle();
        String serialName2 = o2.getSerial().getTitle();

        return serialName1.compareTo(serialName2);
    };

    private static Comparator<Serial> descName = (o1, o2) -> {
        String serialName1 = o1.getSerial().getTitle();
        String serialName2 = o2.getSerial().getTitle();
        return serialName2.compareTo(serialName1);
    };

    private static Comparator<Serial> ascRating = (o1, o2) -> {
        Double serialRating1 = o1.getRatingGrade();
        Double serialRating2 = o2.getRatingGrade();
        return Double.compare(serialRating1, serialRating2);
    };

    private static Comparator<Serial> descRating = (o1, o2) -> {
        Double serialRating1 = o1.getRatingGrade();
        Double serialRating2 = o2.getRatingGrade();
        return Double.compare(serialRating2, serialRating1);
    };

    private static Comparator<Serial> ascFav = (o1, o2) -> {
        int noFavoriteAdd1 = o1.getNoFavoriteAdd();
        int noFavoriteAdd2 = o2.getNoFavoriteAdd();
        return Integer.compare(noFavoriteAdd1, noFavoriteAdd2);
    };

    private static Comparator<Serial> descFav = (o1, o2) -> {
        int noFavoriteAdd1 = o1.getNoFavoriteAdd();
        int noFavoriteAdd2 = o2.getNoFavoriteAdd();
        return Integer.compare(noFavoriteAdd2, noFavoriteAdd1);
    };

    private static Comparator<Serial> ascDuration = (o1, o2) -> {
        int serialDuration1 = o1.getDuration();
        int serialDuration2 = o2.getDuration();
        return Integer.compare(serialDuration1, serialDuration2);
    };

    private static Comparator<Serial> descDuration = (o1, o2) -> {
        int serialDuration1 = o1.getDuration();
        int serialDuration2 = o2.getDuration();
        return Integer.compare(serialDuration2, serialDuration1);
    };

    private static Comparator<Serial> ascViews = (o1, o2) -> {
        int serialViews1 = o1.getNoViews();
        int serialViews2 = o2.getNoViews();
        return Integer.compare(serialViews1, serialViews2);
    };

    private static Comparator<Serial> descViews = (o1, o2) -> {
        int serialViews1 = o1.getNoViews();
        int serialViews2 = o2.getNoViews();
        return Integer.compare(serialViews2, serialViews1);
    };
    /**
     * @return
     */
    @Override
    public String toString() {
        return serial.getTitle();
    }
}
