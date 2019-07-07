package nl.saxion.playground.template.lumberjack_simulator.local_lib;

import android.app.Application;
import android.content.Context;

/**
 * Class that gives global app context in non activity classes that need to pass context
 * @author Michael Cornelisse
 */

public class GlobalApplication extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();

        /* If you has other classes that need context object to initialize when application is created,
         you can use the appContext here to process. */
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
