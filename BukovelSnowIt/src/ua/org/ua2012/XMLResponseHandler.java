package ua.org.ua2012;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

class XMLResponseHandler implements ResponseHandler<List<String>> {

	private static final String MOUNTAINSNOW_TAG = "schneehoehe_berg";
	private static final String VALLEYSNOW_TAG = "schneehoehe_tal";
	private static final String SNOWCONDITION_TAG = "schneequalitaet";
	private String mLat, mLng, mMag;
	private boolean mIsParsingLat, mIsParsingLng, mIsParsingMag;
	private final List<String> mResults = new ArrayList<String>();

	@Override
	public List<String> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		try {

			// Create the Pull Parser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Set the Parser's input to be the XML document in the HTTP Response
			xpp.setInput(new InputStreamReader(response.getEntity()
					.getContent()));
			
			// Get the first Parser event and start iterating over the XML document 
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {
					startTag(xpp.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					endTag(xpp.getName());
				} else if (eventType == XmlPullParser.TEXT) {
					text(xpp.getText());
				}
				eventType = xpp.next();
			}
			return mResults;
		} catch (XmlPullParserException e) {
		}
		return null;
	}

	public void startTag(String localName) {
		if (localName.equals(SNOWCONDITION_TAG)) {
			mIsParsingLat = true;
		} else if (localName.equals(VALLEYSNOW_TAG)) {
			mIsParsingLng = true;
		} else if (localName.equals(MOUNTAINSNOW_TAG)) {
			mIsParsingMag = true;
		}
	}

	public void text(String text) {
		if (mIsParsingLat) {
			mLat = text.trim();
		} else if (mIsParsingLng) {
			mLng = text.trim();
		} else if (mIsParsingMag) {
			mMag = text.trim();
		}
	}

	public void endTag(String localName) {
		if (localName.equals(SNOWCONDITION_TAG)) {
			mIsParsingLat = false;
		} else if (localName.equals(VALLEYSNOW_TAG)) {
			mIsParsingLng = false;
		} else if (localName.equals(MOUNTAINSNOW_TAG)) {
			mIsParsingMag = false;
		} else if (localName.equals("schneemeldung")) {
			mResults.add(MOUNTAINSNOW_TAG + ":" + mMag + "," + SNOWCONDITION_TAG + ":"
					+ mLat + "," + VALLEYSNOW_TAG + ":" + mLng);
			mLat = null;
			mLng = null;
			mMag = null;
		}
	}
}