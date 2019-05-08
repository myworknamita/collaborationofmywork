package org.chipmonk.smartcity.services;

import java.util.List;

import org.chipmonk.smartcity.dao.AlertDAO;
import org.chipmonk.smartcity.generator.AlertGenerator;
import org.chipmonk.smartcity.model.Alert;
import org.springframework.stereotype.Service;

@Service
public class AlertSimulator implements Runnable {

	private boolean isRunning;
	public AlertSimulator(){
	System.out.println("inside AlertScheduler constructor");
	Thread thread = new Thread(this);
	thread.start();
	}

public void run() {
	AlertDAO alertDao = new AlertDAO();
	AlertGenerator alertGen=new AlertGenerator();
	isRunning=true;
	while (isRunning) {
		System.out.println("inside AlertScheduler run method");
		List<Alert> alertList=alertGen.GenerateAlertList();
			alertDao.save(alertList);
		try {
			Thread.sleep(30000*60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
}
}
}
