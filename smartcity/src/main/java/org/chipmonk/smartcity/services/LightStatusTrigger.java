package org.chipmonk.smartcity.services;

import org.chipmonk.smartcity.dao.LightDAO;
import org.springframework.stereotype.Service;

@Service
public class LightStatusTrigger implements Runnable {

	private boolean isRunning;
	public LightStatusTrigger() {
		System.out.println("inside LightStatusScheduler constructor");
		Thread thread = new Thread(this);
		thread.start();
	}
	public void run() {
		LightDAO lightDao = new LightDAO();
		isRunning=true;
		System.out.println("inside LightStatusScheduler run method");
//		while (isRunning) {
//			lightDao.updateLightStatus();
//			try {
//				Thread.sleep(30000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}
}
