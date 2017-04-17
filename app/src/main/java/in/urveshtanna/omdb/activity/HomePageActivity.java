package in.urveshtanna.omdb.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.adapters.HomePageAdapter;
import in.urveshtanna.omdb.databinding.ActivityHomePageBinding;
import in.urveshtanna.omdb.entities.MovieModel;
import in.urveshtanna.omdb.entities.SearchPayloadModel;
import in.urveshtanna.omdb.presenter.HomePresenter;
import in.urveshtanna.omdb.tools.HelperClass;
import in.urveshtanna.omdb.tools.ViewUtils;
import in.urveshtanna.omdb.view.HomePageView;

public class HomePageActivity extends AppCompatActivity implements HomePageView {

    private SearchView searchView;
    private Context mContext;
    private ActivityHomePageBinding binding;
    private BottomSheetBehavior bottomSheetBehavior;
    private int count = 0;
    private HomePresenter homePresenter;
    private boolean isFilterChanged = false;
    private int totalItems = 0;
    private int startPage = 1;
    private boolean mIsLastPage = false;
    private boolean mIsLoading = false;
    public static int PAGE_SIZE = 10;
    private boolean isFirstTime = true;
    private HomePageAdapter homePageAdapter;
    private boolean isAPIPending = true;
    private List<MovieModel> searchList;
    private String queryToFind = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindPage();//bind page
        setUpFilterBottomSheet();

