package ua.org.ua2012.weather;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonMainActivity extends ListActivity {

	private static final int CODE_OK = 0;
	private static final int CODE_ERROR = 1;
	private static final String TAG = "PlacesFromJson Example";

	private static final String COORD_N = "45";
	private static final String COORD_S = "-10";
	private static final String COORD_W = "-20";
	private static final String COORD_E = "55";
	
	private ProgressDialog dialog;
	
	private GeonameList cities;
	
	private SimpleAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	// This handler will be notified when the service has responded.
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dialog.dismiss();
			if (msg.what == CODE_ERROR) {
				Toast.makeText(JsonMainActivity.this, "Service error.", Toast.LENGTH_SHORT).show();
			}
			else if (cities != null && cities.getGeonames() != null) {
				Log.i(TAG, "Cities found: " + cities.getGeonames().size());
				buildList();
			}
		}
	};
	
	private void callService() {

		// Show a loading dialog.
		dialog = ProgressDialog.show(this, "Loading", "Calling GeoNames web service...", true, false);

		// Create the thread that calls the webservice.
		Thread loader = new Thread() {
			public void run() {

				// init stuff.
				Looper.prepare();
				cities = new GeonameList();
				boolean error = false;

				// build the webservice URL from parameters.
				String wsUrl = "http://api.geonames.org/citiesJSON?lang=en&username=allx1m1k";
				wsUrl += "&north="+COORD_N;
				wsUrl += "&south="+COORD_S;
				wsUrl += "&east="+COORD_E;
				wsUrl += "&west="+COORD_W;
				
				String wsResponse = "";

				try {
					// call the service via HTTP.
					wsResponse = readStringFromUrl(wsUrl);
					
					// deserialize the JSON response to the cities objects.
					cities = new Gson().fromJson(wsResponse, GeonameList.class);
				}
				catch (IOException e) {
					// IO exception
					Log.e(TAG, e.getMessage(), e);
					error = true;
				}
				catch (IllegalStateException ise) {
					// Illegal state: maybe the service returned an empty string.
					Log.e(TAG, ise.getMessage(), ise);
					error = true;
				}
				catch (JsonSyntaxException jse) {
					// JSON syntax is wrong. This could be quite bad.
					Log.e(TAG, jse.getMessage(), jse);
					error = true;
				}
				
				if (error) {
					// error: notify the error to the handler.
					handler.sendEmptyMessage(CODE_ERROR);
				}
				else {
					// everything ok: tell the handler to show cities list.
					handler.sendEmptyMessage(CODE_OK);
				}
			}
		};

		// start the thread.
		loader.start();
	}
	
	private void buildList() {
		
		// init stuff.
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> currentChildMap = null;
		String line1;
		String line2;
        String line3; //Lat
        String line4; //Lng
	
		// cycle on the cities and create list entries.
		for (Geonames city : cities.getGeonames()) {
			currentChildMap = new HashMap<String, String>();
			data.add(currentChildMap);
			
			line1 = city.getToponymName() + " (" + city.getCountrycode() + ")";
			line2 = "Население: " + city.getPopulation();
            line3 = "Широта: " + city.getLat(); //latitude
            line4 = "Долгота: " + city.getLng(); //longitude
			
			currentChildMap.put("LABEL", line1);
			currentChildMap.put("TEXT", line2);
            currentChildMap.put("LAT", line3);
            currentChildMap.put("LNG", line4);
		}
	
		// create the list adapter from the created map.
		//dima
		//adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2,
        adapter = new SimpleAdapter(this, data, R.layout.mysimple_list_item_2,
				new String[] { "LABEL", "TEXT", "LAT", "LNG" },
                new int[] { R.id.text1, R.id.text2, R.id.text3, R.id.text4 });
				//dima
				// new int[] { android.R.id.text1, android.R.id.text2 });
	
		setListAdapter(adapter);
	}

	private String readStringFromUrl(String fileURL) throws IOException {
		
		InputStream is = null;
		BufferedInputStream bis = null;
	    ByteArrayBuffer bufH = new ByteArrayBuffer(512);
	    byte[] bufL = new byte[512];

		if (checkConnectivity()) {
			
			try {
				URL url = new URL(fileURL);

				long startTime = System.currentTimeMillis();

				Log.d(TAG, "Started download from URL " + url);
				
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				is = new BufferedInputStream(urlConnection.getInputStream());
			    
			    int read;
			    do {
			       read = is.read(bufL, 0, bufL.length);
			       if (read > 0) bufH.append(bufL, 0, read);
			    } while (read >= 0);

				Log.d(TAG, "completed download in " + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
				Log.d(TAG, "downloaded " + bufH.length() + " byte");
				
				String text = new String(bufH.toByteArray()).trim();
			    return text;
			}
			catch (SocketTimeoutException ste) {
				throw ste;
			}
			catch (IOException ioe) {
				throw ioe;
			}
			finally {
				try {
					if (bis != null) {
						bis.close();
					}
					if (is != null) {
						is.close();
					}
				}
				catch (IOException e) {	}
			}
		}
		else {
			throw new IOException("Download error: no connection");
		}
	}
	
	private boolean checkConnectivity() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;
		NetworkInfo infoMobi = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo infoWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo.State connectionMobi = NetworkInfo.State.DISCONNECTED;
		NetworkInfo.State connectionWifi = NetworkInfo.State.DISCONNECTED;
		if (infoMobi != null)
			connectionMobi = infoMobi.getState();
		if (infoWifi != null)
			connectionWifi = infoWifi.getState();
		return (connectionMobi == NetworkInfo.State.CONNECTED) || (connectionWifi == NetworkInfo.State.CONNECTED);
	}
}
