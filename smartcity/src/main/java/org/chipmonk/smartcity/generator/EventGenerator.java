package org.chipmonk.smartcity.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.chipmonk.smartcity.dao.LightDAO;
import org.chipmonk.smartcity.model.Event;
import org.chipmonk.smartcity.model.Light;

public class EventGenerator {

	private static final String LIGHT_OFF = "OFF";
	private static final String LIGHT_ON = "ON";
	private static final String COMM_FAILURE="COMM_FAILURE";
	private static final int DAY_EVALUATION = 2;
	private static final int DAYS_IN_YEAR = 365;

	public ArrayList<Event> getEvent() throws ParseException {
		Date time = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-01-28 18:00:00");
		long currMinute=0;
		ArrayList<Event> eventsToBeGenerated=new ArrayList<Event>();
		LightDAO lightDAO = new LightDAO();
		ArrayList<Light> allLights = (ArrayList<Light>) lightDAO.getAll();
		String action=LIGHT_OFF;
		for (int numberOfDays = 1; numberOfDays <= DAYS_IN_YEAR; numberOfDays++) {
			for (int countsPerDay = 0; countsPerDay < DAY_EVALUATION; countsPerDay++) {
				for (int lightCounter = 1; lightCounter <= allLights.size(); lightCounter++) {
					Event event=new Event(lightCounter,time,action);
					eventsToBeGenerated.add(event);
					if(action.equals(LIGHT_ON))
					{
						action = LIGHT_OFF;
					}
					else if(action.equals(LIGHT_OFF))
					{
						action=COMM_FAILURE;
					}
					else
					{
						action=LIGHT_ON;
					}
				}
				currMinute = time.getTime() + (12*60*1000*60);
				time = new Date(currMinute);
			}
		}	
		return eventsToBeGenerated;
}
}
