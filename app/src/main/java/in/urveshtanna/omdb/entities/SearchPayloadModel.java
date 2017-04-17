package in.urveshtanna.omdb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used to search product with pricing and navigate to product details
 *
 * @author urveshtanna
 * @version <Current-Version>
 * @see <Usage>
 * @since 1.2.0
 */

public class SearchPayloadModel {

    @SerializedName("Search")
    private ArrayList<MovieModel> searchModelList = new ArrayList<>();

    @SerializedName("Response")
    private String response;

    @SerializedName("totalResults")
    private String pageCount;

    @SerializedName("Error")
    private String error;

    public List<MovieModel> getSearchModelList() {
        return searchModelList;
    }

    public void setSearchModelList(ArrayList<MovieModel> searchModelList) {
        this.searchModelList = searchModelList;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
