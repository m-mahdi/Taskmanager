package ir.mohammadi.taskmanager;

import android.app.Application;

import retrofit2.Retrofit;

public class G extends Application {
    public static String baseUrl = "https://api.backtory.com/";
    public static Retrofit retrofit = null;
    public static String token;

    protected static G instance;

    public G() {
        super();
        instance = this;
    }

    public static String getBearerToken(){
        return "Bearer " + token;
    }
}
