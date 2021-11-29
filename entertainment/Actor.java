package entertainment;

import fileio.ActorInputData;

import java.util.Comparator;

public class Actor {
    private final ActorInputData actor;
    private Double rating;
    private int noAwards = 0;

    public Actor(ActorInputData actor) {
        this.actor = actor;
    }

    public ActorInputData getActor() {
        return actor;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getNoAwards() {
        return noAwards;
    }

    public void setNoAwards(int nrOfAwards) {
        this.noAwards += nrOfAwards;
    }

    public static Comparator<Actor> ascActorRating = (o1, o2) -> {
        Double actorRating1 = o1.getRating();
        Double actorRating2 = o2.getRating();

        return Double.compare(actorRating1, actorRating2);
    };

    public static Comparator<Actor> descActorRating = (o1, o2) -> {
        Double actorRating1 = o1.getRating();
        Double actorRating2 = o2.getRating();

        return Double.compare(actorRating2, actorRating1);
    };

    public static Comparator<Actor> ascActorName = (o1, o2) -> {
        String actorName1 = o1.getActor().getName();
        String actorName2 = o2.getActor().getName();

        return actorName1.compareTo(actorName2);
    };

    public static Comparator<Actor> descActorName = (o1, o2) -> {
        String actorName1 = o1.getActor().getName();
        String actorName2 = o2.getActor().getName();

        return actorName2.compareTo(actorName1);
    };

    public static Comparator<Actor> ascNoAwards = (o1, o2) -> {
        int actorNoAwards1 = o1.getNoAwards();
        int actorNoAwards2 = o2.getNoAwards();

        return Integer.compare(actorNoAwards1, actorNoAwards2);
    };

    public static Comparator<Actor> descNoAwards = (o1, o2) -> {
        int actorNoAwards1 = o1.getNoAwards();
        int actorNoAwards2 = o2.getNoAwards();

        return Integer.compare(actorNoAwards2, actorNoAwards1);
    };

    @Override
    public String toString() {
        return actor.getName();
    }
}
