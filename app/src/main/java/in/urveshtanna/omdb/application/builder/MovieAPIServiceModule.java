package in.urveshtanna.omdb.application.builder;

import dagger.Module;
import dagger.Provides;
import in.urveshtanna.omdb.api.MovieAPI;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.urveshtanna.omdb.tools.Constants.BASE_URL;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

@Module
class MovieAPIServiceModule {

    @AppScope
    @Provides
    MovieAPI provideMovieAPIService(OkHttpClient client, GsonConverterFactory gson, CallAdapter.Factory rxAdapter) {
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(BASE_URL).addConverterFactory(gson).
                        addCallAdapterFactory(rxAdapter).build();

        return retrofit.create(MovieAPI.class);
    }
}
