package ua.org.ua2012.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by dima on 1/13/2015.
 */
public class BukovelMapActivity extends Activity {
    private static String TAG = "WeatherFromJson";
    ImageView mImageButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate BukovelMapActivity" );
        setContentView(R.layout.bukovel_map);
        mImageButton = (ImageView) findViewById(R.id.bukovel_big_map);
        //setting the first image and url - is facebook
        //mImageButton.setImageResource(R.drawable.bukovel);
        //mImageButton.setBackground();
        //intent = new Intent(Intent.ACTION_VIEW);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick BukovelMapActivity" );
                //there are not any logic, just starting the given intent
                //all magic: changing the logo and url is going on behind the scene in onPause and onResume methods
                //startActivity(intent);
            }
        });
        super.onCreate(savedInstanceState);
    }
}