package in.urveshtanna.omdb.application;

import android.app.Application;

import in.urveshtanna.omdb.BuildConfig;
import in.urveshtanna.omdb.application.builder.AppComponent;
import in.urveshtanna.omdb.application.builder.AppContextModule;
import in.urveshtanna.omdb.application.builder.DaggerAppComponent;
import timber.log.Timber;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

public class AppController extends Application{

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initDagger();
    }

    private void initDagger() {
        appComponent = DaggerAppComponent
                .builder()
                .appContextModule(new AppContextModule(this))
                .build();
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    //TODO  decide what to log in release version
                }
            });
        }
    }

    public static AppComponent getNetComponent() {
        return appComponent;
    }

}
