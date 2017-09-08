package in.urveshtanna.omdb.screens.home.dagger;

import dagger.Module;
import dagger.Provides;
import in.urveshtanna.omdb.api.MovieAPI;
import in.urveshtanna.omdb.screens.home.HomePageActivity;
import in.urveshtanna.omdb.screens.home.core.HomePageModel;
import in.urveshtanna.omdb.screens.home.core.HomePageView;
import in.urveshtanna.omdb.screens.home.core.HomePresenter;
import in.urveshtanna.omdb.tools.rx.RxSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

@Module
public class HomePageModule {

    @HomePageScope
    @Provides
    HomePageView providesView(HomePageActivity activity) {
        return new HomePageView(activity);
    }

    @HomePageScope
    @Provides
    HomePresenter providePresenter(RxSchedulers schedulers, HomePageView view, HomePageModel model) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        return new HomePresenter(schedulers, model, view, compositeDisposable);
    }

    @HomePageScope
    @Provides
    HomePageModel providesHomePageModel(MovieAPI movieAPI, HomePageActivity activity) {
        return new HomePageModel(movieAPI, activity);
    }
}
