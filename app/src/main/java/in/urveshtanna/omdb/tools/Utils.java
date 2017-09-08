package in.urveshtanna.omdb.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.models.ErrorModel;
import io.reactivex.Observable;

/**
 * Useful/Helper methods
 *
 * @author urveshtanna
 * @version 1.0
 * @since 1.0
 */

public class Utils {

    public static void displayErrorMsg(Context context, ErrorModel responseModel) {
        switch (responseModel.getStatusCode()) {
            case -1://No Internet
                HelperClass.showToastBar(context, context.getString(R.string.internet_connection_error_message));
                break;
            case 0:
                HelperClass.showToastBar(context, context.getString(R.string.internet_connection_error_message));
                break;
            case 200:// status "OK"
                break;
            case 201:// status "Created"
                break;
            case 300:// status "Multiple Choices"
                break;
            case 301:// status "Moved Permanently"
                break;
            case 400:// status "Bad Request"
                HelperClass.showToastBar(context, context.getString(R.string.something_went_wrong_message));
                break;
            case 401:// status "Unauthorized"
                HelperClass.showToastBar(context, context.getString(R.string.something_went_wrong_message));
                break;
            case 403:
                HelperClass.showToastBar(context, context.getString(R.string.something_went_wrong_contact_us));
                break;
            case 404:// status "Not Found"
                HelperClass.showToastBar(context, context.getString(R.string.something_went_wrong_message));
                break;
            case 405://status "Method Not Allowed"
                HelperClass.showToastBar(context, context.getString(R.string.something_went_wrong_message));
                break;
            case 500:// status "Internal Server Error"
                HelperClass.showToastBar(context, context.getString(R.string.something_went_wrong_message));
                break;
            case 502:// status "Bad Gateway"
                HelperClass.showToastBar(context, context.getString(R.string.something_went_wrong_message));
                break;
            case 503:// status "Service Unavailable"
                HelperClass.showToastBar(context, context.getString(R.string.error_message_something_went_wrong_retry));
                break;
            case 504:// status "Gateway Timeout" || "Socket TimeOut"
                HelperClass.showToastBar(context, context.getString(R.string.internet_connection_error_message));
                break;
            default:
                HelperClass.showToastBar(context, context.getString(R.string.something_went_wrong_message));
                break;
        }

    }

    public static boolean isInternetConnected(Context context, boolean showMsg) {
        boolean networkConnected = isNetworkAvailable(context);
        if (!networkConnected && showMsg)
            displayErrorMsg(context, new ErrorModel(-1, context.getResources().getString(R.string.internet_connection_error_message)));
        return networkConnected;
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }


    public static Observable<Boolean> isNetworkAvailableObservable(Context context) {
        return Observable.just(isNetworkAvailable(context));
    }


    public static int getResultTypeColor(Context context, String typeOfResult) {
        switch (typeOfResult.toLowerCase()) {
            case "movie":
                return ContextCompat.getColor(context, R.color.md_red_500);
            case "game":
                return ContextCompat.getColor(context, R.color.md_teal_500);
            case "series":
                return ContextCompat.getColor(context, R.color.md_blue_grey_500);
            case "episode":
                return ContextCompat.getColor(context, R.color.md_brown_500);
            default:
                return ContextCompat.getColor(context, R.color.md_blue_500);
        }
    }
}
