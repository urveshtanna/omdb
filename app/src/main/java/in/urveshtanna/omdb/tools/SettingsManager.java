package in.urveshtanna.omdb.tools;


import in.urveshtanna.omdb.BuildConfig;


/**
 * Generic class for app related settings
 *
 * @author urveshtanna
 * @version 1.0
 * @since 1.0
 */

public class SettingsManager {

    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }
}