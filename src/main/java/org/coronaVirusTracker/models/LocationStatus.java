package org.coronaVirusTracker.models;

import java.io.Serializable;

public class LocationStatus implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String state;
	private String country;
	private int latestTotalCases;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLatestTotalCases() {
		return latestTotalCases;
	}

	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}

	@Override
	public String toString() {
		return "State=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases
				+ "]";
	}

}
