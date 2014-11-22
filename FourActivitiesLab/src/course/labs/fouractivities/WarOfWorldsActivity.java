package course.labs.fouractivities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class WarOfWorldsActivity extends Activity {
    WebView mWebView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_warofworlds);
        //initialize controls
        mWebView = (WebView) findViewById(R.id.webView);

        //load web content
        mWebView.loadUrl("file:///android_asset/waroftheworlds.html");

    }
}