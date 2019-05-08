package org.chipmonk.smartcity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.chipmonk.smartcity.dao.AlertDAO;
import org.chipmonk.smartcity.dao.EventDAO;
import org.chipmonk.smartcity.dao.LightDAO;
import org.chipmonk.smartcity.dao.PowerDAO;
import org.chipmonk.smartcity.dao.SmartcityDAO;
import org.chipmonk.smartcity.dao.StatusDAO;
import org.chipmonk.smartcity.dao.StreetDAO;
import org.chipmonk.smartcity.dao.WardsDAO;
import org.chipmonk.smartcity.generator.AlertGenerator;
import org.chipmonk.smartcity.generator.EventGenerator;
import org.chipmonk.smartcity.generator.LightGenerator;
import org.chipmonk.smartcity.generator.PowerGenerator;
import org.chipmonk.smartcity.model.Alert;
import org.chipmonk.smartcity.model.BaseResponse;
import org.chipmonk.smartcity.model.Event;
import org.chipmonk.smartcity.model.FailedLightCount;
import org.chipmonk.smartcity.model.Light;
import org.chipmonk.smartcity.model.LightCount;
import org.chipmonk.smartcity.model.OffLightCount;
import org.chipmonk.smartcity.model.Power;
import org.chipmonk.smartcity.model.PowerConsmedForWard;
import org.chipmonk.smartcity.model.PowerConsumedForEveryWardOnHourlyBasis;
import org.chipmonk.smartcity.model.PowerConsumedForPeriod;
import org.chipmonk.smartcity.model.SmartCity;
import org.chipmonk.smartcity.model.Status;
import org.chipmonk.smartcity.model.Street;
import org.chipmonk.smartcity.model.StreetListResponse;
import org.chipmonk.smartcity.model.WardResponse;
import org.chipmonk.smartcity.model.Wards;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartcityController {
	// private final String sharedKey = "SHARED_KEY";
	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";
	private static final int CODE_SUCCESS = 100;
	private static final int AUTH_FAILURE = 102;

	@RequestMapping(value = "/smartcity", method = RequestMethod.POST)
	public BaseResponse smartcity(@RequestBody SmartCity request) {
		BaseResponse b = new BaseResponse();
		SmartcityDAO s = new SmartcityDAO();
		int i = s.save(request);
		if (i > 0) {
			b.setStatus(SUCCESS_STATUS);
			b.setCode(CODE_SUCCESS);
		} else {
			b.setCode(AUTH_FAILURE);
			b.setStatus(ERROR_STATUS);
		}
		return b;
	}

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	public BaseResponse event(@RequestBody Event request) throws ParseException {
		EventDAO e = new EventDAO();
		EventGenerator eventGenerator = new EventGenerator();
		ArrayList<Event> eventList = eventGenerator.getEvent();
		BaseResponse b = new BaseResponse();
		int i = e.save(eventList);
		if (i > 0) {
			b.setStatus(SUCCESS_STATUS);
			b.setCode(CODE_SUCCESS);
		} else {
			b.setCode(AUTH_FAILURE);
			b.setStatus(ERROR_STATUS);
		}
		return b;

	}

	@RequestMapping(value = "/light", method = RequestMethod.POST)
	public BaseResponse light() {

		LightGenerator lightGen = new LightGenerator();
		ArrayList<Light> light = lightGen.getLight();
		LightDAO lightdao = new LightDAO();
		BaseResponse b = new BaseResponse();
		int i = lightdao.save(light);
		if (i > 0) {
			b.setStatus(SUCCESS_STATUS);
			b.setCode(CODE_SUCCESS);
		} else {
			b.setCode(AUTH_FAILURE);
			b.setStatus(ERROR_STATUS);
		}
		return b;
	}

	@RequestMapping(value = "/power", method = RequestMethod.POST)
	public BaseResponse power(@RequestBody Power request) {
		PowerDAO powerDAO = new PowerDAO();
		PowerGenerator powerGenerator = new PowerGenerator();
		ArrayList<Power> powerList = powerGenerator.getPower();
		BaseResponse b = new BaseResponse();
		int i = powerDAO.save(powerList);
		if (i > 0) {
			b.setStatus(SUCCESS_STATUS);
			b.setCode(CODE_SUCCESS);
		} else {
			b.setCode(AUTH_FAILURE);
			b.setStatus(ERROR_STATUS);
		}
		return b;

	}

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public BaseResponse status(@RequestBody Status request) {
		StatusDAO e = new StatusDAO();
		BaseResponse b = new BaseResponse();
		int i = e.save(request);
		if (i > 0) {
			b.setStatus(SUCCESS_STATUS);
			b.setCode(CODE_SUCCESS);
		} else {
			b.setCode(AUTH_FAILURE);
			b.setStatus(ERROR_STATUS);
		}
		return b;

	}

	@RequestMapping(value = "/street", method = RequestMethod.POST)
	public BaseResponse street(@RequestBody Street request) {
		StreetDAO e = new StreetDAO();
		BaseResponse b = new BaseResponse();
		int i = e.save(request);
		if (i > 0) {
			b.setStatus(SUCCESS_STATUS);
			b.setCode(CODE_SUCCESS);
		} else {
			b.setCode(AUTH_FAILURE);
			b.setStatus(ERROR_STATUS);
		}
		return b;

	}

	@RequestMapping(value = "/wards", method = RequestMethod.POST)
	public BaseResponse wards(@RequestBody Wards request) {
		WardsDAO e = new WardsDAO();
		BaseResponse b = new BaseResponse();
		int i = e.save(request);
		if (i > 0) {
			b.setStatus(SUCCESS_STATUS);
			b.setCode(CODE_SUCCESS);
		} else {
			b.setCode(AUTH_FAILURE);
			b.setStatus(ERROR_STATUS);
		}
		return b;

	}

	@RequestMapping(value = "/ward/all", method = RequestMethod.GET)
	public WardResponse wardsDetails() {
		final Logger logger = Logger.getLogger(SmartcityController.class.getName());
		WardsDAO wardsdao = new WardsDAO();
		WardResponse wardResponse = new WardResponse();
		logger.info("calling wardDAO getAll method");
		List<Wards> wardList = wardsdao.getAllWards();
		wardResponse.setWardList(wardList);
		return wardResponse;

	}

	@RequestMapping(value = "/ward/{id}", method = RequestMethod.GET)
	public Wards wardDetails(@PathVariable(value = "id") int id) {
		WardsDAO wardsdao = new WardsDAO();
		List<Wards> wardList = wardsdao.getWardDetails(id);
		return wardList.get(0);
	}

	@RequestMapping(value = "/light", method = RequestMethod.GET)
	public List<Light> lightMappings(@RequestParam(value = "street") Optional<String> street,
			@RequestParam(value = "ward") Optional<String> ward) {
		SmartcityController smartcityController = new SmartcityController();
		String streetStr = street.isPresent() ? street.get() : null;
		String wardStr = ward.isPresent() ? ward.get() : null;
		int streetId = 0, wardId = 0;
		if (streetStr != null) {
			streetId = Integer.parseInt(streetStr);
		} else {
			wardId = Integer.parseInt(wardStr);
		}
		if (streetId != 0) {
			return smartcityController.streetLightDetails(streetId);
		} else {
			return smartcityController.wardLightDetails(wardId);
		}

	}

	@RequestMapping(value = "/light/{id}", method = RequestMethod.GET)
	public Light lightDetails(@PathVariable(value = "id") int id) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
	  return lightdao.getLightDetails(id);
		
	}

	public List<Light> streetLightDetails(int streetId) {
		LightDAO lightdao = new LightDAO();
		return lightdao.getStreetLightDetails(streetId);

	}

	public List<Light> wardLightDetails(int id) {
		LightDAO lightdao = new LightDAO();
		return lightdao.getWardLightDetails(id);
	}

	@RequestMapping(value = "/street/all", method = RequestMethod.GET)
	public StreetListResponse allStreetDetailsWithinWard() {
		StreetDAO streetdao = new StreetDAO();
		StreetListResponse allStreetDetailsResponse = new StreetListResponse();
		List<Street> allStreetDetailsList = streetdao.getAllStreets();
		allStreetDetailsResponse.setStreetList(allStreetDetailsList);
		return allStreetDetailsResponse;
	}

	@RequestMapping(value = { "/street/{streetId}" }, method = RequestMethod.GET)
	public Street streetDetailsWithinWard(@PathVariable(value = "streetId") int streetId) {
		StreetDAO streetdao = new StreetDAO();
		List<Street> streetDetailsList = streetdao.getStreetDetailsWithinWard(streetId);
		return streetDetailsList.get(0);
	}

	@RequestMapping(value = "/light/count", method = RequestMethod.GET)
	public LightCount lightCountMappings(@RequestParam(value = "location") String location,
			@RequestParam(value = "status") Optional<String> status, @RequestParam(value = "id") int id) {
		String statusValue = status.isPresent() ? status.get() : null;
		SmartcityController smartcityController = new SmartcityController();
		String street = "street";
		String ward = "ward";
		if (statusValue == null) {
			if (location.equals(street)) {
				return smartcityController.streetLightCount(location, id);
			} else if (location.equals(ward)) {
				return smartcityController.wardLightCount(location, id);
			}
		} else if (statusValue != null) {
			switch (statusValue.toLowerCase()) {
			case "on":

				if (location.equals("street")) {
					return smartcityController.streetOnLightCount(location, id, status);
				} else if (location.equals("ward")) {
					return smartcityController.wardOnLightCount(location, id, status);
				}
				break;
			case "off":
				if (location.equals("street")) {
					return smartcityController.streetOffLightCount(location, id, status);
				} else if (location.equals("ward")) {
					return smartcityController.wardOffLightCount(location, id, status);
				}
				break;
			case "failed":
				if (location.equals("street")) {
					return smartcityController.streetFailedLightCount(location, id, status);
				} else if (location.equals("ward")) {
					return smartcityController.wardFailedLightCount(location, id, status);
				}
				break;
			}
		}
		return null;

	}

	@RequestMapping(value = "/light/count/historical", method = RequestMethod.GET)
	public List<FailedLightCount> lightCountMappingBaesOnTime(@RequestParam(value = "location") String location,
			@RequestParam(value = "status") Optional<String> status, @RequestParam(value = "id") int id,
			@RequestParam(value = "period") int period, @RequestParam(value = "duration") String duration)
			throws ParseException {
		String statusValue = status.isPresent() ? status.get() : null;
		String dur[] = duration.split(",");
		String[] date1 = dur[0].split("-");
		String[] date2 = dur[1].split("-");
		String newDate1 = date1[2] + "-" + date1[1] + "-" + date1[0];
		String newDate2 = date2[2] + "-" + date2[1] + "-" + date2[0];
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate1);
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate2);
		SmartcityController smartcityController = new SmartcityController();
		if (statusValue == null) {
			if (location.equalsIgnoreCase("street")) {
				return smartcityController.numberOfLightsInStreetDate(id, period, startDate, endDate);
			} else if (location.equalsIgnoreCase("ward")) {
				return smartcityController.numberOfLightsInWardDate(id, period, startDate, endDate);
			}
		} else {
			if (statusValue.equalsIgnoreCase("failed")) {
				statusValue = "COMM_FAILURE";

				if (location.equals("street")) {
					return smartcityController.streetFailedLightCountBasedOnTime(location, statusValue, id, period,
							startDate, endDate);
				}
				if (location.equals("ward")) {
					return smartcityController.wardFailedLightCountBasedOnTime(location, statusValue, id, period,
							startDate, endDate);
				}
			} else if (statusValue.equalsIgnoreCase("on")) {
				if (location.equalsIgnoreCase("street")) {
					return smartcityController.streetOnLightCountBasedOnTime(statusValue, id, period, startDate,
							endDate);
				}
				if (location.equalsIgnoreCase("ward")) {
					return smartcityController.wardOnLightCountBasedOnTime(statusValue, id, period, startDate, endDate);
				}
			} else if (statusValue.equalsIgnoreCase("off")) {
				if (location.equalsIgnoreCase("street")) {
					return smartcityController.streetOffLightCountBasedOnTime(statusValue, id, period, startDate,
							endDate);
				}
				if (location.equalsIgnoreCase("ward")) {
					return smartcityController.wardOffLightCountBasedOnTime(statusValue, id, period, startDate,
							endDate);
				}
			}
		}

		return null;

	}

	@RequestMapping(value = "/powerconsumption/all/wards/hourlybasis", method = RequestMethod.GET)
	public List<PowerConsumedForEveryWardOnHourlyBasis> powerConsuptionOfAllWardsHourlyBasis() {
		PowerDAO powerDao = new PowerDAO();
		return powerDao.getPowerConsumedForEveryWardOnHourlyBasis();
	}

	@RequestMapping(value = "/light/alerts", method = RequestMethod.GET)
	public List<Alert> getLightAlert() {
		AlertDAO alertDAO = new AlertDAO();
		return alertDAO.getAll();
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/light/alerts/active", method = RequestMethod.GET)
	public List<Alert> todaysAlerts(@RequestParam(value = "id") Optional<String> light,
			@RequestParam(value = "street") Optional<String> street,
			@RequestParam(value = "ward") Optional<String> ward) {
		String idStr = light.isPresent() ? light.get() : null;
		String streetIdStr = street.isPresent() ? street.get() : null;
		String wardIdStr = ward.isPresent() ? ward.get() : null;
		AlertDAO alertDao = new AlertDAO();
		List<Alert> list = new ArrayList<Alert>();
		if (idStr != null) {
			int id = Integer.parseInt(idStr);
			return alertDao.getActiveAlertsForSpecifiedLight(id);

		} else if (streetIdStr != null) {
			int streetId = Integer.parseInt(streetIdStr);
			return list = alertDao.getActiveAlertsForStreet(streetId);
		} else if (wardIdStr != null) {
			int wardId = Integer.parseInt(wardIdStr);
			list = alertDao.getActiveAlertsForWard(wardId);
			return list;
		} else if (streetIdStr != null && wardIdStr != null) {
			int streetId = Integer.parseInt(streetIdStr);
			int wardId = Integer.parseInt(wardIdStr);
			list = alertDao.getActiveAlertForSpecifiedStreetAndWard(streetId, wardId);
			return list;
		} else {
			list = alertDao.getActiveAlerts();
			return list;
		}
	}

	// Get historical light alerts based on lightId
	@RequestMapping(value = "/light/alerts/historical/light", method = RequestMethod.GET)
	public List<Alert> getHistoricalLightAlertsBasedOnLightId(@RequestParam("id") int lightId) {

		AlertDAO alertDAO = new AlertDAO();
		List<Alert> list = new ArrayList<Alert>();
		list = alertDAO.getHistoricalAlertForSpecifiedLightId(lightId);
		return list;

	}

	// Get historical light alerts based on streetId
	@RequestMapping(value = "/light/alerts/historical/street", method = RequestMethod.GET)
	public List<Alert> getHistoricalLightAlertsBasedOnStreetId(@RequestParam("id") int streetId) {

		AlertDAO alertDAO = new AlertDAO();
		List<Alert> list = new ArrayList<Alert>();
		list = alertDAO.getHistoricalAlertsForSpecifiedStreet(streetId);
		return list;
	}

	// Get historical light alerts based on wardId
	@RequestMapping(value = "/light/alerts/historical/ward"
			+ "", method = RequestMethod.GET)
	public List<Alert> getHistoricalLightAlertsBasedOnWardId(@RequestParam("id") int wardId) {

		AlertDAO alertDAO = new AlertDAO();
		List<Alert> list = new ArrayList<Alert>();
		list = alertDAO.getHistoricalAlertsForSpecifiedWard(wardId);
		return list;
	}

	// Get historical light alerts based on streetId and wardsId
	@RequestMapping(value = "/light/alerts/historical/location", method = RequestMethod.GET)
	public List<Alert> getHistoricalLightAlertsBasedOnWardId(@RequestParam("ward") int wardId,
			@RequestParam("street") int streetId) {

		AlertDAO alertDAO = new AlertDAO();
		List<Alert> list = new ArrayList<Alert>();
		list = alertDAO.getHistoricalAlertForSpecifiedStreetAndWard(streetId, wardId);
		return list;
	}

	// Get historical light alerts
	@RequestMapping(value = "/light/alerts/historical", method = RequestMethod.GET)
	public List<Alert> getHistoricalLightAlert() {

		AlertDAO alertDAO = new AlertDAO();
		List<Alert> list = new ArrayList<Alert>();
		list = alertDAO.getHistoricalAlerts();
		return list;
	}

	
	@RequestMapping(value = "/light/alerts/historical/", method = RequestMethod.GET)
	public List<Alert> historicalAlerts(@RequestParam("duration") String duration,
			@RequestParam("id") Optional<String> id) {
		Date startDate = null, endDate = null;
		AlertDAO alertDao = new AlertDAO();
		try {
			String dateStr[] = duration.split(",");
			String sdate2[] = dateStr[0].split("-");
			String startDateStr = sdate2[2] + "-" + sdate2[1] + "-" + sdate2[0] + " " + dateStr[1];
			startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startDateStr);
			String edate2[] = dateStr[2].split("-");
			String endDateStr = edate2[2] + "-" + edate2[1] + "-" + edate2[0] + " " + dateStr[3];
			endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(endDateStr);
			System.out.println(startDate);
			System.out.println(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String idStr = id.isPresent() ? id.get() : null;
		if (idStr == null) {
			return alertDao.getHistoricalAlerts(startDate, endDate);
		} else {
			int lightId = Integer.parseInt(idStr);
			return alertDao.getHistoricalAlertsForSpecifiedLight(startDate, endDate, lightId);
		}
	}

	public List<FailedLightCount> wardOffLightCountBasedOnTime(String statusValue, int id, int period, Date startDate,
			Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getWardOffLightCountBasedOnTime(statusValue, id, period, startDate, endDate);
	}

	public List<FailedLightCount> streetOffLightCountBasedOnTime(String statusValue, int id, int period, Date startDate,
			Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getStreetOffLightCountBasedOnTime(statusValue, id, period, startDate, endDate);
	}

	public List<FailedLightCount> streetOnLightCountBasedOnTime(String statusValue, int id, int period, Date startDate,
			Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getStreetOnLightCountBasedOnTime(statusValue, id, period, startDate, endDate);
	}

	private List<FailedLightCount> wardOnLightCountBasedOnTime(String statusValue, int id, int period, Date startDate,
			Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getWardOnLightCountBasedOnTime(statusValue, id, period, startDate, endDate);
	}

	@RequestMapping(value = "/powerconsumed", method = RequestMethod.GET)
	public List<PowerConsmedForWard> powerConsumedByEveryWard() {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getPowerConsumedByEveryWard();
	}

	@RequestMapping(value = "/light/all/count/off", method = RequestMethod.GET)
	public List<OffLightCount> offLightCountForEveryWard() {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getOffLightCountForEveryWard();
	}

	@RequestMapping(value = "/powerConsumption", method = RequestMethod.GET)
	public List<PowerConsumedForPeriod> powerMapping(@RequestParam(value = "location") String location,
			@RequestParam(value = "id") int id, @RequestParam(value = "period") int period,
			@RequestParam(value = "duration") String duration) throws ParseException {
		String[] dur = duration.split(",");
		String[] date1 = dur[0].split("-");
		String[] date2 = dur[1].split("-");
		String newDate1 = date1[2] + "-" + date1[1] + "-" + date1[0];
		String newDate2 = date2[2] + "-" + date2[1] + "-" + date2[0];
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate1);
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate2);
		SmartcityController smartcityController = new SmartcityController();
		if (location.equalsIgnoreCase("street")) {
			return smartcityController.powerConsumedBasedOnTimeForStreet(id, period, startDate, endDate);
		} else {
			return smartcityController.powerConsumedBasedOnTimeForWard(id, period, startDate, endDate);
		}
	}

	public List<FailedLightCount> numberOfLightsInStreetDate(int id, int period, Date startDate, Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getHistoricalLightCountInStreetDateRange(id, period, startDate, endDate);

	}

	public List<FailedLightCount> numberOfLightsInWardDate(int id, int period, Date startDate, Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getHistoricalLightCountInWardDateRange(id, period, startDate, endDate);

	}

	public List<PowerConsumedForPeriod> powerConsumedBasedOnTimeForWard(int id, int period, Date startDate,
			Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getPowerConsumedBasedOnTimeForWard(id, period, startDate, endDate);
	}

	public List<PowerConsumedForPeriod> powerConsumedBasedOnTimeForStreet(int id, int period, Date startDate,
			Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getPowerConsumedBasedOnTimeForStreet(id, period, startDate, endDate);
	}

	public List<FailedLightCount> wardFailedLightCountBasedOnTime(String location, String status, int id,
			int timePeriod, Date startDate, Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getwardFailedLightCountBasedOnTime(status, id, timePeriod, startDate, endDate);

	}

	public List<FailedLightCount> streetFailedLightCountBasedOnTime(String location, String status, int id,
			int timePeriod, Date startDate, Date endDate) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		return lightdao.getstreetFailedLightCountBasedOnTime(status, id, timePeriod, startDate, endDate);

	}

	public LightCount wardFailedLightCount(String location, int id, Optional<String> status) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		List<LightCount> lightCount = lightdao.getwardFailedLightCount(id, 3);
		return lightCount.get(0);
	}

	public LightCount streetFailedLightCount(String location, int id, Optional<String> status) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		List<LightCount> lightCount = lightdao.getstreetFailedLightCount(id, 3);
		return lightCount.get(0);
	}

	public LightCount wardOffLightCount(String location, int id, Optional<String> status) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		List<LightCount> lightCount = lightdao.getwardOffLightCount(id, 2);
		return lightCount.get(0);
	}

	public LightCount streetOffLightCount(String location, int id, Optional<String> status) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		List<LightCount> lightCount = lightdao.getstreetOffLightCount(id, 2);
		return lightCount.get(0);
	}

	public LightCount wardOnLightCount(String location, int id, Optional<String> status) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		List<LightCount> lightCount = lightdao.getwardOnLightCount(id, 1);
		return lightCount.get(0);
	}

	public LightCount streetOnLightCount(String location, int id, Optional<String> status) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		List<LightCount> lightCount = lightdao.getStreetOnLightCount(id, 1);
		return lightCount.get(0);
	}

	public LightCount wardLightCount(String location, int id) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		List<LightCount> lightCount = lightdao.getWardLightCount(id);
		return lightCount.get(0);
	}

	public LightCount streetLightCount(String location, int id) {
		LightDAO lightdao = new LightDAO();
		// lightdao.setDataSource();
		List<LightCount> lightCount = lightdao.getStreetLightCount(id);
		return lightCount.get(0);

	}
}
