package in.urveshtanna.omdb.view;

import java.util.List;

import in.urveshtanna.omdb.entities.MovieModel;
import in.urveshtanna.omdb.entities.SearchPayloadModel;

/**
 *
 * @author urveshtanna
 * @version <Current-Version>
 * @see <Usage>
 * @since 1.0
 */

public interface HomePageView extends LoadingView{

    void noSearchResultFound(SearchPayloadModel searchPayloadModel);

    void setAdapter(List<MovieModel> searchModelList);

    void paginationData(SearchPayloadModel searchPayloadModel);
}
