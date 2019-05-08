package org.chipmonk.smartcity.traffic.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.chipmonk.smartcity.traffic.dao.RoadDAO;
import org.chipmonk.smartcity.traffic.model.Road;
import org.chipmonk.smartcity.traffic.model.TrafficEstimation;

public class TrafficGenerator {
	private static final int DAY_EVALUATION = 48;
	private static final int DAYS_IN_MONTH = 90;

	public List<TrafficEstimation> trafficGenerator() {
		List<TrafficEstimation> trafficList = new ArrayList<TrafficEstimation>();
		try {
			RoadDAO roaddao = new RoadDAO();
			List<Road> roadList = roaddao.getAll();
			int[] speed = { 33, 30, 25, 22, 19, 18, 17, 16, 31, 32, 15, 12, 13, 10, 11 };
			long currMinute = 0;
			Date date = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2015-03-01 12:00:00");
			for (int dayCounter = 1; dayCounter <= DAYS_IN_MONTH; dayCounter++) {
				for (int period = 1; period <= DAY_EVALUATION; period++) {
					for (int roadCounter = 1; roadCounter <= roadList.size(); roadCounter++) {
						int i = TrafficGenerator.random();
						TrafficEstimation trafficEstimation = new TrafficEstimation(roadCounter, speed[i], date);
						trafficList.add(trafficEstimation);
					}
					currMinute = date.getTime() + (30 * 1000 * 60);
					date = new Date(currMinute);
				}

			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return trafficList;
	}

	public static int random() {
		int low = 0;
		int high = 14;
		Random random = new Random();
		return random.nextInt(low + high) + low;
	}
}
