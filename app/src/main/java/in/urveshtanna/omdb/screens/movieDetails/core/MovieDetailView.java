package in.urveshtanna.omdb.screens.movieDetails.core;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.List;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.databinding.ActivityMovieDetailsBinding;
import in.urveshtanna.omdb.models.MovieModel;
import in.urveshtanna.omdb.models.Ratings;
import in.urveshtanna.omdb.screens.movieDetails.MovieDetailActivity;
import in.urveshtanna.omdb.screens.movieDetails.list.RatingAdapter;
import in.urveshtanna.omdb.tools.HelperClass;

/**
 * @author urveshtanna
 * @created 08/09/17
 */

public class MovieDetailView {

    private MovieModel movieModel;
    private boolean isDataFetched = false;
    private MovieDetailActivity movieDetailActivity;
    private ActivityMovieDetailsBinding binding;
    private Context mContext;

    public MovieDetailView(MovieDetailActivity movieDetailActivity) {
        this.movieDetailActivity = movieDetailActivity;
        binding = DataBindingUtil.setContentView(movieDetailActivity, R.layout.activity_movie_details);
        mContext = movieDetailActivity;
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
        if (movieModel != null && movieModel.getResponse().equals("True")) {
            hideLoadingView();
            setPageInfo(movieModel);
        } else {
            hideLoadingView();
            //noSearchResultFound(movieModel);
        }
    }

    void showLoadingView() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.appBar.setVisibility(View.GONE);
        binding.include.content.setVisibility(View.GONE);
    }

    private void hideLoadingView() {
        binding.progressBar.setVisibility(View.GONE);
        binding.appBar.setVisibility(View.VISIBLE);
        binding.include.content.setVisibility(View.VISIBLE);
    }

    private void setPageInfo(MovieModel movieModel) {
        //bind model
        binding.include.setModel(movieModel);

        binding.include.tvImdbRating.setText(movieModel.getImdbrating() + "/10 (" + movieModel.getImdbvotes() + " votes)");

        //Set Up Toolbar
        setUpToolbar(movieModel);

        //Update toolbar menu
        isDataFetched = true;
        movieDetailActivity.invalidateOptionsMenu();

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
        movieDetailActivity.setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDetailActivity.onBackPressed();
            }
        });
        binding.toolbar.setNavigationIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_back_white_24dp));
    }

    public void noSearchResultFound(MovieModel movieModel) {
        binding.progressBar.setVisibility(View.GONE);
        binding.appBar.setVisibility(View.GONE);
        binding.include.content.setVisibility(View.GONE);
        HelperClass.showToastBar(mContext, movieModel.getError());
    }

    public MovieModel getMovieModel() {
        return movieModel;
    }

    public boolean isDataFetched() {
        return isDataFetched;
    }
}
