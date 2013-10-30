package edu.vt.ECE4564.jjzolperassignment2;

public class Place {

	// Create a structure that is passed between the background network and
	// front tasks
	String name;
	String vicinity;

	// Default Constructor
	public Place() {
		this.name = "";
		this.vicinity = "";
	}

	// Initial Constructor
	public Place(String a, String b) {
		this.name = a;
		this.vicinity = b;
	}

	// Set Name
	public void setName(String a) {

		this.name = a;

	}

	// Set Vicinity
	public void setVicinity(String b) {

		this.vicinity = b;

	}

}
