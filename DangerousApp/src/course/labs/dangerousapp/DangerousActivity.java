package course.labs.dangerousapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DangerousActivity extends Activity {

    private static final String TAG = "Lab-Permissions";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i(TAG, "Entered DangerousActivity");
	}

}
