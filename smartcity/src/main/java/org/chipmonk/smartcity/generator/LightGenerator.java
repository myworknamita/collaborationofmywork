package org.chipmonk.smartcity.generator;

import java.util.ArrayList;

import java.util.Date;

import java.util.List;

import java.util.Random;

import org.chipmonk.smartcity.dao.StreetDAO;

import org.chipmonk.smartcity.dao.WardsDAO;

import org.chipmonk.smartcity.model.Light;

import org.chipmonk.smartcity.model.Street;

import org.chipmonk.smartcity.model.Wards;

public class LightGenerator {

	private static final int LIGHT_OFF = 2;
	private static final int LIGHT_ON = 1;
	private static final int COMM_FAILURE = 3;
	private static final int NO_OF_LIGHTS = 10;
	//private int[] statusGenerator = { LIGHT_ON, LIGHT_OFF, COMM_FAILURE };
	private int[] powerValues = { 40, 45, 40, 55, 50, 90, 100, 115, 75, 95, 65, 60, 70, 80, 85 };
	private double[] locationValues = { 0.0001, 0.0003, 0.0015, 0.0002, 0.0017, 0.0019, 0.0021, 0.0011, 0.0005, 0.0007,
			0.0014, 0.0014, 0.0023, 0.0025 };

	public ArrayList<Light> getLight() {
		int actualStatus = 1, requiredStatus = 1;
		ArrayList<Light> lightsToBeCreated = new ArrayList<Light>();
		LocationGenerator locationGenerator = new LocationGenerator();
		ArrayList<Double> longitudeList = locationGenerator.wardsLongitude();
		ArrayList<Double> lattitudeList = locationGenerator.wardsLattitude();
		DateGenerator dateGenerator = new DateGenerator();
		ArrayList<Date> on = null;
		ArrayList<Date> off = null;
		if (actualStatus == LIGHT_ON) {
			on = dateGenerator.bigDateGenerator();
			off = dateGenerator.smallDateGenerator();
		} else {
			off = dateGenerator.bigDateGenerator();
			on = dateGenerator.smallDateGenerator();
		}
		StreetDAO streetDAO = new StreetDAO();
		List<Street> allStreets = streetDAO.getAll();
		WardsDAO wardsDAO = new WardsDAO();
		List<Wards> allWards = wardsDAO.getAll();
		int streetUpdater = 0, streetBreaker = 0;
		int streetsPerWard = allStreets.size() / allWards.size();
		for (int wardCounter = 0; wardCounter < allWards.size(); wardCounter++) {
			Wards ward = allWards.get(wardCounter);
			double longitude = longitudeList.get(wardCounter);
			System.out.println(longitude);
			double lattitude = lattitudeList.get(wardCounter);
			for (int streetCounter = streetUpdater; streetCounter < allStreets.size(); streetCounter++) {
				Street street = allStreets.get(streetCounter);
				for (int lightCounter = 0; lightCounter < NO_OF_LIGHTS; lightCounter++) {
					int[] a = dateGenerator.randomNumber();
					String lightName = ward.getName() + "_" + street.getName() + "_" + (lightCounter + 1);
					Date onDate = on.get(a[0]);
					Date offDate = off.get(a[0]);
					int randomNum = random();
					longitude = longitude + locationValues[randomNum];
					lattitude = lattitude + locationValues[randomNum];
					float powerRating = powerValues[randomNum];
					Date mfgDate = on.get(a[1]);
					actualStatus = getRandomStatus();
					requiredStatus = getRandomStatus();
					Date installDate = off.get(a[1]);
					Light light = new Light(street.getId(), lightName, onDate, offDate, powerRating, mfgDate,
							installDate, actualStatus, requiredStatus, longitude, lattitude);
					lightsToBeCreated.add(light);
				}
				if (streetCounter == (streetsPerWard + streetBreaker - 1)) {
					streetUpdater = streetCounter + 1;
					streetBreaker = streetBreaker + 5;
					break;
				}
			}
			actualStatus = COMM_FAILURE;
		}
		for (int i = 0; i < allStreets.size(); i++) {
			System.out.println(allStreets.get(i));
		}
		return lightsToBeCreated;
	}

	private static int random() {
		Random r = new Random();
		int high = 14;
		int low = 0;
		int randomNumber = r.nextInt(high-low) + low;
		return randomNumber;
	}

	private static int getRandomStatus() {
		Random random = new Random();
		int high = 3;
		int low = 1;
		return random.nextInt(high-low) + low;
	}

}
