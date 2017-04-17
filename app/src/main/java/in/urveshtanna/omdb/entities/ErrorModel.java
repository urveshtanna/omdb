package in.urveshtanna.omdb.entities;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Response;

public class ErrorModel {

    private boolean status = false;
    private String tag;
    private int statusCode = -1;
    private String statusText;
    private String errorMessage;
    private String errorType;
    private Throwable exception;

    public ErrorModel() {
    }

    public ErrorModel(Exception e) {
        this.statusCode = 1000;
        this.errorMessage = e.getMessage();
    }

    public ErrorModel(int statusCode, String errorMsg) {
        this.statusCode = statusCode;
        this.errorMessage = errorMsg;
    }

    public ErrorModel(int statusCode, String errorMessage, String tag) {
        this(statusCode, errorMessage);
        this.tag = tag;
    }

    public ErrorModel(int statusCode, String errorMessage, String tag, Throwable t) {
        this(statusCode, errorMessage, tag);
        this.exception = t;
    }

    public ErrorModel(Response response) {
        this.statusCode = response.code();
        this.errorMessage = response.message();
        this.statusText = response.message();

        try {
            String errorBodyString = response.errorBody().string();
            ResponseModel responseModel = new Gson().fromJson(new JsonParser().parse(errorBodyString), ResponseModel.class);
            if (responseModel.getError() != null) {
                this.errorMessage = responseModel.getError();
            } else {
                this.errorMessage = responseModel.getMetaData().getMessage();
            }
            this.errorType = responseModel.getErrorType();
            this.statusText = responseModel.getMetaData().getMessage();
        } catch (IOException | JsonSyntaxException | NullPointerException e) {
            this.exception = e;
        }
    }

    public ErrorModel(Response response, String tag) {
        this(response);
        this.tag = tag;
    }


    public ErrorModel(okhttp3.Response response) {
        statusCode = response.code();
        statusText = response.message();
        errorMessage = response.message();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}