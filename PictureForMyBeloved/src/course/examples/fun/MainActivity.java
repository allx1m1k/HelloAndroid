package course.examples.fun;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import course.examples.fun.R;

public class MainActivity extends Activity {
    private final static String TAG = "PictureForMyBeloved";
    private ImageButton mImageButton;
    private Intent intent;
    private Intent emailIntent;
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
        //old intent = new Intent(Intent.ACTION_VIEW);
        intent = new Intent(Intent.ACTION_VIEW);


        //setOnClickListener
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick " + selector);
                //there are not any logic, just starting the given intent
                //all magic: changing the logo and url is going on behind the scene in onPause and onResume methods
                //startActivity(intent);
            }
        });

        //setOnLong
        mImageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG, "onLongClick " + selector);
                //startActivity(emailIntent);
                //Intent emailIntent = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","abc@gmail.com", null));
                //Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "mail_to_send", null));
                //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
                //startActivity(Intent.createChooser(emailIntent, "Send email..."));
                sendEmail();
                return true;
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

    protected void sendEmail() {
        Log.i(TAG, "Send email");
        String[] TO = {"dima@gmail.com"};
        String subject = "To my Beloved";
        String body = "I love you!";

        final Intent result = new Intent(android.content.Intent.ACTION_SEND);
        result.setType("plain/text");
        result.putExtra(android.content.Intent.EXTRA_EMAIL, TO);
        result.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        result.putExtra(android.content.Intent.EXTRA_TEXT, body);
        try {
            Intent mailer = Intent.createChooser(result, null);
            startActivity(mailer);
            //startActivity(Intent.createChooser(result, "Send mail..."));
            //finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
/*
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","abc@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
        emailIntent.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
            finish();
            Log.i(TAG, "Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
*/
/*
        String[] TO = {"dima@gmail.com"};
        //String[] CC = {"mcmohd@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
*/
    }

}
