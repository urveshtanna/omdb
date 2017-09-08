package in.urveshtanna.omdb.screens.home.dagger;

import dagger.Component;
import in.urveshtanna.omdb.application.builder.AppComponent;
import in.urveshtanna.omdb.screens.home.HomePageActivity;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

@HomePageScope
@Component(modules = {HomeContextModule.class, HomePageModule.class}, dependencies = {AppComponent.class})
public interface HomePageComponent {

    void inject(HomePageActivity homePageActivity);
}
