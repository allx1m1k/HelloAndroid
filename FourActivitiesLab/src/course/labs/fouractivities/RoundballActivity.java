package course.labs.fouractivities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by dima on 22.11.2014.
 */
public class RoundballActivity extends Activity {
    WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_other);
        //initialize view
        mWebView = (WebView) findViewById(R.id.webView);

        //set JavaScript and DOM storage enabled
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        //load web content
        mWebView.loadUrl("file:///android_asset/roundball/roundball.html");


    }
}