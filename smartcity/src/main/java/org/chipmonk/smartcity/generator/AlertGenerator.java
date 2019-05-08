package org.chipmonk.smartcity.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.chipmonk.smartcity.dao.AlertDAO;
import org.chipmonk.smartcity.dao.LightDAO;
import org.chipmonk.smartcity.model.Alert;
import org.chipmonk.smartcity.model.Light;

public class AlertGenerator {
	private static final int active = 1;

	public List<Alert> GenerateAlertList() {
		List<Alert> newAlertList = new ArrayList<Alert>();
		List<Light> lightList = null;
		LightDAO lightDao = new LightDAO();
		lightList = lightDao.getAll();
		AlertDAO alertDao = new AlertDAO();
		List<Alert> alertList = alertDao.getAll();
		String alertMsg = null;
		String status = null;
		int alertFlag = 0;
		// newAlertList.addAll(alertList);
		for (int lightCounter = 0; lightCounter < lightList.size(); lightCounter++) {
			Light light = lightList.get(lightCounter);
			if (light.getActualStatus() != light.getRequiredStatus()) {
				for (int alertCounter = 0; alertCounter < alertList.size(); alertCounter++) {
					if (alertList.get(alertCounter).getLightId() == light.getId()) {
						alertFlag = 1;
						break;
					}
				}
				if (alertFlag == 1) {
					continue;
				} else {
					if (light.getRequiredStatus() == 1)
						alertMsg = "This Light Sholuld Be Turned On ";
					else if (light.getRequiredStatus() == 2)
						alertMsg = "This Light Should Be Turned Off";
					else
						alertMsg = "Communication Failure";
					if (light.getActualStatus() == 1)
						status = "ON";
					else if (light.getActualStatus() == 2)
						status = "OFF";
					else if (light.getActualStatus() == 3)
						status = "COMM_FAILURE";

					Alert alert = new Alert(light.getId(), light.getStreet_id(), status, alertMsg, active, new Date());
					newAlertList.add(alert);
				}

			}

		}
		// newAlertList.removeAll(alertList);
		return newAlertList;
	}
}