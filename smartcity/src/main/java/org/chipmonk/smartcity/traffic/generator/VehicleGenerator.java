package org.chipmonk.smartcity.traffic.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.chipmonk.smartcity.traffic.dao.RoadDAO;
import org.chipmonk.smartcity.traffic.model.Road;
import org.chipmonk.smartcity.traffic.model.Vehicle;

public class VehicleGenerator {

	public List<Vehicle> getVehicles()
	{
		RoadDAO roaddao=new RoadDAO();
		List<Road> roadList=roaddao.getAll();
		List<Vehicle> vehicleList=new ArrayList<Vehicle>();
		String owner="public";
		String type[]= {"car","bus","auto","bike","scooty","bicycle","mini-bus","truck","lorry"};
		for(int roadCounter=1;roadCounter <= roadList.size();roadCounter++)
		{
			for(int i=0;i<1000;i++)
			{
			int j=VehicleGenerator.random();
			Vehicle vehicle=new Vehicle(roadCounter,type[j],owner);
			vehicleList.add(vehicle);
			if((i%3)==0)
				owner="private";
			else if((i%2)==0)
				owner="public";
			}
			owner="public";
		}
		return vehicleList;
	}

	private static int random() {
		int low=0;
		int high=9;
		Random random=new Random();
		
		return random.nextInt(low+high)+low;
	}
}
