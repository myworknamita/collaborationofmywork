package org.chipmonk.smartcity.services;

import org.chipmonk.smartcity.dao.AlertDAO;
import org.springframework.stereotype.Service;

@Service
public class AlertTrigger implements Runnable {

		private boolean isRunning;
		public AlertTrigger(){
		System.out.println("inside AlertTrigger constructor");
		Thread thread = new Thread(this);
		thread.start();
		}

	public void run() {
		AlertDAO alertDao = new AlertDAO();
		isRunning=true;
		while (isRunning) {
			System.out.println("inside AlertTrigger run method");
			alertDao.updateAlertActivity();
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	}
	

}
