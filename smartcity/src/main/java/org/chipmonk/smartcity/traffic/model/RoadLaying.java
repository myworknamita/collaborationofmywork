package org.chipmonk.smartcity.traffic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "road_laying")
public class RoadLaying {
	@Id
	@GeneratedValue
	private int id;

	public RoadLaying(int roadId, String contractorName, Date date, float cost) {
		super();
		this.roadId = roadId;
		this.contractorName = contractorName;
		this.date = date;
		this.cost = cost;
	}

	public RoadLaying() {
		super();
	}

	@JoinColumn(name="id")
	@Column(name = "road_id")
	private int roadId;
	@Column(name = "contractor_name")
	private String contractorName;
	private Date date;
	private float cost;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoadId() {
		return roadId;
	}

	public void setRoadId(int roadId) {
		this.roadId = roadId;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
}
