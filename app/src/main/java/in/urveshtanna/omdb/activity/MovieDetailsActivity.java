package in.urveshtanna.omdb.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.adapters.RatingAdapter;
import in.urveshtanna.omdb.databinding.ActivityMovieDetailsBinding;
import in.urveshtanna.omdb.entities.MovieModel;
import in.urveshtanna.omdb.entities.Ratings;
import in.urveshtanna.omdb.presenter.MovieDetailsPresenter;
import in.urveshtanna.omdb.tools.CustomChromeTab;
import in.urveshtanna.omdb.tools.HelperClass;
import in.urveshtanna.omdb.view.MovieDetailsView;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsView {

    ActivityMovieDetailsBinding binding;
    private Context mContext;
    private boolean isDataFetched = false;
    private MovieModel movieModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        mContext = this;
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getString("title") != null) {
            fetchDetailsFromTitle(getIntent().getExtras().getString("title"));
        } else {
            HelperClass.showToastBar(mContext, getString(R.string.something_went_wrong_message));
        }
    }

    private void fetchDetailsFromTitle(String title) {
        MovieDetailsPresenter movieDetailsPresenter = new MovieDetailsPresenter(mContext, this);
        movieDetailsPresenter.start();
        movieDetailsPresenter.getDetailsFromTitle(title);
    }

    @Override
    public void showLoadingView() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.appBar.setVisibility(View.GONE);
        binding.include.content.setVisibility(View.GONE);
    }

    @Override
    public void hideLoadingView() {
        binding.progressBar.setVisibility(View.GONE);
        binding.appBar.setVisibility(View.VISIBLE);
        binding.include.content.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPageInfo(MovieModel movieModel) {

        setMovieModel(movieModel);

        //bind model
        binding.include.setModel(movieModel);

        binding.include.tvImdbRating.setText(movieModel.getImdbrating() + "/10 (" + movieModel.getImdbvotes() + " votes)");

        //Set Up Toolbar
        setUpToolbar(movieModel);

        //Update toolbar menu
        isDataFetched = true;
        invalidateOptionsMenu();

        //SetUpRating Adapter
        showRatingList(movieModel.getRatings());
    }

    private void showRatingList(List<Ratings> ratings) {
        binding.include.ratingRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        RatingAdapter adapter = new RatingAdapter(ratings, mContext);
        binding.include.ratingRecyclerView.setAdapter(adapter);
    }

    private void setUpToolbar(MovieModel movieModel) {
        binding.toolbar.setTitle(movieModel.getTitle());
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.toolbar.setNavigationIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_back_white_24dp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isDataFetched) {
            getMenuInflater().inflate(R.menu.movie_details_menu, menu);
            if (movieModel != null && movieModel.getWebsite() != null) {
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
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_message, movieModel.getTitle(), "http://www.imdb.com/title/" + movieModel.getImdbid() + "/"));
                startActivity(Intent.createChooser(intent, mContext.getString(R.string.share_with_friends)));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (item.getItemId() == R.id.action_website) {
            mContext.startActivity(new Intent(mContext, CustomChromeTab.class).putExtra("url", "http://www.imdb.com/title/" + movieModel.getImdbid() + "/"));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void noSearchResultFound(MovieModel movieModel) {
        binding.progressBar.setVisibility(View.GONE);
        binding.appBar.setVisibility(View.GONE);
        binding.include.content.setVisibility(View.GONE);
        HelperClass.showToastBar(mContext, movieModel.getError());
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }
}
