package course.examples.fun;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import course.examples.fun.R;

public class MainActivity extends Activity {
    private final static String TAG = "ClickMyLogo";
    private ImageButton mImageButton;
    private Intent intent;
    final String mFB = "https://en.wikipedia.org/wiki/Facebook";
    final String mGgl = "https://en.wikipedia.org/wiki/Google";
    //initial selector value is relevant to facebook logo
    private int selector = 0;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate " + selector);
        setContentView(R.layout.main);
        mImageButton = (ImageButton) findViewById(R.id.imageButton);
        //setting the first image and url - is facebook
        mImageButton.setImageResource(R.drawable.flower320x480);
        intent = new Intent(Intent.ACTION_VIEW);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick " + selector);
                //there are not any logic, just starting the given intent
                //all magic: changing the logo and url is going on behind the scene in onPause and onResume methods
                startActivity(intent);
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause " + selector);
        // evaluate the selector and change its value
        switch (selector) {
            case 0:
                selector = 1;
                break;
            case 1:
                selector = 0;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume " + selector);
        // change bitmap for button based on selector value
        if (selector == 0) {
            intent.setData(Uri.parse(mFB));
            mImageButton.setImageResource(R.drawable.flower320x480);
            //startActivity(intent);
        } else {
            intent.setData(Uri.parse(mGgl));
            mImageButton.setImageResource(R.drawable.flower480x800);
            //startActivity(intent);
        }
        super.onResume();
    }
}
