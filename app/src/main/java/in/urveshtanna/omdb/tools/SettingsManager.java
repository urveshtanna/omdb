package in.urveshtanna.omdb.tools;


import in.urveshtanna.omdb.BuildConfig;

public class SettingsManager {

    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }
}