package org.chipmonk.smartcity.model;

import java.util.List;

public class StreetListResponse {
	private List<Street> streetList;

	public List<Street> getStreetList() 
	{
		return streetList;
	}

	public void setStreetList(List<Street> streetList)
	{
		this.streetList = streetList;
	}
}
