package in.urveshtanna.omdb.screens.movieDetails.dagger;

import dagger.Component;
import in.urveshtanna.omdb.application.builder.AppComponent;
import in.urveshtanna.omdb.screens.movieDetails.MovieDetailActivity;

/**
 * @author urveshtanna
 * @created 08/09/17
 */
@MovieDetailScope
@Component(dependencies = {AppComponent.class}, modules = {MovieDetailModule.class})
public interface MovieDetailComponent {

    void inject(MovieDetailActivity movieDetailActivity);
}
