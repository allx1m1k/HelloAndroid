package com.example.mapdemo;
import com.google.android.gms.maps.*;
        import com.google.android.gms.maps.model.*;
        import android.app.Activity;
        import android.os.Bundle;

public class MyMapsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapView)).getMap();

        LatLng sydney = new LatLng(-33.867, 151.206);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }

    /** https://developers.google.com/maps/documentation/android/start
     * package com.example.mapdemo;

     import android.app.Activity;
     import android.os.Bundle;

     public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    }
    }
     */
}
