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

    public String getAccessToken() {
        return "Token token=" + accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExternalLink() {
        return externalLink;
    }

    public void setExternalLink(boolean externalLink) {
        this.externalLink = externalLink;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