        mContext = this;
        initializePager();
    }

    private void setUpFilterBottomSheet() {
        //Setup sheet
        bottomSheetBehavior = BottomSheetBehavior.from(binding.include.bottomSheet);
        binding.include.bottomSheet.setVisibility(View.GONE);

        //Set Up spinner
        List<String> list = new ArrayList<>();
        list.add("None");
        for (int i = 1900; i <= 2050; i++) {
            list.add(String.valueOf(i));
        }
        list.add("None");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.include.spYear.setAdapter(dataAdapter);

        binding.include.btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFilterChanged = true;
                bottomSheetBehavior.setPeekHeight(ViewUtils.dpToPx(mContext, 64));
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                showLoadingView();
                initializePager();
                getMovies();
            }
        });
    }

    private void bindPage() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
        setSupportActionBar(binding.include.toolbar);
        binding.include.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //initialize presenter
        homePresenter = new HomePresenter(this, HomePageActivity.this);
        homePresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        MenuItem menuSearch = menu.findItem(R.id.menu_search);
        searchView = (SearchView) menuSearch.getActionView();
        if (searchView != null) {
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setQueryHint(mContext.getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    isFilterChanged = false;
                    initializePager();
                    showLoadingView();
                    queryToFind = query;
                    binding.include.rdbAll.setChecked(true);
                    getMovies();
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(final String query) {
                    if (query.length() > 0) {
                        //Search on text change
                    } else {
                        // if text is empty
                    }

                    return true;
                }
            });

            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNavigationIcon(true);
                }
            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    setNavigationIcon(false);
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        int id = item.getItemId();
        return id == R.id.menu_search || super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (searchView != null && !searchView.isIconified()) {
            searchView.setIconified(true);
        } else if (count >= 1) {
            super.onBackPressed();
        } else {
            HelperClass.showToastBar(mContext, "Press again to exit");
            count++;
        }
    }

    private void setNavigationIcon(boolean enableBackKey) {
        if (enableBackKey) {
            binding.include.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        } else {
            binding.include.toolbar.setNavigationIcon(null);
        }
    }

    @Override
    public void showLoadingView() {
        binding.include.childInclude.recyclerView.setVisibility(View.GONE);
        binding.include.childInclude.emptyState.setVisibility(View.GONE);
        binding.include.childInclude.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        binding.include.childInclude.recyclerView.setVisibility(View.GONE);
        binding.include.childInclude.emptyState.setVisibility(View.GONE);
        binding.include.childInclude.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void noSearchResultFound(SearchPayloadModel searchPayloadModel) {
        binding.include.childInclude.recyclerView.setVisibility(View.GONE);
        binding.include.childInclude.emptyState.setVisibility(View.VISIBLE);

        if (queryToFind != null && isFilterChanged)
            binding.include.bottomSheet.setVisibility(View.VISIBLE);
        else
            binding.include.bottomSheet.setVisibility(View.GONE);

        if (searchPayloadModel != null && searchPayloadModel.getError() != null) {
            HelperClass.showMessage(mContext, searchPayloadModel.getError());
        } else {
            HelperClass.showMessage(mContext, "No results found");
        }
    }


    public String getParameters() {
        String typeOfResult = "";
        switch (binding.include.listOfResultType.getCheckedRadioButtonId()) {
            case R.id.rdb_all:
                typeOfResult = null;
                break;
            case R.id.rdb_episodes:
                typeOfResult = "episode";
                break;
            case R.id.rdb_movies:
                typeOfResult = "movie";
                break;
            case R.id.rdb_series:
                typeOfResult = "series";
                break;
        }
        return typeOfResult;

    }


    public void setUpRecyclerAdapter() {
        homePageAdapter = new HomePageAdapter(searchList, mContext);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.include.childInclude.recyclerView.setLayoutManager(layoutManager);
        binding.include.childInclude.recyclerView.setAdapter(homePageAdapter);
        binding.include.childInclude.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!mIsLoading && !mIsLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        if (!isFirstTime) {
                            if (totalItems != 0 && homePageAdapter != null) {
                                loadMoreItems();
                            }
                        }
                    }

                }
            }
        });
    }

    private void loadMoreItems() {
        int size = homePageAdapter.getItemCount() - 2;
        if (size == totalItems) {
            mIsLastPage = true;
            mIsLoading = true;
            homePageAdapter.enableFooter(false);
            homePageAdapter.notifyDataSetChanged();
        } else {
            if (isAPIPending) {
                isAPIPending = false;
                startPage += 1;
                getMovies();
            }
        }
    }

    private void getMovies() {
        homePresenter.searchForMovieWithName(queryToFind, getParameters(), binding.include.spYear.getSelectedItem().toString().equals("None") ? null : binding.include.spYear.getSelectedItem().toString(), startPage);
    }

    @Override
    public void setAdapter(List<MovieModel> searchModelList) {
        binding.include.bottomSheet.setVisibility(View.VISIBLE);
        binding.include.childInclude.recyclerView.setVisibility(View.VISIBLE);
        if (searchModelList.size() > 0) {
            searchList.addAll(searchModelList);
            if (isFirstTime) {
                binding.include.childInclude.recyclerView.getRecycledViewPool().clear();
                this.homePageAdapter.notifyItemInserted(startPage);
            } else {
                binding.include.childInclude.recyclerView.getRecycledViewPool().clear();
                this.homePageAdapter.notifyItemInserted(startPage);
            }
        }

        isFirstTime = false;
        if ((this.homePageAdapter.getItemCount() - 2) == totalItems) {
            mIsLastPage = true;
            mIsLoading = true;
            this.homePageAdapter.enableFooter(false);
            this.homePageAdapter.notifyDataSetChanged();
        } else {
            mIsLastPage = false;
            mIsLoading = false;
        }
    }


    @Override
    public void paginationData(SearchPayloadModel searchPayloadModel) {
        this.totalItems = Integer.parseInt(searchPayloadModel.getPageCount());
        isAPIPending = true;
        int size = 0;
        if (!isFirstTime) {
            size = homePageAdapter.getItemCount() - 2;
        }
        if (size == Integer.parseInt(searchPayloadModel.getPageCount())) {
            mIsLastPage = true;
            mIsLoading = true;
            homePageAdapter.enableFooter(false);
            homePageAdapter.notifyDataSetChanged();
        }
    }


    public void initializePager() {
        totalItems = 0;
        startPage = 1;
        mIsLastPage = false;
        mIsLoading = false;
        PAGE_SIZE = 10;
        isFirstTime = true;
        isAPIPending = true;
        searchList = new ArrayList<>();
        if (homePageAdapter != null) {
            homePageAdapter.notifyDataSetChanged();
        }
        setUpRecyclerAdapter();
    }
}
