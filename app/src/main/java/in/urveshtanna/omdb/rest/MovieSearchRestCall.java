package in.urveshtanna.omdb.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.otto.Bus;

import in.urveshtanna.omdb.entities.RequestUrl;
import in.urveshtanna.omdb.entities.SearchPayloadModel;
import in.urveshtanna.omdb.tools.Constants;
import in.urveshtanna.omdb.tools.JsonKeys;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class MovieSearchRestCall extends RestCall {

    private static final String GET_SEARCHED_MOVIES = "get_searched_movies";
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


    private interface PublicService {

        @GET("/")
        Call<SearchPayloadModel> getList(
                @Query(JsonKeys.QUERY_TO_SEARCH) String searchKey,
                @Query(JsonKeys.QUERY_TYPE_OF_RESULT) String type,
                @Query(JsonKeys.QUERY_YEAR_OF_RELEASE) String yearOfRelease,
                @Query(JsonKeys.QUERY_PAGE) int page);


    }
}
