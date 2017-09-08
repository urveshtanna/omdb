package in.urveshtanna.omdb.screens.home.core;


import in.urveshtanna.omdb.api.MovieAPI;
import in.urveshtanna.omdb.models.SearchPayloadModel;
import in.urveshtanna.omdb.screens.home.HomePageActivity;
import in.urveshtanna.omdb.tools.Utils;
import io.reactivex.Observable;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

public class HomePageModel {

    private MovieAPI movieAPI;
    private HomePageActivity homePageActivity;

    public HomePageModel(MovieAPI movieAPI, HomePageActivity homePageActivity) {
        this.movieAPI = movieAPI;
        this.homePageActivity = homePageActivity;
    }

    Observable<SearchPayloadModel> getMovies(String searchKey, String type, String yearOfRelease, int page) {
        return movieAPI.getList(searchKey, type, yearOfRelease, page);
    }

    Observable<Boolean> isNetworkAvailable() {
        return Utils.isNetworkAvailableObservable(homePageActivity);
    }
}
