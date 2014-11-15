package course.labs.dontcallme;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class DontCallMeActivity extends Activity {
    private final String URL = "http://developer.android.com/guide/topics/ui/controls/button.html";
    private final String TAG = "DontCallMe-Lab";
    private MediaPlayer mPlayer;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Entering onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button mStart = (Button) findViewById(R.id.rStart);
        final Button mStop = (Button) findViewById(R.id.rStop);

        // Set an OnClickListener on start Button
        // Called each time the user clicks the Button
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start playing
                if(mPlayer.isPlaying()) {
                    mPlayer.pause();
                    mPlayer.seekTo(0);
                }
                mPlayer.start();
            }
        });

        mStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mPlayer.isPlaying()) {
                    mPlayer.pause();
                    mPlayer.seekTo(0);
                }
                mPlayer.pause();
            }
        });

    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Entering onResume");
        mPlayer = MediaPlayer.create(this, R.raw.fifa10official);
        mPlayer.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "Entering onPause");
        if(mPlayer.isPlaying()) {
            mPlayer.pause();
            mPlayer.seekTo(0);
        }
        //mPlayer.pause();
        mPlayer.stop();
        mPlayer.release();
        super.onPause();
    }

    // Once you have this working, change the string to another web page
        public void openWebPage(View v) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(URL));
            startActivity(i);
        }
}



