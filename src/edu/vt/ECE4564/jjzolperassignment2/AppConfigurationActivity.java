package edu.vt.ECE4564.jjzolperassignment2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AppConfigurationActivity extends Activity {
	private static Button setAndroidSettingsButton_;
	private static EditText inputServerIP_;
	private static EditText inputServerPort_;
	static TextView updateAndroidSettingStatus_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_configuration);
		setAndroidSettingsButton_ = (Button) findViewById(R.id.setAndroidSettingsBtn);
		inputServerIP_ = (EditText) findViewById(R.id.inputIP);
		inputServerPort_ = (EditText) findViewById(R.id.inputPort);
		updateAndroidSettingStatus_ = (TextView) findViewById(R.id.updateAndroidSettingStatus);
		setAndroidSettingsButton_
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						final String inputServerIP = inputServerIP_.getText()
								.toString();
						final String inputServerPort = inputServerPort_
								.getText().toString();

						if (inputServerIP.isEmpty()
								|| inputServerPort.isEmpty()) {

							// Error, bad server settings

						} else {

							boolean finished = false;
							finished = updateAndroidServerConfiguration(
									inputServerIP, inputServerPort);
							if (finished) {
								AppConfigurationActivity.updateAndroidSettingStatus_
										.setText("");
								displaySettingsUpdateSuccessMessage();
								finished = false;

							}

						}

					}

				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.app_configuration, menu);
		return true;
	}

	public boolean updateAndroidServerConfiguration(String newServerIP,
			String newServerPort) {

		MainActivity.IP = newServerIP;
		MainActivity.Port = newServerPort;

		return true;

	}

	// This function takes the current values of the member variables
	// It then updates the UI based on what the HTTP response returned
	public static void displaySettingsUpdateSuccessMessage() {

		// Update the resulting places text view with the place we found
		AppConfigurationActivity.updateAndroidSettingStatus_
				.append("The Android settings" + "\n"
						+ "were sucessfully updated." + "\n");

	}

}
