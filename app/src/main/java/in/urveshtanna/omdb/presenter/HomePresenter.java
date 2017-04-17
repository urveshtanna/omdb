package in.urveshtanna.omdb.presenter;

import android.content.Context;

import com.squareup.otto.Subscribe;

import in.urveshtanna.omdb.MainThreadBus;
import in.urveshtanna.omdb.entities.ErrorModel;
import in.urveshtanna.omdb.entities.SearchPayloadModel;
import in.urveshtanna.omdb.rest.MovieSearchRestCall;
import in.urveshtanna.omdb.tools.Utils;
import in.urveshtanna.omdb.view.HomePageView;

public class HomePresenter extends Presenter<HomePageView> {

    MovieSearchRestCall searchRestCall;
    private String TAG = "HomePresenter";

    public HomePresenter(Context context, HomePageView view) {
        super(context, view, new MainThreadBus());
        searchRestCall = new MovieSearchRestCall(getRequestUrl(), getBus());
    }

    public void searchForMovieWithName(String name, String typeOf, String yearOfRelease, int page) {
        if (Utils.isInternetConnected(getContext(), true)) {
            searchRestCall.getMoviesWithName(name, typeOf, yearOfRelease, page);
        }
    }

    @Subscribe
    public void responseModelEvent(SearchPayloadModel searchPayloadModel) {
        if (searchPayloadModel != null && searchPayloadModel.getResponse().equals("True")) {
            getView().hideLoadingView();
            getView().paginationData(searchPayloadModel);
            getView().setAdapter(searchPayloadModel.getSearchModelList());
        } else {
            getView().hideLoadingView();
            getView().noSearchResultFound(searchPayloadModel);
        }
    }

    @Subscribe
    public void errorModelEvent(ErrorModel errorModel) {
        super.error(errorModel, true);
        if (getView() != null) {
            getView().hideLoadingView();
        }
    }

}
