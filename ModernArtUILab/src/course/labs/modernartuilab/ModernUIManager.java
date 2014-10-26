package course.labs.modernartuilab;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ModernUIManager extends Activity {

    static private final String TAG = "ModernUIManager";
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Entered onCreate");
        setContentView(R.layout.main);
        TextView tv = (TextView) findViewById(R.id.text_view);
    }


    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "Entered onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    // Process clicks on Options Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.moreInfo:
                Toast.makeText(getApplicationContext(), "User have asked for more info. Dialog have to appear!",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.userDialog:
                Toast.makeText(getApplicationContext(), "Dialog starts!",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
