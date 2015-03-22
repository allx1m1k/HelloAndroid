package ua.org.ua2012.weather;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;

import android.app.Activity;
import android.widget.TextView;
import com.google.gson.*;
import org.apache.http.util.ByteArrayBuffer;
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


public class JsonMainActivity extends Activity {

	private static final int CODE_OK = 0;
	private static final int CODE_ERROR = 1;
	private static final String TAG = "PlacesFromJson Example";

    private static final String COORD_LAT = "48.358311";
    private static final String COORD_LNG = "24.407369";
    private static final String USRNAME = "allx1m1k";
/*
	dima depricated
    private static final String COORD_N = "45";
	private static final String COORD_S = "-10";
	private static final String COORD_W = "-20";
	private static final String COORD_E = "55";
*/
	private ProgressDialog dialog;

    //dima depricated
    private GeonameList cities;
    //dima added
    private WeatherObservations observationsJson;
    private JsonObject jsonObject;
    private JsonElement jsonElement;
    private JsonArray jsonArray;

	private SimpleAdapter adapter;
    private static TextView stationView;
    private static TextView latView;
    private static TextView lngView;
    private static TextView observationView;
    private static TextView datetimeView;
    private static TextView temperatureView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // dima
        // Initialize tha Activity UI

        setContentView(R.layout.mysimple_list_item_2);
        stationView = (TextView) findViewById(R.id.station);
        latView = (TextView) findViewById(R.id.latitude);
        lngView = (TextView) findViewById(R.id.longitude);
        observationView = (TextView) findViewById(R.id.weather);
        datetimeView = (TextView) findViewById(R.id.datetime);
        temperatureView = (TextView) findViewById(R.id.temperature);


		/*
		View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(android.R.id.station)).setText(
                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));


		 */

		// Call the web-service from geonames.org
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
				Toast.makeText(JsonMainActivity.this, "Service error.", Toast.LENGTH_LONG).show();
			}
            /* dima depricated
			else if (cities != null && cities.getGeonames() != null) {
				Log.i(TAG, "Cities found: " + cities.getGeonames().size());
				buildList();
		    */
            else if (observationsJson != null && observationsJson.getStationName() != null) {
                Log.i(TAG, "Weather found: " + observationsJson.getWeatherCondition());
                // dima depricated
                // buildList();

                // dima added
                // Build the text field for View
                myBuildList();

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
                //dima added
                // Initialize variable which is represented weather observations
                observationsJson = new WeatherObservations();
                // Initialize variable which is represented service call error status
                boolean error = false;


				/* dima depricated
                String wsUrl = "http://api.geonames.org/citiesJSON?lang=en&username=allx1m1k";
				wsUrl += "&north="+COORD_N;
				wsUrl += "&south="+COORD_S;
				wsUrl += "&east="+COORD_E;
				wsUrl += "&west="+COORD_W;
				*/

                // build the webservice URL from parameters.
                String wsUrl = "http://api.geonames.org/findNearByWeatherJSON?formatted=true";
                wsUrl += "&lat="+COORD_LAT;
                wsUrl += "&lng="+COORD_LNG;
                wsUrl += "&username="+USRNAME;
                wsUrl += "&style=full";

                // Initialize variable which is represented String which was derived from request to web service
				String wsResponse = "";

				try {
					// call the service via HTTP.
					wsResponse = readStringFromUrl(wsUrl);
                    // dima added
                    // Initialize JsonObject variable with result of request to wev service
                    jsonObject = getJsonObject(wsResponse, getApplicationContext());
                    // Initialize JsonElement variable within the particular name
                    jsonElement = jsonObject.get("weatherObservation");
                    // Initialize the target data structure which is represented by Json elements
                    observationsJson = new Gson().fromJson(jsonElement, WeatherObservations.class);

                    /* dima depricated
                    for(JsonElement obj : jArray )
                    {
                        //channelSearchEnum cse = gson.fromJson( obj , channelSearchEnum.class);
                        Type listType = new TypeToken<List<WeatherObservationList>>() {}.getType();
                        observations = new Gson().fromJson(wsResponse, listType);
                        lcs.add(observations);
                    }
                    */
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
                    //Log.i(TAG, "Долгота JsonObj" + jsonObject.get("lng"));
                    Log.i(TAG, "Долгота WeatheObservations " + observationsJson.getLat());
                    Log.i(TAG, "Дата WeatheObservations " + observationsJson.getDatetime());
                    Log.i(TAG, "Температура WeatheObservations " + observationsJson.getTemperature());
                    handler.sendEmptyMessage(CODE_OK);
				}
			}
		};

		// start the thread.
		loader.start();
	}

    private void myBuildList() {
        // init stuff. dima depricated

        Log.i(TAG, "Долгота WeatheObservations for TextView is :" + observationsJson.getLat());
        Log.i(TAG, "Станция WeatheObservations for TextView is :" + observationsJson.getStationName());
        Log.i(TAG, "Дата WeatheObservations for TextView is " + observationsJson.getDatetime());
        Log.i(TAG, "Температура WeatheObservations for TextView is " + observationsJson.getTemperature());

        // dima added
        // populate TextView with derived data
        stationView.setText(getString(R.string.station_name) + observationsJson.getStationName());
        latView.setText(getString(R.string.latitude_val) + observationsJson.getLat().toString());
        lngView.setText(getString(R.string.longitude_val) + observationsJson.getLng().toString());
        observationView.setText(getString(R.string.weather_data) + observationsJson.getObservation());
        datetimeView.setText(getString(R.string.datetime_data) + observationsJson.getDatetime());
        temperatureView.setText(getString(R.string.temperature_data) + observationsJson.getTemperature());

        //dima depricated
        // setListAdapter(adapter);
    }
	
	private void buildList() {
		
		// init stuff.
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> currentChildMap = null;
		String line1; //weather station
		String line2; //
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
                new int[] { R.id.station, R.id.latitude, R.id.longitude, R.id.weather});
				//dima
				// new int[] { android.R.id.station, android.R.id.text2 });

        //setListAdapter(adapter);
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

    public static JsonElement getJsonElement(String response, Context context) {
        JsonParser parser = new JsonParser();
        JsonElement element = null;

        try {
            element = parser.parse(response);
        } catch (JsonParseException e) {
            e.printStackTrace();
            //showErrorNetworkMessage(context);
            element = null;
        }

        return element;
    }


    public static JsonObject getJsonObject(String response, Context context) {
        if (response == null || response.length() <= 0) {
            return null;
        }

        JsonElement element = getJsonElement(response, context);
        JsonObject obj = null;

        if (element != null && element.isJsonObject()) {
            try {
                obj = element.getAsJsonObject();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                obj = null;
            }
        } else {
            Log.i(TAG, "JsonElement is not JsonObject");
        }

        return obj;
    }


}
