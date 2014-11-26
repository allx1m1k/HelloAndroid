package course.labs.locationlab;

import android.app.ListActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceViewActivity extends ListActivity implements LocationListener {
	private static final long FIVE_MINS = 5 * 60 * 1000;
	private static final String TAG = "Lab-Location";

	// Set to false if you don't have network access
	public static boolean sHasNetwork = false;

	private Location mLastLocationReading;
	private PlaceViewAdapter mAdapter;
	private LocationManager mLocationManager;
	private boolean mMockLocationOn = false;

	// default minimum time between new readings
	private long mMinTime = 5000;

	// default minimum distance between old and new readings.
	private float mMinDistance = 1000.0f;

	// A fake location provider used for testing
	private MockLocationProvider mMockLocationProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set up the app's user interface. This class is a ListActivity, 
		// so it has its own ListView. ListView's adapter should be a PlaceViewAdapter

		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ListView placesListView = getListView();

		// DONE - add a footerView to the ListView
		// You can use footer_view.xml to define the footer
		//View footerView = null;

        // Put divider between ToDoItems and FooterView
        getListView().setFooterDividersEnabled(true);

        // DONE - Inflate footerView for footer_view.xml file
        LayoutInflater inflater = LayoutInflater.from(PlaceViewActivity.this);
        View footerView = (TextView)inflater.inflate(R.layout.footer_view, null);

        // Create a new PlaceViewAdapter for this ListActivity's ListView
        placesListView.addFooterView(footerView);
        mAdapter = new PlaceViewAdapter(getApplicationContext());
        setListAdapter(mAdapter);

		// TODO - footerView must respond to user clicks, handling 3 cases:

		// There is no current location - response is up to you. One good 
		// solution is to disable the footerView until you have acquired a
		// location.

		// There is a current location, but the user has already acquired a
		// PlaceBadge for this location. In this case issue a Toast message with the text -
		// "You already have this location badge." 
		// Use the PlaceRecord class' intersects() method to determine whether 
		// a PlaceBadge already exists for a given location.

		// There is a current location for which the user does not already have
		// a PlaceBadge. In this case download the information needed to make a new
		// PlaceBadge.

		footerView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i(TAG, "Entered footerView.OnClickListener.onClick()");

                // Determine whether initial reading is
                // "good enough". If not, register for
                // further location updates

                if (null == mLastLocationReading
                        || mLastLocationReading.getAccuracy() > mMinTime
                        || mLastLocationReading.getTime() < System.currentTimeMillis()
                        - mMinTime) {

                    return;
                    // Register for network location updates
                    /*
                    if (null != mLocationManager
                            .getProvider(LocationManager.NETWORK_PROVIDER)) {
                        mLocationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER, FIVE_MINS,
                                mMinDistance, mLocationListener);
                    }
                    */
                }

                if (mAdapter.intersects(mLastLocationReading)) {
                    Log.i(TAG, "You already have this location badge.");
                    Toast.makeText(getApplicationContext(), "AAAA!", Toast.LENGTH_LONG);
                    return;

                }


                //download from web service
                new PlaceDownloaderTask(PlaceViewActivity.this, sHasNetwork).execute(mLastLocationReading);

                //
                /*
                if (mLastLocationReading != null) {

                    //download information and than execute() see 11:00
                    new PlaceDownloaderTask(PlaceViewActivity.this, sHasNetwork) {
                        protected void onPostExecute(PlaceRecord result) {
                            if (null != result && null != mParent.get()) {
                                mParent.get().addNewPlace(result);
                            }
                        }
                    };

                }
				*/
				
				
				
				
				
				
			}

		});

    }

	@Override
	protected void onResume() {
		super.onResume();

		startMockLocationManager();

		// DONE - Check NETWORK_PROVIDER for an existing location reading.
		// Only keep this last reading if it is fresh - less than 5 minutes old
		//mLastLocationReading = null;

        // My implementation below: https://class.coursera.org/android-002/forum/thread?thread_id=2947
        mLastLocationReading = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        if (mLastLocationReading != null && ageInMilliseconds(mLastLocationReading) > FIVE_MINS) {
            mLastLocationReading = null;
        }

        if (mLocationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
            Toast.makeText(getApplicationContext(), "On resume called: Provider is not null!", Toast.LENGTH_SHORT).show();

        }

        // DONE - register to receive location updates from NETWORK_PROVIDER

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, mMinTime, mMinDistance, this);
		
		
	}

	@Override
	protected void onPause() {

		// DONE - unregister for location updates
        mLocationManager.removeUpdates(this);


		
		
		shutdownMockLocationManager();
		super.onPause();
	}

	// Callback method used by PlaceDownloaderTask
	public void addNewPlace(PlaceRecord place) {
		Log.i(TAG, "Entered addNewPlace()");

		// TODO - Attempt to add place to the adapter, considering the following cases

		// A PlaceBadge for this location already exists. In this case issue a Toast message
		// with the text - "You already have this location badge." Use the PlaceRecord 
		// class' intersects() method to determine whether a PlaceBadge already exists
		// for a given location. Do not add the PlaceBadge to the adapter
		
		// The place is null. In this case issue a Toast message with the text
		// "PlaceBadge could not be acquired"
		// Do not add the PlaceBadge to the adapter
		
		// The place has no country name. In this case issue a Toast message
		// with the text - "There is no country at this location". 
		// Do not add the PlaceBadge to the adapter
		
		// Otherwise - add the PlaceBadge to the adapter

        mAdapter.add(place);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	// LocationListener methods
	@Override
	public void onLocationChanged(Location currentLocation) {

		// DONE - Update last location considering the following cases.
		// 1) If there is no last location, set the last location to the current
		// location.
		// 2) If the current location is older than the last location, ignore
		// the current location
		// 3) If the current location is newer than the last locations, keep the
		// current location.

        // My implementation: https://class.coursera.org/android-002/forum/thread?thread_id=2947
        // case 1 & 3:
        if (mLastLocationReading == null || ageInMilliseconds(currentLocation) < ageInMilliseconds(mLastLocationReading)) {
            mLastLocationReading = currentLocation;
            // case 2:
        } else if(currentLocation.getTime() < mLastLocationReading.getTime()) {
            // do nothing, ignore the current location
            Log.i(TAG, "currentlocation is newer than the last loactoin");
        }

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

	// Returns age of location in milliseconds
	private long ageInMilliseconds(Location location) {
		return System.currentTimeMillis() - location.getTime();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_badges:
			mAdapter.removeAllViews();
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
