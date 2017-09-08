package in.urveshtanna.omdb.screens.movieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.application.AppController;
import in.urveshtanna.omdb.screens.movieDetails.core.MovieDetailPresenter;
import in.urveshtanna.omdb.screens.movieDetails.core.MovieDetailView;
import in.urveshtanna.omdb.screens.movieDetails.dagger.DaggerMovieDetailComponent;
import in.urveshtanna.omdb.screens.movieDetails.dagger.MovieDetailModule;
import in.urveshtanna.omdb.tools.CustomChromeTab;
import in.urveshtanna.omdb.tools.HelperClass;

public class MovieDetailActivity extends AppCompatActivity {

    @Inject
    MovieDetailView movieDetailView;

    @Inject
    MovieDetailPresenter movieDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMovieDetailComponent.builder().appComponent(AppController.getNetComponent()).movieDetailModule(new MovieDetailModule(this)).build().inject(this);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getString("title") != null) {
            movieDetailPresenter.onCreate(getIntent().getExtras().getString("title"));
        } else {
            HelperClass.showToastBar(this, getString(R.string.something_went_wrong_message));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieDetailPresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (movieDetailView.isDataFetched()) {
            getMenuInflater().inflate(R.menu.movie_details_menu, menu);
            if (movieDetailView.getMovieModel() != null && movieDetailView.getMovieModel().getWebsite() != null && !movieDetailView.getMovieModel().getWebsite().equals("N/A")) {
                menu.findItem(R.id.action_website).setVisible(true);
            } else {
                menu.findItem(R.id.action_website).setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            //Share using other apps
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_message, movieDetailView.getMovieModel().getTitle(), "http://www.imdb.com/title/" + movieDetailView.getMovieModel().getImdbid() + "/"));
                startActivity(Intent.createChooser(intent, getString(R.string.share_with_friends)));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (item.getItemId() == R.id.action_website) {
            startActivity(new Intent(this, CustomChromeTab.class).putExtra("url", movieDetailView.getMovieModel().getWebsite()));
        }
        return super.onOptionsItemSelected(item);
    }
}
