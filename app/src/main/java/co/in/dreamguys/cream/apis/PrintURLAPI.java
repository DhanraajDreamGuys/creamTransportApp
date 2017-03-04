package co.in.dreamguys.cream.apis;

/**
 * Created by user5 on 03-03-2017.
 */
public class PrintURLAPI {
    private static PrintURLAPI ourInstance = new PrintURLAPI();

    public static PrintURLAPI getInstance() {
        return ourInstance;
    }

    private PrintURLAPI() {
    }
}
