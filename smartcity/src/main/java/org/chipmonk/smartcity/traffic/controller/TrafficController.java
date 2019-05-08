package org.chipmonk.smartcity.traffic.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.chipmonk.smartcity.model.BaseResponse;
import org.chipmonk.smartcity.traffic.dao.RoadDAO;
import org.chipmonk.smartcity.traffic.dao.RoadLayingDAO;
import org.chipmonk.smartcity.traffic.dao.SectionDAO;
import org.chipmonk.smartcity.traffic.dao.TrafficEstimationDAO;
import org.chipmonk.smartcity.traffic.dao.VehicleDAO;
import org.chipmonk.smartcity.traffic.generator.RoadLayingGenerator;
import org.chipmonk.smartcity.traffic.generator.TrafficGenerator;
import org.chipmonk.smartcity.traffic.generator.VehicleGenerator;
import org.chipmonk.smartcity.traffic.model.Road;
import org.chipmonk.smartcity.traffic.model.RoadLaying;
import org.chipmonk.smartcity.traffic.model.Section;
import org.chipmonk.smartcity.traffic.model.TrafficEstimation;
import org.chipmonk.smartcity.traffic.model.Vehicle;
import org.chipmonk.smartcity.traffic.model.VehicleCount;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrafficController {

	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";
	private static final int CODE_SUCCESS = 100;
	private static final int AUTH_FAILURE = 102;
	@RequestMapping(value="/trafficestimation",method=RequestMethod.POST)
	public BaseResponse road()
	{
		BaseResponse baseResponse=new BaseResponse();
		TrafficEstimationDAO trafficdao=new TrafficEstimationDAO();
		TrafficGenerator trafficGenerator=new TrafficGenerator();
		List<TrafficEstimation> trafficList=trafficGenerator.trafficGenerator();
		trafficdao.setDataSource();
		int i=trafficdao.save(trafficList);
		if(i>0)
		{
			baseResponse.setCode(CODE_SUCCESS);
			baseResponse.setStatus(SUCCESS_STATUS);
		}
		else
		{
			baseResponse.setStatus(ERROR_STATUS);
			baseResponse.setCode(AUTH_FAILURE);
		}
		return baseResponse;
	}
	
	@RequestMapping(value="/vehicle",method=RequestMethod.POST)
	public BaseResponse section()
	{
		BaseResponse baseResponse=new BaseResponse();
		VehicleDAO vehicledao=new VehicleDAO();
		vehicledao.setDataSource();
		VehicleGenerator vehicleGenerator=new VehicleGenerator();
		List<Vehicle> vehiclesList=vehicleGenerator.getVehicles();
		int i=vehicledao.save(vehiclesList);
		if(i>0)
		{
			baseResponse.setCode(CODE_SUCCESS);
			baseResponse.setStatus(SUCCESS_STATUS);
		}
		else
		{
			baseResponse.setStatus(ERROR_STATUS);
			baseResponse.setCode(AUTH_FAILURE);
		}
		return baseResponse;
	}
	@RequestMapping(value="/laying",method=RequestMethod.POST)
	public BaseResponse roadLaying()
	{
		BaseResponse baseResponse=new BaseResponse();
		RoadLayingDAO roadLayingDao=new RoadLayingDAO();
		RoadLayingGenerator roadLayingGenerator=new RoadLayingGenerator();
		List<RoadLaying> roadLayingList=roadLayingGenerator.getRoadLaying();
		int i=roadLayingDao.save(roadLayingList);
		if(i>0)
		{
			baseResponse.setCode(CODE_SUCCESS);
			baseResponse.setStatus(SUCCESS_STATUS);
		}
		else
		{
			baseResponse.setCode(AUTH_FAILURE);
			baseResponse.setStatus(ERROR_STATUS);
		}
		return baseResponse;
	}
	@RequestMapping(value="/roadlaying",method=RequestMethod.GET)
	public List<RoadLaying> roadLayingList()
	{
		RoadLayingDAO roadLayingDao=new RoadLayingDAO();
		roadLayingDao.setDataSource();
		return roadLayingDao.getAll();
		
	}
	@RequestMapping(value="/road/all",method=RequestMethod.GET)
	public List<Road> allRoads()
	{
		RoadDAO roaddao=new RoadDAO();
		return roaddao.getAll();
	}
	@RequestMapping(value="/section/all",method=RequestMethod.GET)
	public List<Section> allSections()
	{
		SectionDAO sectiondao=new SectionDAO();
		sectiondao.setDataSource();
		return sectiondao.getAll();
	}
	@RequestMapping(value="/road/intersect/{id}",method=RequestMethod.GET)
	public List<Road> roadIntersection(@PathVariable(value="id") int id)
	{
		RoadDAO roaddao=new RoadDAO();
		roaddao.setDataSource();
		return roaddao.getRoadIntersection(id);
	}
	@RequestMapping(value="/road",method=RequestMethod.GET)
	public List<Road> road(@RequestParam(value="type") Optional<String> type, @RequestParam(value="directionality") Optional<String> directionality)
	{
		RoadDAO roaddao=new RoadDAO();
		roaddao.setDataSource();
		String typeStr=type.isPresent()?type.get():null;
		String directionalityStr=directionality.isPresent()?directionality.get():null;
		if(typeStr!=null)
		{
			return roaddao.getRoadBasedOnType(typeStr);
		}
		else if(directionalityStr!=null)
		{
			return roaddao.getRoadBasedOnDirectionality(directionalityStr);
		}
		else return null;
	}
	@RequestMapping(value="/traffic/highdensity",method=RequestMethod.GET)
	public List<Road> highDensity(@RequestParam(value="date") String dateString,@RequestParam(value="time") String timeString) throws ParseException
	{
		String dateStr[]=dateString.split(",");
		String dateStr1[]=dateStr[0].split("-");
		String dateStr2[]=dateStr[1].split("-");
		dateStr[0]=dateStr1[2]+"-"+dateStr1[1]+"-"+dateStr1[0];
		dateStr[1]=dateStr2[2]+"-"+dateStr2[1]+"-"+dateStr2[0];
		Date startDate=new SimpleDateFormat("yyyy-MM-dd").parse(dateStr[0]);
		Date endDate=new SimpleDateFormat("yyyy-MM-dd").parse(dateStr[1]);
		String timeStr[]=timeString.split(",");
		System.out.println(timeStr[0]);
		System.out.println(timeStr[1]);
		LocalTime startTime=LocalTime.parse(timeStr[0]);
		LocalTime endTime=LocalTime.parse(timeStr[1]);
		//LocalTime endTime=null;//LocalTime.parse(timeStr[1]);
		TrafficEstimationDAO trafficdao= new TrafficEstimationDAO();
		trafficdao.setDataSource();
		return trafficdao.getHighDensityRoadDetails(startDate,endDate,startTime,endTime);
	}
	@RequestMapping(value="/vehicle/count",method=RequestMethod.GET)
	public List<VehicleCount> vehicleCount(@RequestParam(value="ownership") String ownership,@RequestParam(value="date") String dateString,@RequestParam(value="time") String timeString) throws ParseException
	{
		String dateStr[]=dateString.split(",");
		String timeStr[]=timeString.split(",");
		String dateStr1[]=dateStr[0].split("-");
		String dateStr2[]=dateStr[1].split("-");
		dateStr[0]=dateStr1[2]+"-"+dateStr1[1]+"-"+dateStr1[0];
		dateStr[1]=dateStr2[2]+"-"+dateStr2[1]+"-"+dateStr2[0];
		LocalTime startTime=LocalTime.parse(timeStr[0]);
		LocalTime endTime=LocalTime.parse(timeStr[1]);
		Date startDate=new SimpleDateFormat("yyyy-MM-dd").parse(dateStr[0]);
		Date endDate=new SimpleDateFormat("yyyy-MM-dd").parse(dateStr[1]);
		VehicleDAO vehicledao=new VehicleDAO();
		vehicledao.setDataSource();
		return vehicledao.getVehicleCount(ownership,startDate,endDate,startTime,endTime);
	}
	/*@RequestMapping(value="/trainingset",method=RequestMethod.POST)
	public BaseResponse trainingSet()
	{
		
	}*/
}
