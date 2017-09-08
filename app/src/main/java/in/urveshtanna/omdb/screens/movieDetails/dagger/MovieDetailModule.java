package in.urveshtanna.omdb.screens.movieDetails.dagger;

import dagger.Module;
import dagger.Provides;
import in.urveshtanna.omdb.api.MovieAPI;
import in.urveshtanna.omdb.screens.movieDetails.MovieDetailActivity;
import in.urveshtanna.omdb.screens.movieDetails.core.MovieDetailModel;
import in.urveshtanna.omdb.screens.movieDetails.core.MovieDetailPresenter;
import in.urveshtanna.omdb.screens.movieDetails.core.MovieDetailView;
import in.urveshtanna.omdb.tools.rx.AppRxSchedulers;
import in.urveshtanna.omdb.tools.rx.RxSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author urveshtanna
 * @created 08/09/17
 */

@Module
public class MovieDetailModule {

    private MovieDetailActivity movieDetailActivity;

    public MovieDetailModule(MovieDetailActivity movieDetailActivity) {
        this.movieDetailActivity = movieDetailActivity;
    }

    @MovieDetailScope
    @Provides
    MovieDetailActivity provideContext() {
        return movieDetailActivity;
    }

    @MovieDetailScope
    @Provides
    RxSchedulers provideSchedulers(){
        return new AppRxSchedulers();
    }

    @MovieDetailScope
    @Provides
    MovieDetailPresenter providePresenter(MovieDetailModel model, MovieDetailView movieDetailView, RxSchedulers rxSchedulers) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        return new MovieDetailPresenter(model, compositeDisposable, movieDetailView, rxSchedulers);
    }

    @MovieDetailScope
    @Provides
    MovieDetailView provideView() {
        return new MovieDetailView(movieDetailActivity);
    }

    @MovieDetailScope
    @Provides
    MovieDetailModel provideModel(MovieAPI movieAPI) {
        return new MovieDetailModel(movieAPI, movieDetailActivity);
    }

}
