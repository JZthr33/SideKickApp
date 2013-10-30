package edu.vt.ECE4564.jjzolperassignment2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PersonalSettingsActivity extends Activity {
	private static Button setPreferencesButton_;
	private static EditText inputRadius_;
	private static EditText inputType_;
	boolean returnToMap = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_settings);
		setPreferencesButton_ = (Button) findViewById(R.id.setPreferencesBtn);
		inputRadius_ = (EditText) findViewById(R.id.inputRadius);
		inputType_ = (EditText) findViewById(R.id.inputType);
		setPreferencesButton_.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				final String radiusFilter = inputRadius_.getText().toString();
				final String typeFilter = inputType_.getText().toString();

				Intent mapIntent = new Intent(getApplicationContext(),
						MapActivity.class);

				mapIntent.putExtra("RadiusFilter", radiusFilter);
				mapIntent.putExtra("TypeFilter", typeFilter);
				startActivity(mapIntent);
				finish();

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_settings, menu);
		return true;
	}

}
