package in.urveshtanna.omdb.entities;

import java.util.HashMap;

public class RequestUrl {

    private String accessToken;
    private boolean isDebug;
    private String urlPath;
    private String title;
    private boolean externalLink;
    private String baseUrl;

    public RequestUrl(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public RequestUrl(String urlPath) {
        this.urlPath = urlPath;
    }

    public RequestUrl(boolean isDebug, String accessToken, HashMap<String, String> additionParams) {
        this.isDebug = isDebug;
        this.accessToken = accessToken;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
