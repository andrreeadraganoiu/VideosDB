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

    public Serial(SerialInputData serial) {
        this.serial = serial;
        this.ratings = new ArrayList<>();
    }

    public SerialInputData getSerial() {
        return serial;
    }

    public int getNoFavoriteAdd() {
        return noFavoriteAdd;
    }

    public void updateNoFavoriteAdd() {
        this.noFavoriteAdd += 1;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration += duration;
    }

    public int getNoViews() {
        return noViews;
    }

    public void updateNoViews(int noViews) {
        this.noViews += noViews;
    }

    public Double getRatingGrade() {

        Double sum = 0.0, seasonGrade;
        int nr = this.getSerial().getNumberSeason();

        if (nr == 0) {
            return 0.0;
        }
        else {
            for(int i = 0; i < nr; i++) {
                seasonGrade = 0.0;
                if(this.getSerial().getSeasons().get(i).getRatings().size() != 0) {
                    for(int j = 0; j < this.getSerial().getSeasons().get(i).getRatings().size(); j++) {
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

    public static Comparator<Serial> ascSerialName = (o1, o2) -> {
        String serialName1 = o1.getSerial().getTitle();
        String serialName2 = o2.getSerial().getTitle();

        return serialName1.compareTo(serialName2);
    };

    public static Comparator<Serial> descSerialName = (o1, o2) -> {
        String serialName1 = o1.getSerial().getTitle();
        String serialName2 = o2.getSerial().getTitle();

        return serialName2.compareTo(serialName1);
    };

    public static Comparator<Serial> ascSerialRating = (o1, o2) -> {
        Double serialRating1 = o1.getRatingGrade();
        Double serialRating2 = o2.getRatingGrade();

        return Double.compare(serialRating1, serialRating2);
    };

    public static Comparator<Serial> descSerialRating = (o1, o2) -> {
        Double serialRating1 = o1.getRatingGrade();
        Double serialRating2 = o2.getRatingGrade();

        return Double.compare(serialRating2, serialRating1);
    };

    public static Comparator<Serial> ascSerialFav = (o1, o2) -> {
        int noFavoriteAdd1 = o1.getNoFavoriteAdd();
        int noFavoriteAdd2 = o2.getNoFavoriteAdd();

        return Integer.compare(noFavoriteAdd1, noFavoriteAdd2);
    };

    public static Comparator<Serial> descSerialFav = (o1, o2) -> {
        int noFavoriteAdd1 = o1.getNoFavoriteAdd();
        int noFavoriteAdd2 = o2.getNoFavoriteAdd();

        return Integer.compare(noFavoriteAdd2, noFavoriteAdd1);
    };

    public static Comparator<Serial> ascSerialDuration = (o1, o2) -> {
        int serialDuration1 = o1.getDuration();
        int serialDuration2 = o2.getDuration();

        return Integer.compare(serialDuration1, serialDuration2);
    };

    public static Comparator<Serial> descSerialDuration = (o1, o2) -> {
        int serialDuration1 = o1.getDuration();
        int serialDuration2 = o2.getDuration();

        return Integer.compare(serialDuration2, serialDuration1);
    };

    public static Comparator<Serial> ascSerialViews = (o1, o2) -> {
        int serialViews1 = o1.getNoViews();
        int serialViews2 = o2.getNoViews();

        return Integer.compare(serialViews1, serialViews2);
    };

    public static Comparator<Serial> descSerialViews = (o1, o2) -> {
        int serialViews1 = o1.getNoViews();
        int serialViews2 = o2.getNoViews();

        return Integer.compare(serialViews2, serialViews1);
    };

    @Override
    public String toString() {
        return serial.getTitle();
    }
}
