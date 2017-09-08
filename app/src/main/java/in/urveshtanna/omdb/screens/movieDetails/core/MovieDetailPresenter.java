package in.urveshtanna.omdb.screens.movieDetails.core;

import in.urveshtanna.omdb.tools.HelperClass;
import in.urveshtanna.omdb.tools.RetrofitException;
import in.urveshtanna.omdb.tools.rx.RxSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Adapter used to search product with pricing and navigate to product details
 *
 * @author urveshtanna
 * @version <Current-Version>
 * @see <Usage>
 * @since 1.2.0
 */

public class MovieDetailPresenter {

    private MovieDetailModel model;
    private CompositeDisposable disposable;
    private MovieDetailView movieDetailView;
    private RxSchedulers rxSchedulers;

    public MovieDetailPresenter(MovieDetailModel model, CompositeDisposable disposable, MovieDetailView movieDetailView, RxSchedulers rxSchedulers) {
        this.model = model;
        this.disposable = disposable;
        this.movieDetailView = movieDetailView;
        this.rxSchedulers = rxSchedulers;
    }

    public void onCreate(String title) {
        movieDetailView.showLoadingView();
        disposable.add(getDetailsFromTitle(title));
    }

    public void onDestroy() {
        disposable.clear();
    }

    public Disposable getDetailsFromTitle(String title) {
        return model.isNetworkAvailable().doOnNext(isInternetAvailable -> {
            if (!isInternetAvailable) {

            }
        }).filter(filterNetwork -> true)
                .flatMap(isAvailable -> model.getMovieFromName(title))
                .subscribeOn(rxSchedulers.internet())
                .observeOn(rxSchedulers.androidThread())
                .subscribe(movieModel -> movieDetailView.setMovieModel(movieModel), throwable -> {
                    RetrofitException error = (RetrofitException) throwable;
                    movieDetailView.hideLoadingView();
                    if (error.getResponse().code() == 401) {
                        HelperClass.showDialogMessage(movieDetailView.getActivity(), "Unauthorized", error.getMessage(), "Exit", null, null, false, new HelperClass.OnDialogClickListener() {
                            @Override
                            public void onPositiveClick() {
                                movieDetailView.getActivity().finish();
                            }

                            @Override
                            public void onNegativeClick() {

                            }

                            @Override
                            public void onNeutralClick() {

                            }
                        });
                    } else {
                        HelperClass.showDialogMessage(movieDetailView.getActivity(), null, error.getMessage(), "Exit", null, null, true, null);
                    }
                });
    }
}
