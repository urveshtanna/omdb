package in.urveshtanna.omdb.application.builder;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author urveshtanna
 * @created 07/09/17
 */

@Module
public class AppContextModule {

    private Context context;

    public AppContextModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    Context providesContext(){
        return context;
    }
}
