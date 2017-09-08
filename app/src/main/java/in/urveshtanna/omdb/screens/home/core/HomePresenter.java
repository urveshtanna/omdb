package in.urveshtanna.omdb.screens.home.core;

import in.urveshtanna.omdb.screens.home.HomePageActivity;
import in.urveshtanna.omdb.tools.HelperClass;
import in.urveshtanna.omdb.tools.RetrofitException;
import in.urveshtanna.omdb.tools.rx.RxSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author urveshtanna
 * @version 1.0
 * @see HomePageActivity
 * @since 1.0
 */

public class HomePresenter {

    private HomePageModel model;
    private HomePageView homePageView;
    private RxSchedulers schedulers;
    private CompositeDisposable compositeDisposable;

    public HomePresenter(RxSchedulers schedulers, HomePageModel model, HomePageView view, CompositeDisposable compositeDisposable) {
        this.model = model;
        this.schedulers = schedulers;
        this.homePageView = view;
        this.compositeDisposable = compositeDisposable;
    }

    void onCreate(String name, String typeOf, String yearOfRelease, int page) {
        compositeDisposable.add(searchForMovieWithName(name, typeOf, yearOfRelease, page));
    }

    private Disposable searchForMovieWithName(String name, String typeOf, String yearOfRelease, int page) {
        return model.isNetworkAvailable().doOnNext(isInternetAvailable -> {
            if (!isInternetAvailable) {

            }
        }).filter(filterNetwork -> true)
                .flatMap(isAvailable -> model.getMovies(name, typeOf, yearOfRelease, page))
                .subscribeOn(schedulers.internet())
                .observeOn(schedulers.androidThread())
                .subscribe(searchPayloadModel -> homePageView.setSearchView(searchPayloadModel), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        RetrofitException error = (RetrofitException) throwable;
                        if (error.getResponse().code() == 401) {
                            homePageView.hideLoadingView();
                            HelperClass.showDialogMessage(homePageView.getActivity(), "Unauthorized", error.getMessage(), "Exit", null, null, false, new HelperClass.OnDialogClickListener() {
                                @Override
                                public void onPositiveClick() {
                                    homePageView.getActivity().finish();
                                }

                                @Override
                                public void onNegativeClick() {

                                }

                                @Override
                                public void onNeutralClick() {

                                }
                            });
                        } else {
                            HelperClass.showDialogMessage(homePageView.getActivity(), null, error.getMessage(), "Exit", null, null, true, null);
                        }
                    }
                });
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }

}
