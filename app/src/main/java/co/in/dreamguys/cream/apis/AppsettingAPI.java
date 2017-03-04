package co.in.dreamguys.cream.apis;

/**
 * Created by user5 on 03-03-2017.
 */
public class AppsettingAPI {
    private static AppsettingAPI ourInstance = new AppsettingAPI();

    public static AppsettingAPI getInstance() {
        return ourInstance;
    }

    private AppsettingAPI() {
    }
}
