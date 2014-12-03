package course.labs.contentproviderlab;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import course.labs.contentproviderlab.provider.PlaceBadgesContract;

import java.security.acl.Group;

public class PlaceViewActivity extends ListActivity implements
		LocationListener, LoaderCallbacks<Cursor> {
	private static final long FIVE_MINS = 5 * 60 * 1000;

	private static String TAG = "Lab-ContentProvider";
    //public static LayoutInflater  inflater;
    //public static MenuInflater inflater;

    // False if you don't have network access
	public static boolean sHasNetwork = false;

	private boolean mMockLocationOn = false;

	// The last valid location reading
	private Location mLastLocationReading;

	// The ListView's adapter
	// private PlaceViewAdapter mAdapter;
	private PlaceViewAdapter mCursorAdapter;

	// default minimum time between new location readings
	private long mMinTime = 5000;
    //footerView


	// default minimum distance between old and new readings.
	private float mMinDistance = 1000.0f;

	// Reference to the LocationManager
	private LocationManager mLocationManager;

	// A fake location provider used for testing
	private MockLocationProvider mMockLocationProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(getApplicationContext(),
					"External Storage is not available.", Toast.LENGTH_LONG)
					.show();
			finish();
		}

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LayoutInflater inflater = getLayoutInflater();
        View mPlaceView = inflater.inflate(R.layout.footer_view, null);
        getListView().addFooterView(mPlaceView);
        TextView footerView = (TextView) mPlaceView.findViewById(R.id.footer);
        getListView().addFooterView(footerView);
		
		// Can be removed after implementing the DONE above
		//if (null == footerView ) {
        //	return;
		//}



		// DONE - footerView must respond to user clicks, handling 3 cases:

		// There is no current location - response is up to you. The best
		// solution is to always disable the footerView until you have a
		// location.

		// There is a current location, but the user has already acquired a
		// PlaceBadge for this location - issue a Toast message with the text -
		// "You already have this location badge." 
		// Use the PlaceRecord class' intersects() method to determine whether 
		// a PlaceBadge already exists for a given location

		// There is a current location for which the user does not already have
		// a PlaceBadge. In this case download the information needed to make a new
		// PlaceBadge.

		footerView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(TAG, "Entered footerView.OnClickListener.onClick()");

                if (mLastLocationReading != null) {
                    if (mCursorAdapter.intersects(mLastLocationReading)) {
                        Toast.makeText(getApplicationContext(), "Locations intersects!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "new PlaceDownloaderTask started!", Toast.LENGTH_SHORT).show();
                        new PlaceDownloaderTask(PlaceViewActivity.this, false).execute(mLastLocationReading);
                    }
                } else {
                    Log.i(TAG, "Location not reachable");
                    Toast.makeText(getApplicationContext(), "There is no current location!", Toast.LENGTH_SHORT).show();
                }
            }
		});

        //placesListView.addFooterView(footerView);
		// DONE - Create and set empty PlaceViewAdapter
		//mCursorAdapter = new PlaceViewAdapter(getApplicationContext());
        //mCursorAdapter = new PlaceViewAdapter(getApplicationContext(), new C, 1);
        //https://class.coursera.org/android-002/forum/thread?thread_id=293
        //also see https://class.coursera.org/android-002/forum/thread?thread_id=293&sort=newest !!!
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(PlaceBadgesContract.CONTENT_URI, null, null, null, null);
        mCursorAdapter = new PlaceViewAdapter(getApplicationContext(), cursor, 0);
       // mCursorAdapter = new PlaceViewAdapter(getApplicationContext(), null, mCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);


		// DONE - Initialize the loader
        //https://class.coursera.org/android-002/forum/thread?thread_id=293
        //  getLoaderManager().initLoader(0, null, this);
        getLoaderManager().initLoader(0, null, PlaceViewActivity.this);
        setListAdapter(mCursorAdapter);

        //

	}

	@Override
	protected void onResume() {
		super.onResume();

		startMockLocationManager();

		// TODO - Check NETWORK_PROVIDER for an existing location reading.
		// Only keep this last reading if it is fresh - less than 5 minutes old

		
		
		
		
		
		
		// TODO - register to receive location updates from NETWORK_PROVIDER


		
		
	}

	@Override
	protected void onPause() {

		// TODO - unregister for location updates

		
		
		shutdownMockLocationManager();
		super.onPause();

	}

	public void addNewPlace(PlaceRecord place) {

		// TODO - Attempt to add place to the adapter, considering the following cases

		// A PlaceBadge for this location already exists - issue a Toast message
		// with the text - "You already have this location badge." Use the PlaceRecord 
		// class' intersects() method to determine whether a PlaceBadge already exists
		// for a given location. Do not add the PlaceBadge to the adapter
		
		// The place is null - issue a Toast message with the text
		// "PlaceBadge could not be acquired"
		// Do not add the PlaceBadge to the adapter
		
		// The place has no country name - issue a Toast message
		// with the text - "There is no country at this location". 
		// Do not add the PlaceBadge to the adapter
		
		// Otherwise - add the PlaceBadge to the adapter


        // todo - Attempt to add place to the adapter

        if (""==place.getCountryName()) {

        }
        else if (mCursorAdapter.intersects(mLastLocationReading)){

        }
        else mCursorAdapter.add(place);
        //else mContext.getContentResolver().insert(PlaceBadgesContract.CONTENT_URI, values);
    }
		
		
		
		


	// LocationListener methods
	@Override
	public void onLocationChanged(Location currentLocation) {

		// TODO - Update location considering the following cases.
		// 1) If there is no last location, set the last location to the current
		// location.
		// 2) If the current location is older than the last location, ignore
		// the current location
		// 3) If the current location is newer than the last locations, keep the
		// current location.


		
		
		
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// not implemented
	}

	@Override
	public void onProviderEnabled(String provider) {
		// not implemented
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// not implemented
	}

	
	// LoaderCallback methods
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

		
		// DONE - Create a new CursorLoader and return it
        //https://class.coursera.org/android-002/forum/thread?thread_id=293
        return new CursorLoader(getApplicationContext(),
                PlaceBadgesContract.CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> newLoader, Cursor newCursor) {

		
		// TODO - Swap in the newCursor

	
	
	}

	@Override
	public void onLoaderReset(Loader<Cursor> newLoader) {

		
		// TODO - swap in a null Cursor

	
	
	}

	// Returns age of location in milliseconds
	private long ageInMilliseconds(Location location) {
		return System.currentTimeMillis() - location.getTime();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Dima super.onCreateOptionsMenu(menu);
        Log.i(TAG, "Entered onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_badges:
			mCursorAdapter.removeAllViews();
			return true;
		case R.id.place_one:
			mMockLocationProvider.pushLocation(37.422, -122.084);
			return true;
		case R.id.place_no_country:
			mMockLocationProvider.pushLocation(0, 0);
			return true;
		case R.id.place_two:
			mMockLocationProvider.pushLocation(38.996667, -76.9275);
			return true;
		default:
			//return false;
			return super.onOptionsItemSelected(item);
		}
	}

	private void shutdownMockLocationManager() {
		if (mMockLocationOn) {
			mMockLocationProvider.shutdown();
		}
	}

	private void startMockLocationManager() {
		if (!mMockLocationOn) {
			mMockLocationProvider = new MockLocationProvider(
					LocationManager.NETWORK_PROVIDER, this);
		}
	}
}
