package in.urveshtanna.omdb.screens.movieDetails.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.databinding.ItemMovieRatingBinding;
import in.urveshtanna.omdb.models.Ratings;
import in.urveshtanna.omdb.screens.movieDetails.MovieDetailActivity;

/**
 * @author urveshtanna
 * @version <Current-Version>
 * @see MovieDetailActivity >
 * @since 1.1
 */

public class RatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ratings> ratingsList = new ArrayList<>();
    private Context mContext;

    public RatingAdapter(List<Ratings> ratingsList, Context mContext) {
        this.ratingsList = ratingsList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieRatingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_movie_rating, parent, false);
        return new RatingHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RatingHolder) {
            ((RatingHolder) viewHolder).binding.setModel(ratingsList.get(viewHolder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        return ratingsList.size();
    }

    private static class RatingHolder extends RecyclerView.ViewHolder {

        ItemMovieRatingBinding binding;

        RatingHolder(ItemMovieRatingBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
