package edu.vt.ECE4564.jjzolperassignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.view.View;

class NetworkTask extends AsyncTask<String, Place, ArrayList<Place>> {

	protected ArrayList<Place> doInBackground(String... searchquery) {

		ArrayList<Place> toReturn = new ArrayList<Place>();

		toReturn = queryJettyServer(searchquery[0], searchquery[1],
				searchquery[2]);

		return toReturn;

	}

	protected void onPostExecute(ArrayList<Place> A) {

		MapActivity.resultingPlaces_.setText("");

		if (A.isEmpty()) {

			// No results returned
			updateUINoResults();

		} else {
			// Iterate over what we collected and update the UI
			for (int i = 0; i < A.size(); i++) {

				updateUI(A.get(i));

			}
		}

	}

	public ArrayList<Place> queryJettyServer(String locationFilter,
			String radiusFilter, String typeFilter) {

		ArrayList<Place> temp = new ArrayList<Place>();

		try {

			// Now that we have connected to the server we want to query our
			// Jetty server
			// that will then go out to Google and get the relevant information
			String locationFilter2 = "";
			locationFilter2 = locationFilter.replace(" ", "");
			HttpClient client = new DefaultHttpClient();
			String queryRequestURL = "http://" + MainActivity.IP + ":"
					+ MainActivity.Port + "/data?format=xml&location="
					+ locationFilter2.replaceAll("\n", "") + "&radius="
					+ radiusFilter.replaceAll("\n", "") + "&type="
					+ typeFilter.replaceAll("\n", "");
			HttpGet request = new HttpGet(queryRequestURL);
			HttpResponse response = client.execute(request);

			// Compile the XML from the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
			String finalXMLStringResultToParse = builder.toString();

			temp = parsePlaces(finalXMLStringResultToParse);

		} catch (IOException e) {

			e.printStackTrace();

		}

		return temp;

	}

	public static ArrayList<Place> parsePlaces(String XMLToParseStr) {

		ArrayList<Place> toReturn = new ArrayList<Place>();

		String[] items = XMLToParseStr.split("result");

		for (int j = 0; j < items.length; j++) {

			if (items[j].contains("<name>")) {

				// Now take the currentitem string, and begin to parse for the
				// individual item
				String placename = items[j].substring(
						items[j].indexOf("<name>") + "<name>".length(),
						items[j].indexOf("</name>"));
				String placevicinity = items[j].substring(
						items[j].indexOf("<vicinity>") + "<vicinity>".length(),
						items[j].indexOf("</vicinity>"));

				Place placeobj = new Place(placename, placevicinity);
				toReturn.add(placeobj);
				placename = "";
				placevicinity = "";

			}
			// Finish parsing for this individual item and continue to the next
			// item

		}

		return toReturn;

	}

	// There were no results so update the UI accordingly
	public static void updateUINoResults() {

		MapActivity.pb.setVisibility(View.INVISIBLE);

		// Update the resulting places text view with the place we found
		MapActivity.resultingPlaces_
				.append("There were no results, please try again.");

	}

	// This function takes the current values of the member variables
	// It then updates the UI based on what the HTTP response returned
	public static void updateUI(Place PlaceA) {

		MapActivity.pb.setVisibility(View.INVISIBLE);

		// Update the resulting places text view with the place we found
		MapActivity.resultingPlaces_.append("Name: " + PlaceA.name + "\n"
				+ "Vicinity: " + PlaceA.vicinity + "\n");

	}

}
