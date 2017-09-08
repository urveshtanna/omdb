package in.urveshtanna.omdb.application.builder;

import android.content.Context;

import dagger.Component;
import in.urveshtanna.omdb.api.MovieAPI;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

@AppScope
@Component(modules = {NetworkModule.class, AppContextModule.class, RxModule.class, MovieAPIServiceModule.class})
public interface AppComponent {

    Context getContext();

    MovieAPI getMovieApis();
}
