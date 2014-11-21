package apps101.javabook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Find that webView1
		WebView pointer = (WebView) findViewById(R.id.webView1);
        //enable zoom controls and JavaScript
        pointer.getSettings().setBuiltInZoomControls(true);
        pointer.getSettings().setJavaScriptEnabled(true);

		// Open asset/index.html
		pointer.loadUrl("file:///android_asset/index.html");

        //my simple html page
        //pointer.loadUrl("file:///android_asset/myIndex.html");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
