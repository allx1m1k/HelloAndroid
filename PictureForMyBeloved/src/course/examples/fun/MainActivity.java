package course.examples.fun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {
    private final static String TAG = "PictureForMyBeloved";
    private ImageButton mImageButton;
    //initial selector value is relevant to R.drawable.flower320x480
    private int selector = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Entered onCreate " + selector);
        setContentView(R.layout.main);
        mImageButton = (ImageButton) findViewById(R.id.imageButton);
        //setting the first image and url - is facebook
        mImageButton.setImageResource(R.drawable.flower320x480);

        //setOnClickListener in order to display desirable image
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Entered onClick " + selector);
                //the logic here is to evaluate the flag selector
                //and based on evaluated value we have to display the image
                // selector = 0 -> R.drawable.flower320x480
                // selector = 1 -> R.drawable.flower480x800
                switch (selector) {
                    case 0:
                        selector = 1;
                        mImageButton.setImageResource(R.drawable.flower480x800);
                        break;
                    case 1:
                        selector = 0;
                        mImageButton.setImageResource(R.drawable.flower320x480);
                }
            }
        });

        //setOnLongClickListenet in order to call email Client
        mImageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG, "onLongClick " + selector);
                //we call Email client
                sendEmail();
                return true;
            }
        });

        super.onCreate(savedInstanceState);
    }

    /**
     * Called when Activity recieved onPause callback
     */
    @Override
    protected void onPause() {
        Log.i(TAG, "Entered onPause " + selector);
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

    /**
     * Called when Activity recieved onResume callback
     */
    @Override
    protected void onResume() {
        Log.i(TAG, "Entered onResume " + selector);
        // change bitmap for button based on selector value
        if (selector == 0) {
            mImageButton.setImageResource(R.drawable.flower320x480);
        } else {
            mImageButton.setImageResource(R.drawable.flower480x800);
        }
        super.onResume();
    }

    /**
     * Called when Activity recieved onBackPressed callback
     */
    @Override
    public void onBackPressed() {
        Log.i(TAG, "Entered onBackPressed");
        //the logic here is to finish Activity the 3 after sec when user pressed Back button
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Entered run");
                finish();
            }
        };
        mImageButton.postDelayed(r, 2000);
    }

    /**
     * Called when Email has to be sent
     */
    protected void sendEmail() {
        Log.i(TAG, "Send email");
        String[] TO = {"dima@ua2012.org.ua"};
        String subject = "To my Beloved";
        String body = "I love you!";
        //Create an Intent to
        final Intent result = new Intent(android.content.Intent.ACTION_SEND);
        result.setType("plain/text");
        result.putExtra(android.content.Intent.EXTRA_EMAIL, TO);
        result.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        result.putExtra(android.content.Intent.EXTRA_TEXT, body);
        try {
            Intent mailer = Intent.createChooser(result, null);
            startActivity(mailer);
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}