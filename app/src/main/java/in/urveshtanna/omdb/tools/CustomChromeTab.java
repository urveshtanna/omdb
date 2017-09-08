package in.urveshtanna.omdb.tools;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import in.urveshtanna.omdb.R;
import in.urveshtanna.omdb.screens.home.list.HomePageAdapter;

/**
 * @author urveshtanna
 * @version 1.0
 * @see HomePageAdapter
 * @since 1.0
 */

public class CustomChromeTab extends AppCompatActivity {

    private final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession mCustomTabsSession;
    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsIntent mCustomTabsIntent;
    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpChromeTabs();
        try {
            if (getIntent().getExtras().getString("url") != null) {
                url = getIntent().getExtras().getString("url");
            }
            if (url != null) {
                mCustomTabsIntent.launchUrl(this, Uri.parse(url));
                finish();
            } else {
                finish();
            }
        } catch (Exception e) {
            finish();
        }
    }

    private void setUpChromeTabs() {
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient = customTabsClient;
                mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCustomTabsClient = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(false)
                .setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.icons))
                .setStartAnimations(this, R.anim.slide_from_right, R.anim.exit_to_left)
                .setExitAnimations(this, R.anim.slide_from_left, R.anim.exit_to_right)
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .build();
    }
}
