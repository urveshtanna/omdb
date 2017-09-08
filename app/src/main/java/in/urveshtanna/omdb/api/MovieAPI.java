package in.urveshtanna.omdb.api;

import in.urveshtanna.omdb.models.MovieModel;
import in.urveshtanna.omdb.models.SearchPayloadModel;
import in.urveshtanna.omdb.tools.JsonKeys;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

public interface MovieAPI {

    @GET("/?apikey=BanMePlz&")
    Observable<SearchPayloadModel> getList(@Query(JsonKeys.QUERY_TO_SEARCH) String searchKey,
                                           @Query(JsonKeys.QUERY_TYPE_OF_RESULT) String type,
                                           @Query(JsonKeys.QUERY_YEAR_OF_RELEASE) String yearOfRelease,
                                           @Query(JsonKeys.QUERY_PAGE) int page);

    @GET("/?apikey=BanMePlz&")
    Observable<MovieModel> getMovieFromName(@Query(JsonKeys.QUERY_TITLE) String title,
                                            @Query(JsonKeys.QUERY_PLOT) String plot);

}
