package org.chipmonk.smartcity.generator;

import java.util.ArrayList;
import java.util.List;

import org.chipmonk.smartcity.dao.WardsDAO;
import org.chipmonk.smartcity.model.Wards;

public class LocationGenerator {
	public ArrayList<Double> wardsLongitude=new ArrayList<Double>();
	public  ArrayList<Double> wardsLattitude=new ArrayList<Double>();
	public  ArrayList<Double> wardsLongitude()
	{
		double[] longitude= {77.6227,77.4788,77.5946,77.6412,77.5333};
		WardsDAO wardsDAO=new WardsDAO();
		List<Wards> wardsList=wardsDAO.getAll();
		for(int wardsCounter=0;wardsCounter<wardsList.size();wardsCounter++)
		{
		wardsLongitude.add(wardsCounter,longitude[wardsCounter]);
		}
		return wardsLongitude;
	}
	public  ArrayList<Double> wardsLattitude()
	{
		double[] lattitude= {12.9317,12.9023,12.9716,12.9719,12.9969};
		WardsDAO wardsDAO=new WardsDAO();
		List<Wards> wardsList=wardsDAO.getAll();
		for(int wardsCounter=0;wardsCounter<wardsList.size();wardsCounter++)
		{
		wardsLattitude.add(wardsCounter,lattitude[wardsCounter]);
		}
		return wardsLattitude;
	}

}
