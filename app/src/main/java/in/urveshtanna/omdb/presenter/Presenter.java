package in.urveshtanna.omdb.presenter;

import android.content.Context;

import com.squareup.otto.Bus;

import in.urveshtanna.omdb.entities.ErrorModel;
import in.urveshtanna.omdb.entities.RequestUrl;
import in.urveshtanna.omdb.tools.Constants;
import in.urveshtanna.omdb.tools.SettingsManager;
import in.urveshtanna.omdb.tools.Utils;


/**
 * Base presenter
 *
 * @author urveshtanna
 * @version 1.0
 * @since 1.0
 */

public abstract class Presenter<T> {

    private Context mContext;
    private T mView;
    private Bus mBus;
    private RequestUrl requestUrl;
    private boolean isRegister = false;

    public Presenter(Context context, T view, Bus bus) {
        this.mContext = context;
        this.mView = view;
        this.mBus = bus;
        requestUrl = new RequestUrl(SettingsManager.isDebuggable());
        requestUrl.setBaseUrl(Constants.BASE_URL);
    }

    public void start() {
        if (!isRegister) {
            mBus.register(this);
            isRegister = true;
        }
    }

    public void stop() {
        try {
            mView = null;
            if (isRegister) {
                mBus.unregister(this);
                isRegister = false;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Bus getBus() {
        return mBus;
    }

    public void setBus(Bus bus) {
        this.mBus = bus;
    }

    public T getView() {
        return mView;
    }

    public void setView(T view) {
        this.mView = view;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public RequestUrl getRequestUrl() {
        return requestUrl;
    }


    public void error(ErrorModel errorModel) {
        error(errorModel, true);
    }

    public void error(ErrorModel errorModel, boolean showErrorMsg) {
        if (errorModel == null) return;
        if (showErrorMsg) {
            Utils.displayErrorMsg(getContext(), errorModel);
        }
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    public void checkBus() {
        if (!isRegister()) {
            start();
        }
    }
}