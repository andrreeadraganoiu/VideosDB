package entertainment;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Comparator;

public class User {
    private final UserInputData user;
   // private final ArrayList<String> favoriteSerials;
    private int numberOfRatings = 0;

    public User(UserInputData user) {
        this.user = user;
        //this.favoriteSerials = new ArrayList<>();
    }

    public UserInputData getUser() {
        return user;
    }

//    //public ArrayList<String> getFavoriteSerials() {
//        return favoriteSerials;
//    }


    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void updateNoRatings() {
        this.numberOfRatings += 1;
    }

    public static Comparator<User> ascUserRatings = (o1, o2) -> {
        int userRatings1 = o1.getNumberOfRatings();
        int userRatings2 = o2.getNumberOfRatings();

        return Integer.compare(userRatings1, userRatings2);
    };

    public static Comparator<User> descUserRatings = (o1, o2) -> {
        int userRatings1 = o1.getNumberOfRatings();
        int userRatings2 = o2.getNumberOfRatings();

        return Integer.compare(userRatings2, userRatings1);
    };

    public static Comparator<User> ascName = (o1, o2) -> {
        String userName1 = o1.getUser().getUsername();
        String userName2 = o2.getUser().getUsername();

        return userName1.compareTo(userName2);
    };

    public static Comparator<User> descName = (o1, o2) -> {
        String userName1 = o1.getUser().getUsername();
        String userName2 = o2.getUser().getUsername();

        return userName2.compareTo(userName1);
    };

    @Override
    public String toString() {
        return user.getUsername();
    }
}
