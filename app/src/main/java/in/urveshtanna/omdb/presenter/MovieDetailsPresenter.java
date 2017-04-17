package in.urveshtanna.omdb.presenter;

import android.content.Context;

import com.squareup.otto.Subscribe;

import in.urveshtanna.omdb.MainThreadBus;
import in.urveshtanna.omdb.entities.ErrorModel;
import in.urveshtanna.omdb.entities.MovieModel;
import in.urveshtanna.omdb.rest.MovieSearchRestCall;
import in.urveshtanna.omdb.tools.Constants;
import in.urveshtanna.omdb.view.MovieDetailsView;

/**
 * Adapter used to search product with pricing and navigate to product details
 *
 * @author urveshtanna
 * @version <Current-Version>
 * @see <Usage>
 * @since 1.2.0
 */

public class MovieDetailsPresenter extends Presenter<MovieDetailsView> {

    private MovieSearchRestCall movieSearchRestCall;

    public MovieDetailsPresenter(Context context, MovieDetailsView view) {
        super(context, view,new MainThreadBus());
        movieSearchRestCall = new MovieSearchRestCall(getRequestUrl(),getBus());
    }

    public void getDetailsFromTitle(String title){
        getView().showLoadingView();
        movieSearchRestCall.getMoviesWithTitle(title, Constants.DEFAULT_PLOT);
    }


    @Subscribe
    public void responseModelEvent(MovieModel movieModel) {
        if (movieModel != null && movieModel.getResponse().equals("True")) {
            getView().hideLoadingView();
            getView().setPageInfo(movieModel);
        } else {
            getView().hideLoadingView();
            getView().noSearchResultFound(movieModel);
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
