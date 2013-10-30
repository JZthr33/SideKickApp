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
import android.util.Log;
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
	private static Boolean flag = false;
	String location = "";
	String radius = "";
	String type = "";	
	String radiusfilter = "";
	String typefilter = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		Log.i("!", "On Create Map Activity");
		
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
				
				flag = displayGpsStatus();
				if (flag) {
 
					//MapActivity.helpView_.setText("Please move your device to"
							//+ " see the changes in coordinates." + "\n Please Wait..");

					pb.setVisibility(View.VISIBLE);
					locationListener = new MyLocationListener();

					// Register the listener with the Location Manager to receive location updates
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

				} else {

					alertbox("GPS Status", "Your GPS is: OFF");

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
				
				Log.i("!", "Radius Filter:" + radiusfilter);
				Log.i("!", "Type Filter:" + typefilter);
				
				pb.setVisibility(View.VISIBLE);

				final NetworkTask myTask = new NetworkTask();
				radius = radiusfilter;
				type = typefilter;
				myTask.execute(location, radius, type);

			}

		};

		GetPlaces_.setOnClickListener(listener);

	}
	
	/*----Method to Check GPS is enabled or disabled ----- */
	private Boolean displayGpsStatus() {
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
		if (gpsStatus) {
			return true;
		} else {
			return false;
		}
	}
	
	/*----------Method to create an AlertBox ------------- */
	protected void alertbox(String title, String mymessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your Device's GPS is Disabled")
				.setCancelable(false)
				.setTitle("** GPS Status **")
				.setPositiveButton("GPS On",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// finish the current activity
								// AlertBoxAdvance.this.finish();
								Intent myIntent = new Intent(
										Settings.ACTION_SECURITY_SETTINGS);
								startActivity(myIntent);
								dialog.cancel();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// cancel the dialog box
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/*---------- Listener class to get coordinates ------------- */
	// Define a listener that responds to location updates
	private class MyLocationListener implements LocationListener {
		
		@Override
		public void onLocationChanged(Location loc) {
			//MapActivity.helpView_.setText("");
			pb.setVisibility(View.INVISIBLE);
			Toast.makeText(getBaseContext(), "You may now proceed, your location has been acquired.", Toast.LENGTH_SHORT).show();
			String longitude = "Longitude: " + loc.getLongitude();
			Log.v("!", longitude);
			String latitude = "Latitude: " + loc.getLatitude();
			Log.v("!", latitude);
			
			// Instead of printing out the latitude and longitude we pass this off to the
			// network task by setting the global "location"
			String newlongitude = " " + loc.getLongitude();
			String newlatitude = " " + loc.getLatitude();
			String locationResult = "";
			String latitude2 = "";
			String longitude2 = "";
			latitude2 = newlatitude.replace(" ", "");
			longitude2 = newlongitude.replace(" ", "");
			locationResult += latitude2.replaceAll("\n", "") + "," + longitude2.replaceAll("\n", "");
			location = locationResult;

		}
		
		@Override
		public void onProviderDisabled(String provider) {}

		@Override
		public void onProviderEnabled(String provider) {}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
		
	}
	
	/*
	public class TemperatureSensorActivity extends Activity implements SensorEventListener {
		 private final SensorManager mSensorManager;
		 private final Sensor mTemp;

		 public TemperatureSensorActivity() {
		     mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		     mTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
		 }

		 protected void onResume() {
		     super.onResume();
		     mSensorManager.registerListener(this, mTemp, SensorManager.SENSOR_DELAY_NORMAL);
		 }

		 protected void onPause() {
		     super.onPause();
		     mSensorManager.unregisterListener(this);
		 }

		 public void onAccuracyChanged(Sensor sensor, int accuracy) {}

		 public void onSensorChanged(SensorEvent event) {
			 
			 // Update Temperature on the screen
			 
		 }
		 
	}
	*/

}
