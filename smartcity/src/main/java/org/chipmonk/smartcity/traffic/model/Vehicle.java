package org.chipmonk.smartcity.traffic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicle")
public class Vehicle {
	
	public Vehicle(int roadId, String type, String ownership) {
		super();
		this.roadId = roadId;
		this.type = type;
		this.ownership = ownership;
	}
	@Id
	@GeneratedValue
	private int id;
	@Column(name="road_id")
	private int roadId;
	private String type;
	private String ownership;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOwnership() {
		return ownership;
	}
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
}
