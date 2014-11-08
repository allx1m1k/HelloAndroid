package course.labs.twocomputer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TwoComputerScientists extends Activity {
    private static final String TAG = "Lab-TwoComputerScientistsLab";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i(TAG, "entered onCreate");

    }

}
