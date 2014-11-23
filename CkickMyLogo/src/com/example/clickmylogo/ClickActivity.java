package com.example.clickmylogo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ClickActivity extends Activity {
    private final static String TAG = "ClickMyLogo";
    ImageButton mImageButton;
    final String mFB = "https://en.wikipedia.org/wiki/Facebook";
    final String mGgl = "https://en.wikipedia.org/wiki/Google";
    int selector = 0;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mImageButton = (ImageButton) findViewById(R.id.imageButton);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "entered mLeftButton onClick");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (selector == 0) {
                    intent.setData(Uri.parse(mFB));
                    mImageButton.setImageResource(R.drawable.google);
                } else {
                    intent.setData(Uri.parse(mGgl));
                    mImageButton.setImageResource(R.drawable.facebook);
                }
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onPause() {
        // change bitmap for button
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
        super.onResume();
    }


}
