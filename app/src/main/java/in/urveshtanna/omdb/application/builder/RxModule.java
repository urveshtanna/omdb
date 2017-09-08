package in.urveshtanna.omdb.application.builder;

import dagger.Module;
import dagger.Provides;
import in.urveshtanna.omdb.tools.rx.AppRxSchedulers;
import in.urveshtanna.omdb.tools.rx.RxSchedulers;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

@Module
class RxModule {

    @Provides
    RxSchedulers provideRxSchedulers() {
        return new AppRxSchedulers();
    }
}
