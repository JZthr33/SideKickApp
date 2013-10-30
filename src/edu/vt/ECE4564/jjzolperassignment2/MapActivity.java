package edu.vt.ECE4564.jjzolperassignment2;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity {
	private static Button GetPlaces_;
	private static Button updateLocation_;

	static TextView resultingPlaces_;
	static TextView helpView_;
	private static LocationManager locationManager = null;
	private static LocationListener locationListener = null;
	static ProgressBar pb = null;
	private static Boolean gpsexists = false;
	String location = "";
	String radius = "";
	String type = "";
	String radiusfilter = "";
	String typefilter = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// Get personal settings
		Bundle extras = getIntent().getExtras();
		radiusfilter = extras.getString("RadiusFilter");
		typefilter = extras.getString("TypeFilter");

		GetPlaces_ = (Button) findViewById(R.id.getPlacesBtn);
		updateLocation_ = (Button) findViewById(R.id.updateLocationBtn);
		resultingPlaces_ = (TextView) findViewById(R.id.resultingPlaces);
		helpView_ = (TextView) findViewById(R.id.helpView);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		pb = (ProgressBar) findViewById(R.id.getPlacesProgressBar);
		pb.setVisibility(View.INVISIBLE);

		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		updateLocation_.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				gpsexists = displayGPSStatus();
				if (gpsexists) {

					pb.setVisibility(View.VISIBLE);
					locationListener = new MyLocationListener();

					// Register the listener with the Location Manager to
					// receive location updates
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 5000, 10,
							locationListener);

				} else {

					// GPS is OFF.
					Toast.makeText(getBaseContext(),
							"Your GPS is OFF, please turn it ON.",
							Toast.LENGTH_SHORT).show();
					
				}

			}
		});

		doConcurrencyWithAsyncTask();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	public void doConcurrencyWithAsyncTask() {

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				pb.setVisibility(View.VISIBLE);

				final NetworkTask myTask = new NetworkTask();
				radius = radiusfilter;
				type = typefilter;
				myTask.execute(location, radius, type);

			}

		};

		GetPlaces_.setOnClickListener(listener);

	}

	// Check if GPS is enabled or disabled
	private Boolean displayGPSStatus() {
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(
				contentResolver, LocationManager.GPS_PROVIDER);
		if (gpsStatus) {
			return true;
		} else {
			return false;
		}
	}

	// Define a listener that responds to location updates.
	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location loc) {
			pb.setVisibility(View.INVISIBLE);
			Toast.makeText(getBaseContext(),
					"You may now proceed, your location has been acquired.",
					Toast.LENGTH_SHORT).show();
			String longitude = "Longitude: " + loc.getLongitude();
			String latitude = "Latitude: " + loc.getLatitude();

			// Instead of printing out the latitude and longitude we pass this
			// off to the
			// network task by setting the global "location"
			String newlongitude = " " + loc.getLongitude();
			String newlatitude = " " + loc.getLatitude();
			String locationResult = "";
			String latitude2 = "";
			String longitude2 = "";
			latitude2 = newlatitude.replace(" ", "");
			longitude2 = newlongitude.replace(" ", "");
			locationResult += latitude2.replaceAll("\n", "") + ","
					+ longitude2.replaceAll("\n", "");
			location = locationResult;

		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	}

}
