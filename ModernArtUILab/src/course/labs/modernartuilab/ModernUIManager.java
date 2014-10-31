package course.labs.modernartuilab;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ModernUIManager extends Activity {

    static private final String TAG = "Lab-ModernUILab";
    private static final int ALERTTAG = 0;
    private DialogFragment mDialog;
    private SeekBar mSeekBar;
    private TextView mPinkView;
    private TextView mBlueView;
    private TextView mGreenView;
    private TextView mWhiteView;
    private TextView mYellowView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Entered onCreate");
        setContentView(R.layout.main_s);
        /*
        SeekBar sb = (SeekBar) findViewById(R.id.seek_bar);
        */
        mPinkView = (TextView) findViewById(R.id.pinkView);
        mBlueView = (TextView) findViewById(R.id.blueView);
        mGreenView = (TextView) findViewById(R.id.greenView);
        mWhiteView = (TextView) findViewById(R.id.whiteView);
        mYellowView = (TextView) findViewById(R.id.yellowView);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        //set seekBar from 0 - 255
        mSeekBar.setMax(255);
        //create an object listener
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //Implementation for methods OnSeekBarChangeListener interface for reacting on changes mSeekBar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "Entered changeColor" + progress);
                //changeViewColor(progress);
                int seek = seekBar.getProgress();
                mPinkView.setBackgroundColor(changeColor(0xFF007F, 0xFF6633, seek));
                mBlueView.setBackgroundColor(changeColor(0x0000FF, 0x6600CC, seek));
                mGreenView.setBackgroundColor(changeColor(0x00FF00, 0x00FF99, seek));
                mWhiteView.setBackgroundColor(changeColor(0xFFFFFF, 0xFFFFFF, seek));
                mYellowView.setBackgroundColor(changeColor(0xFF7F00, 0xFFCC66, seek));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

    }

    //Method for changing colors
    public int changeColor(int color1, int color2, int progress) {
        int deltaRed = Color.red(color2) - Color.red(color1);
        int deltaGreen = Color.green(color2) - Color.green(color1);
        int deltaBlue = Color.blue(color2) - Color.blue(color1);

        int red = Color.red(color1) + (deltaRed * progress / 100);
        int green = Color.green(color1) + (deltaGreen * progress / 100);
        int blue = Color.blue(color1) + (deltaBlue * progress / 100);

        return Color.rgb(red, green, blue);
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
        Log.i(TAG, "Entered onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.moreInfo:
                Toast.makeText(getApplicationContext(), "User have asked for more info. Dialog have to appear!",
                        Toast.LENGTH_SHORT).show();
                showDialogFragment(ALERTTAG);
                return true;
            default:
                return false;
        }
    }

    // Show desired Dialog
    void showDialogFragment(int dialogID) {
        Log.i(TAG, "Entered showDialogFragment");
        switch (dialogID) {
            // Show AlertDialog
            case ALERTTAG:
                // Create a new AlertDialogFragment
                mDialog = AskDialogFragment.newInstance();
                // Show AlertDialogFragment
                mDialog.show(getFragmentManager(), "Alert");
                break;
            default:
                break;
        }
    }

    // Abort or complete ShutDown based on value of shouldContinue
    private void continueWork(boolean shouldContinue) {
        Log.i(TAG, "Entered continueWork");
   if (shouldContinue) {

       //Create a new intent to launch the WebViewActivity class
       Intent explicitIntent = new Intent(this, WebViewActivity.class);
       //Start an Activity using that intent
       Log.i(TAG, "Entered continueWork show WebView");
       startActivity(explicitIntent);

       // Prevent further interaction with the ShutDown Butotn
            //mShutdownButton.setEnabled(false);

            // Show ProgressDialog as shutdown process begins
            //showDialogFragment(PROGRESSTAG);
            // Finish the ShutDown process
            //finishShutdown();

        } else {

        //Dismiss the dialog and proceed with ModernUIManager
       Log.i(TAG, "Entered continueWork DON'T show WebView");
       mDialog.dismiss();
        }
    }

    // Class that creates the AskDialog
    public static class AskDialogFragment extends DialogFragment {

        public static AskDialogFragment newInstance() {
            Log.i(TAG, "New AskDialogFragment created");
            return new AskDialogFragment();
        }
        // Build AlertDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.i(TAG, "Entered onCreateDialog");
            return new AlertDialog.Builder(getActivity())
                    .setMessage("Do you really want to exit?")

                            // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)

                            // Set up No Button
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    ((ModernUIManager) getActivity()).continueWork(false);
                                }
                            })

                            // Set up Yes Button
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    ((ModernUIManager) getActivity()).continueWork(true);
                                }
                            }).create();
        }
    }

}
