package in.urveshtanna.omdb.screens.movieDetails.core;

import in.urveshtanna.omdb.api.MovieAPI;
import in.urveshtanna.omdb.models.MovieModel;
import in.urveshtanna.omdb.screens.movieDetails.MovieDetailActivity;
import in.urveshtanna.omdb.tools.Constants;
import in.urveshtanna.omdb.tools.Utils;
import io.reactivex.Observable;

/**
 * @author urveshtanna
 * @created 08/09/17
 */

public class MovieDetailModel {

    private MovieAPI movieAPI;
    private MovieDetailActivity movieDetailActivity;

    public MovieDetailModel(MovieAPI movieAPI, MovieDetailActivity movieDetailActivity) {
        this.movieAPI = movieAPI;
        this.movieDetailActivity = movieDetailActivity;
    }

    Observable<MovieModel> getMovieFromName(String title) {
        return movieAPI.getMovieFromName(title, Constants.DEFAULT_PLOT);
    }

    Observable<Boolean> isNetworkAvailable() {
        return Utils.isNetworkAvailableObservable(movieDetailActivity);
    }
}
