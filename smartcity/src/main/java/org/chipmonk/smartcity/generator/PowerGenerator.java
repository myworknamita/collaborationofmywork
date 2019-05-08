package org.chipmonk.smartcity.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.chipmonk.smartcity.dao.LightDAO;
import org.chipmonk.smartcity.model.Light;
import org.chipmonk.smartcity.model.Power;

public class PowerGenerator {
	private static final int DAY_EVALUATION = 48;
	private static final int DAYS_IN_MONTH = 30;
	

	/* uses date generator object and creates the list of power class instances */
	public ArrayList<Power> getPower() {
		//int[] powerValues = { 0,0,0,0,0,0,0,0,0,0,5,45,40,50,45,40,45,45,50,44,45,40,45,50,5,0,0,0,0};
		ArrayList<Power> powerList = new ArrayList<Power>();// this List contains 550 records
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2018-01-01 00:00:00");
			long currMinute = 0;
			LightDAO lightDAO = new LightDAO();
			ArrayList<Light> allLights = (ArrayList<Light>) lightDAO.getAll();
			for(int monthOfYear=1;monthOfYear<=2;monthOfYear++) {
			for (int numberOfDays = 1; numberOfDays <= DAYS_IN_MONTH; numberOfDays++) {
				for (int thirtySecIdx = 0; thirtySecIdx < DAY_EVALUATION; thirtySecIdx++) {
					for (int lightCounter = 1; lightCounter <= allLights.size(); lightCounter++) {
						int randomNumber=0;
						if (thirtySecIdx <= 11) {
							randomNumber=random1();
						} else if (thirtySecIdx > 11 && thirtySecIdx <= 24) {
							randomNumber=random2();
						} else if (thirtySecIdx > 24) {
							randomNumber=random1();
						}
						Power power = new Power(lightCounter, randomNumber, date);
						powerList.add(power);

					}
					currMinute = date.getTime() + (30 * 1000 * 60);
					date = new Date(currMinute);
				}
			}
				//currMinute = date.getTime() + (1000 * 60 * 60 * 24);
				//date = new Date(currMinute);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return powerList;
	}

	private static int random1() {
		Random r = new Random();
		int high = 4;
		int low = 0;
		int randomNumber = r.nextInt(high-low) + low;
		return randomNumber;
	}
	private static int random2() {
		Random r = new Random();
		int high = 35;
		int low = 30;
		int randomNumber = r.nextInt(high-low) + low;
		return randomNumber;
	}

}
