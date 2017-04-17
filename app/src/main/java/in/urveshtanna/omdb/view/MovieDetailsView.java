package in.urveshtanna.omdb.view;

import in.urveshtanna.omdb.entities.MovieModel;

/**
 * Adapter used to search product with pricing and navigate to product details
 *
 * @author urveshtanna
 * @version <Current-Version>
 * @see <Usage>
 * @since 1.2.0
 */

public interface MovieDetailsView extends LoadingView {

    void setPageInfo(MovieModel movieModel);

    void noSearchResultFound(MovieModel movieModel);
}
