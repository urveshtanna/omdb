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

/**
 *
 * @author urveshtanna
 * @version 1.0
 * @see in.urveshtanna.omdb.adapters.HomePageAdapter
 * @since 1.0
 */

public class CustomChromeTab extends AppCompatActivity {

    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;
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
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .build();
    }
}
