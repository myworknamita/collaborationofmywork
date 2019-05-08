package org.chipmonk.smartcity.model;

public class PowerConsumedForEveryWardOnHourlyBasis {
	private int hour;
	private float powerconsumed;
	private int wardId;

	public PowerConsumedForEveryWardOnHourlyBasis() {
		super();
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public float getPowerconsumed() {
		return powerconsumed;
	}

	public void setPowerconsumed(float powerconsumed) {
		this.powerconsumed = powerconsumed;
	}

	public int getWardId() {
		return wardId;
	}

	public void setWardId(int wardId) {
		this.wardId = wardId;
	}
}
