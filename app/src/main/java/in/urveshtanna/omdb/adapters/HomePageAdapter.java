package in.urveshtanna.omdb.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.databinding.ItemFooterViewBinding;
import in.urveshtanna.omdb.databinding.ItemSearchCardBinding;
import in.urveshtanna.omdb.entities.MovieModel;
import in.urveshtanna.omdb.tools.CustomChromeTab;
import in.urveshtanna.omdb.tools.Utils;

/**
 * Adapter used to search product with pricing and navigate to product details
 *
 * @author urveshtanna
 * @version 1.0
 * @see in.urveshtanna.omdb.activity.HomePageActivity
 * @since 1.0
 */

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> movieModelList = new ArrayList<>();
    private Context mContext;
    private static final int HEADER = 1;
    private static final int ITEM = 2;
    private static final int LOADING = 3;
    private boolean isFooterEnabled = true;


    public HomePageAdapter(List<MovieModel> movieModelList, Context mContext) {
        this.movieModelList = movieModelList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == ITEM) {
            ItemSearchCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_search_card, parent, false);
            viewHolder = new MovielHolder(binding);
        } else if (viewType == HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_header_view, parent, false);
            viewHolder = new Header(view);
        } else if (viewType == LOADING) {
            ItemFooterViewBinding itemFooterViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_footer_view, parent, false);
            viewHolder = new Footer(itemFooterViewBinding);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else if (position == movieModelList.size() + 1) {
            return LOADING;
        } else {
            return ITEM;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof Footer) {
            bindLoadingViewHolder(viewHolder);
        } else if (viewHolder instanceof MovielHolder) {
            bindItem(viewHolder);
        }
    }

    private void bindItem(RecyclerView.ViewHolder viewHolder) {
        MovielHolder holder = (MovielHolder) viewHolder;
        final MovieModel movieModel = movieModelList.get(holder.getAdapterPosition() - 1);
        holder.itemSearchCardBinding.setModel(movieModel);

        holder.itemSearchCardBinding.btnImdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CustomChromeTab.class).putExtra("url", "http://www.imdb.com/title/" + movieModel.getImdbid() + "/"));
            }
        });

        //Change color according to type of result
        Drawable background = holder.itemSearchCardBinding.tvTypeOfResult.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(Utils.getResultTypeColor(mContext, movieModel.getType()));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(Utils.getResultTypeColor(mContext, movieModel.getType()));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(Utils.getResultTypeColor(mContext, movieModel.getType()));
        }
    }

    @Override
    public int getItemCount() {
        return movieModelList.size() + 2;
    }

    public void enableFooter(boolean isEnabled) {
        this.isFooterEnabled = isEnabled;
    }

    private void bindLoadingViewHolder(RecyclerView.ViewHolder viewHolder) {
        Footer footer = (Footer) viewHolder;
        if (isFooterEnabled)
            footer.binding.footerProgress.setVisibility(View.VISIBLE);
        else
            footer.binding.footerProgress.setVisibility(View.GONE);
    }

    private static class Footer extends RecyclerView.ViewHolder {

        ItemFooterViewBinding binding;

        Footer(ItemFooterViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    private static class MovielHolder extends RecyclerView.ViewHolder {

        ItemSearchCardBinding itemSearchCardBinding;

        MovielHolder(ItemSearchCardBinding itemView) {
            super(itemView.getRoot());
            itemSearchCardBinding = itemView;
        }
    }

    private static class Header extends RecyclerView.ViewHolder {

        Header(View itemView) {
            super(itemView);
        }
    }
}
