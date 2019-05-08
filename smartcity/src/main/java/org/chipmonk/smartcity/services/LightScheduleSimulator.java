package org.chipmonk.smartcity.services;

import org.chipmonk.smartcity.dao.LightDAO;
import org.springframework.stereotype.Service;

@Service
public class LightScheduleSimulator implements Runnable {

	private boolean isRunning;
	public LightScheduleSimulator() {
		System.out.println("inside LightScheduler constructor");
		Thread thread = new Thread(this);
		thread.start();
	}
	public void run() {
		isRunning=true;
		while (isRunning) {
			System.out.println("inside LightScheduler run()");
			LightDAO lightDao = new LightDAO();
			lightDao.updateOnOffTimeForLight();
			try {
				Thread.sleep(43200000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
