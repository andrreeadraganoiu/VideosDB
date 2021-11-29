package main;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import entertainment.Actor;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import fileio.Input;
import fileio.InputLoader;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ActorInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        ArrayList<Movie> movieArray = new ArrayList<>();
        for (MovieInputData movieInputData : input.getMovies()) {
           Movie thisMovie = new Movie(movieInputData);
           movieArray.add(thisMovie);
        }

        ArrayList<Serial> serialArray = new ArrayList<>();
        for (SerialInputData serialInputData : input.getSerials()) {
            Serial thisSerial = new Serial(serialInputData);
            serialArray.add(thisSerial);
        }

        ArrayList<User> userArray = new ArrayList<>();
        for (UserInputData userInputData : input.getUsers()) {
            User thisUser = new User(userInputData);
            userArray.add(thisUser);
        }

        ArrayList<Actor> actorsArray = new ArrayList<>();
        for (ActorInputData actorInputData : input.getActors()) {
            Actor thisActor = new Actor(actorInputData);
            actorsArray.add(thisActor);
        }

        Action.doAction(input, arrayResult, movieArray, serialArray, userArray, actorsArray);

        fileWriter.closeJSON(arrayResult);
    }
}
