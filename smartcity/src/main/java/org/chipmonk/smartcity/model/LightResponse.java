package org.chipmonk.smartcity.model;

import java.util.List;

public class LightResponse {
	public LightResponse() {
		super();
	}

	private int numOfLights;
	private String maxLightsOffLocation;
	private List<Light> lightList;
	private List<PowerConsumed> list;
	private float highestPowerDemand;
	private String highestPowerDemandLocation;

	public int getNumOfLights() {
		return numOfLights;
	}

	public void setNumOfLights(int numOfLights) {
		this.numOfLights = numOfLights;
	}

	public String getMaxLightsOffLocation() {
		return maxLightsOffLocation;
	}

	public void setMaxLightsOffLocation(String maxLightsOffLocation) {
		this.maxLightsOffLocation = maxLightsOffLocation;
	}

	public List<Light> getLightList() {
		return lightList;
	}

	public void setLightList(List<Light> lightList) {
		this.lightList = lightList;
	}

	public float getHighestPowerDemand() {
		return highestPowerDemand;
	}

	public void setHighestPowerDemand(float highestPowerDemand) {
		this.highestPowerDemand = highestPowerDemand;
	}

	public String getHighestPowerDemandLocation() {
		return highestPowerDemandLocation;
	}

	public void setHighestPowerDemandLocation(String highestPowerDemandLocation) {
		this.highestPowerDemandLocation = highestPowerDemandLocation;
	}

	public List<PowerConsumed> getList() {
		return list;
	}

	public void setList(List<PowerConsumed> list) {
		this.list = list;
	}

}
