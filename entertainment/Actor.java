package entertainment;

import fileio.ActorInputData;

import java.util.Comparator;

public class Actor {
    private final ActorInputData actor;
    private Double rating;
    private int noAwards = 0;

    public Actor(final ActorInputData actor) {
        this.actor = actor;
    }
    /**
     * @return
     */
    public ActorInputData getActor() {
        return actor;
    }
    /**
     * @return
     */
    public Double getRating() {
        return rating;
    }
    /**
     * @param rating
     */
    public void setRating(final Double rating) {
        this.rating = rating;
    }
    /**
     * @return
     */
    public int getNoAwards() {
        return noAwards;
    }
    /**
     * @param nrOfAwards
     */
    public void setNoAwards(final int nrOfAwards) {
        this.noAwards = nrOfAwards;
    }

    public static Comparator<Actor> getAscRating() {
        return ascRating;
    }

    public static Comparator<Actor> getDescRating() {
        return descRating;
    }

    public static Comparator<Actor> getAscName() {
        return ascName;
    }

    public static Comparator<Actor> getDescName() {
        return descName;
    }

    public static Comparator<Actor> getAscNoAwards() {
        return ascNoAwards;
    }

    public static Comparator<Actor> getDescNoAwards() {
        return descNoAwards;
    }

    private static Comparator<Actor> ascRating = (o1, o2) -> {
        Double actorRating1 = o1.getRating();
        Double actorRating2 = o2.getRating();
        return Double.compare(actorRating1, actorRating2);
    };

    private static Comparator<Actor> descRating = (o1, o2) -> {
        Double actorRating1 = o1.getRating();
        Double actorRating2 = o2.getRating();
        return Double.compare(actorRating2, actorRating1);
    };

    private static Comparator<Actor> ascName = (o1, o2) -> {
        String actorName1 = o1.getActor().getName();
        String actorName2 = o2.getActor().getName();
        return actorName1.compareTo(actorName2);
    };

    private static Comparator<Actor> descName = (o1, o2) -> {
        String actorName1 = o1.getActor().getName();
        String actorName2 = o2.getActor().getName();
        return actorName2.compareTo(actorName1);
    };

    private static Comparator<Actor> ascNoAwards = (o1, o2) -> {
        int actorNoAwards1 = o1.getNoAwards();
        int actorNoAwards2 = o2.getNoAwards();
        return Integer.compare(actorNoAwards1, actorNoAwards2);
    };

    private static Comparator<Actor> descNoAwards = (o1, o2) -> {
        int actorNoAwards1 = o1.getNoAwards();
        int actorNoAwards2 = o2.getNoAwards();
        return Integer.compare(actorNoAwards2, actorNoAwards1);
    };
    /**
     * @return
     */
    @Override
    public String toString() {
        return actor.getName();
    }
}
