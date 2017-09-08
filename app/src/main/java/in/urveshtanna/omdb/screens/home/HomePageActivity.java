package in.urveshtanna.omdb.screens.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.application.AppController;
import in.urveshtanna.omdb.screens.home.core.HomePageView;
import in.urveshtanna.omdb.screens.home.core.HomePresenter;
import in.urveshtanna.omdb.screens.home.dagger.DaggerHomePageComponent;
import in.urveshtanna.omdb.screens.home.dagger.HomeContextModule;
import in.urveshtanna.omdb.screens.home.dagger.HomePageModule;
import in.urveshtanna.omdb.tools.HelperClass;

/**
 * Main launcher activity for the app showing list of search result
 *
 * @author urveshtanna
 * @version 1.0
 * @since 1.0
 */

public class HomePageActivity extends AppCompatActivity {

    private SearchView searchView;

    @Inject
    HomePageView homePageView;

    @Inject
    HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerHomePageComponent.builder().appComponent(AppController.getNetComponent()).homeContextModule(new HomeContextModule(this)).homePageModule(new HomePageModule()).build().inject(this);
        bindPage();
    }

    private void bindPage() {
        homePageView.setPresenter(homePresenter);
        homePageView.setUpFilterBottomSheet();
        homePageView.initializePager();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        MenuItem menuSearch = menu.findItem(R.id.menu_search);
        searchView = (SearchView) menuSearch.getActionView();
        if (searchView != null) {
            homePageView.setSearchView(searchView);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView != null && !searchView.isIconified()) {
            searchView.setIconified(true);
        } else if (homePageView.count >= 1) {
            super.onBackPressed();
        } else {
            HelperClass.showToastBar(this, "Press again to exit");
            homePageView.count++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
    }
}
