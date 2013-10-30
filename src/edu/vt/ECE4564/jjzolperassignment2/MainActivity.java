package edu.vt.ECE4564.jjzolperassignment2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private static Button MapButton_;
	private static Button PersonalSettingsButton_;
	private static Button ConfigurationButton_;
	static String IP = "192.168.1.132";
	public static String Port = "8080";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MapButton_ = (Button) findViewById(R.id.mapBtn);
		PersonalSettingsButton_ = (Button) findViewById(R.id.personalSettingsBtn);
		ConfigurationButton_ = (Button) findViewById(R.id.configurationBtn);
		MapButton_.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent mapIntent = new Intent(MainActivity.this,
						MapActivity.class);
				startActivity(mapIntent);

			}
		});
		PersonalSettingsButton_.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent psIntent = new Intent(MainActivity.this,
						PersonalSettingsActivity.class);
				startActivity(psIntent);

			}
		});
		ConfigurationButton_.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent appconfigIntent = new Intent(MainActivity.this,
						AppConfigurationActivity.class);
				startActivity(appconfigIntent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
