package in.urveshtanna.omdb.rest;

import com.squareup.otto.Bus;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import in.urveshtanna.omdb.entities.ErrorModel;
import in.urveshtanna.omdb.entities.RequestUrl;
import in.urveshtanna.omdb.tools.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Base Rest call class
 *
 * @author urveshtanna
 * @version 1.0
 * @since 1.0
 */

public abstract class RestCall {

    public static final int DEFAULT_ERROR_CODE = 999;
    private static final int TIMEOUT_TIME = 25;

    public <S> S createService(String baseUrl, Class<S> serviceClass, RequestUrl requestUrl) {
        OkHttpClient client = getClient(requestUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(requestUrl.getBaseUrl())
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }

    private OkHttpClient getClient(RequestUrl requestUrl) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (requestUrl.isDebug()) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

        } else {

            OkHttpClient.Builder client =  new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS);

            return client.build();
        }
    }

    protected void onError(Throwable t, String tag, Bus bus) {
        if (t instanceof UnknownHostException) {
            bus.post(new ErrorModel(0, Constants.NO_INTERNET_CONNECTION_MSG, tag));
        } else if (t instanceof ConnectException) {
            bus.post(new ErrorModel(504, Constants.PROBLEM_CONNECTING_MSG, tag));
        } else if (t instanceof TimeoutException) {
            bus.post(new ErrorModel(504, Constants.TIMEOUT_CONNECTION_MSG, tag));
        } else {
            bus.post(new ErrorModel(0, Constants.COMMON_ERROR_MSG, tag, t));
        }
    }
}
