package in.urveshtanna.omdb.screens.home.dagger;

import dagger.Module;
import dagger.Provides;
import in.urveshtanna.omdb.screens.home.HomePageActivity;
import in.urveshtanna.omdb.tools.rx.AppRxSchedulers;
import in.urveshtanna.omdb.tools.rx.RxSchedulers;

/**
 * @author urveshtanna
 * @created 08/09/17
 */

@Module
public class HomeContextModule {

    private HomePageActivity activity;

    public HomeContextModule(HomePageActivity activity) {
        this.activity = activity;
    }

    @HomePageScope
    @Provides
    HomePageActivity providesContext(){
        return activity;
    }


    @HomePageScope
    @Provides
    RxSchedulers providesSchedulers(){
        return new AppRxSchedulers();
    }
}
