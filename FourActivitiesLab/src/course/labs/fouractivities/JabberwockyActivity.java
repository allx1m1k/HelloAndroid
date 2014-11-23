package course.labs.fouractivities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.io.BufferedReader;

public class JabberwockyActivity extends Activity {
    private final static String TAG = "FourActivitiesLab";
    private MediaPlayer mMediaPlayer;
    /**
     * Called when activity is visible
     */
    @Override
    protected void onResume() {
        Log.i(TAG, "Entered onResume");
        mMediaPlayer = MediaPlayer.create(this, R.raw.weareone);
        mMediaPlayer.start();
        super.onResume();
    }

    /**
     * Called when activity become invisible
     */
    @Override
    protected void onPause() {
        Log.i(TAG, "Entered onPause");
        mMediaPlayer.stop();
        mMediaPlayer.release();
        super.onPause();
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Log.i(TAG, "Entered onCreate");
        setContentView(R.layout.main);
        //initialize controls
        final WebView mWebView = (WebView) findViewById(R.id.webView);
        final Button mLeftButton = (Button) findViewById(R.id.buttonLeft);
        final Button mRightButton = (Button) findViewById(R.id.buttonRight);

        //load web content
        mWebView.loadUrl("file:///android_asset/jabberwocky.html");

        //listener for left button - will open the Wikipedia
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "entered mLeftButton onClick");
                //open Wikipedia in external browser http://stackoverflow.com/questions/3004515/android-sending-an-intent-to-browser-to-open-specific-url
                String url = "http://en.wikipedia.org/wiki/Jabberwocky";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });

        //listener for right button - will open the
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open picture with beast
                Log.i(TAG, "entered mRightButton onClick");
                mWebView.loadUrl("file:///android_asset/angraves-highscore6x6.png");
            }
        });
    }
}
