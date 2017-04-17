package in.urveshtanna.omdb.entities;

import com.google.gson.annotations.SerializedName;

import okhttp3.Response;

public class ResponseModel<S> {

    @SerializedName("payload")
    private S payload;

    @SerializedName("meta")
    private MetaData metaData;

    @SerializedName("error_type")
    private String errorType;

    private int code;
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isStatus() {
        //TODO check logic for 201 and others
        return metaData.getStatus() == 200;
    }

    public void setResponse(Response response) {
        code = response.code();
    }

    public ErrorModel getError(Response response) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setStatusCode(response.code());
        if (error != null) errorModel.setErrorMessage(error);
        if (metaData.getMessage() != null) errorModel.setErrorMessage(metaData.getMessage());
        if (errorType != null) errorModel.setErrorType(errorType);
        return errorModel;
    }

    public S getPayload() {
        return payload;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public class MetaData {
        @SerializedName("status_code")
        private int status;
        @SerializedName("message")
        private String message;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
