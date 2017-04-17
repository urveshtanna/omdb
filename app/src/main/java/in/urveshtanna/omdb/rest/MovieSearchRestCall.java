package in.urveshtanna.omdb.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.otto.Bus;

import in.urveshtanna.omdb.entities.MovieModel;
import in.urveshtanna.omdb.entities.RequestUrl;
import in.urveshtanna.omdb.entities.SearchPayloadModel;
import in.urveshtanna.omdb.tools.Constants;
import in.urveshtanna.omdb.tools.JsonKeys;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Contains all the rest requires for movie search
 *
 * @author urveshtanna
 * @version 1.0
 * @see in.urveshtanna.omdb.presenter.HomePresenter
 * @since 1.0
 */

public class MovieSearchRestCall extends RestCall {

    private static final String GET_SEARCHED_MOVIES = "get_searched_movies";
    private static final String GET_MOVIE_FROM_TITLE = "get_movie_from_title";

    private PublicService apiService;
    private Bus mBus;


    public MovieSearchRestCall(RequestUrl requestUrl, Bus bus) {
        this.mBus = bus;
        String baseUrl = requestUrl.isDebug() ? Constants.BASE_URL : Constants.BASE_URL;
        apiService = createService(baseUrl, PublicService.class, requestUrl);
    }


    public void getMoviesWithName(@NonNull String searchKey, @Nullable String typeOfResult, @Nullable String yearOfRelease, int page) {
        Call<SearchPayloadModel> call = apiService.getList(searchKey, typeOfResult, yearOfRelease, page);
        call.enqueue(new Callback<SearchPayloadModel>() {
            @Override
            public void onResponse(Call<SearchPayloadModel> call, Response<SearchPayloadModel> response) {
                SearchPayloadModel responseModel = response.body();
                if (responseModel != null) {
                    mBus.post(responseModel);
                }
            }

            @Override
            public void onFailure(Call<SearchPayloadModel> call, Throwable t) {
                onError(t, GET_SEARCHED_MOVIES, mBus);
            }
        });
    }

    public void getMoviesWithTitle(@NonNull String title, String plot) {
        Call<MovieModel> call = apiService.getMovieFromName(title, plot);
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                MovieModel responseModel = response.body();
                if (responseModel != null) {
                    mBus.post(responseModel);
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                onError(t, GET_MOVIE_FROM_TITLE, mBus);
            }
        });
    }


    private interface PublicService {

        @GET("/")
        Call<SearchPayloadModel> getList(@Query(JsonKeys.QUERY_TO_SEARCH) String searchKey,
                                         @Query(JsonKeys.QUERY_TYPE_OF_RESULT) String type,
                                         @Query(JsonKeys.QUERY_YEAR_OF_RELEASE) String yearOfRelease,
                                         @Query(JsonKeys.QUERY_PAGE) int page);

        @GET("/")
        Call<MovieModel> getMovieFromName(@Query(JsonKeys.QUERY_TITLE) String title,
                                          @Query(JsonKeys.QUERY_PLOT) String plot);

    }
}
