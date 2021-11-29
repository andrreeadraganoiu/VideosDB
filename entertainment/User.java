package entertainment;

import fileio.UserInputData;
import java.util.Comparator;

public class User {
    private final UserInputData user;
    private int numberOfRatings = 0;

    public User(final UserInputData user) {
        this.user = user;
    }
    /**
     * @return
     */
    public UserInputData getUser() {
        return user;
    }
    /**
     * @return
     */
    public int getNumberOfRatings() {
        return numberOfRatings;
    }
    /**
     *
     */
    public void updateNoRatings() {
        this.numberOfRatings += 1;
    }

    public static Comparator<User> getAscRatings() {
        return ascRatings;
    }

    public static Comparator<User> getDescRatings() {
        return descRatings;
    }

    public static Comparator<User> getAscName() {
        return ascName;
    }

    public static Comparator<User> getDescName() {
        return descName;
    }

    private static Comparator<User> ascRatings = (o1, o2) -> {
        int userRatings1 = o1.getNumberOfRatings();
        int userRatings2 = o2.getNumberOfRatings();
        return Integer.compare(userRatings1, userRatings2);
    };

    private static Comparator<User> descRatings = (o1, o2) -> {
        int userRatings1 = o1.getNumberOfRatings();
        int userRatings2 = o2.getNumberOfRatings();
        return Integer.compare(userRatings2, userRatings1);
    };

    private static Comparator<User> ascName = (o1, o2) -> {
        String userName1 = o1.getUser().getUsername();
        String userName2 = o2.getUser().getUsername();
        return userName1.compareTo(userName2);
    };

    private static Comparator<User> descName = (o1, o2) -> {
        String userName1 = o1.getUser().getUsername();
        String userName2 = o2.getUser().getUsername();
        return userName2.compareTo(userName1);
    };
    /**
     * @return
     */
    @Override
    public String toString() {
        return user.getUsername();
    }
}
