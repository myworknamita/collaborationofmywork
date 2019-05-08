package org.chipmonk.smartcity.traffic.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.chipmonk.smartcity.generator.DateGenerator;
import org.chipmonk.smartcity.traffic.dao.RoadDAO;
import org.chipmonk.smartcity.traffic.model.Road;
import org.chipmonk.smartcity.traffic.model.RoadLaying;

public class RoadLayingGenerator {
 public final static int NUM_OF_CONTRACTS=4;
	public List<RoadLaying> getRoadLaying()
	{
		String[] name= {"John","Rajendra","Rohith","Subhash","Prateek","Preethi","Pragosh","Akila"};
		Float[] cost= {30.4f,40.5f,36.0f,20.09f,67.04f,45.87f,80.04f,43.454f};
		RoadDAO roaddao=new RoadDAO();
		List<Road> roadList=roaddao.getAll();
		List<RoadLaying> roadLayingList=new ArrayList<RoadLaying>();
		DateGenerator dateGen=new DateGenerator();
		List<Date> dateList=dateGen.bigDateGenerator();
		for(int numrOfContracts=0;numrOfContracts<NUM_OF_CONTRACTS;numrOfContracts++)
		{
		for(int roadCounter=0;roadCounter<roadList.size();roadCounter++)
		{
			int random=RoadLayingGenerator.random();
			RoadLaying roadLaying=new RoadLaying(roadCounter, name[random], dateList.get(random), cost[random]);
			roadLayingList.add(roadLaying);			
		}
		}
		return roadLayingList;
	}
	public static int random()
	{
		int low=0;
		int high=7;
		Random random=new Random();
		return random.nextInt(low+high)+low;
	}
}
