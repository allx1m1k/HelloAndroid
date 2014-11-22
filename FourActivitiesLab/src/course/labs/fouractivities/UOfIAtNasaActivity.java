package course.labs.fouractivities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by dima on 22.11.2014.
 */
public class UOfIAtNasaActivity extends Activity {
    WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_other);
        //initialize view
        mWebView = (WebView) findViewById(R.id.webView);


        //load web content
        mWebView.loadUrl("file:///android_asset/uofi-at-nasa.html");
    }
}