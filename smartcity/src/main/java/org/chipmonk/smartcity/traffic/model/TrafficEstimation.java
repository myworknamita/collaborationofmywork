package org.chipmonk.smartcity.traffic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traffic_estimation")
public class TrafficEstimation {
	@Id
	@GeneratedValue
	private int id;

	public TrafficEstimation(int roadId, int speed, Date date) {
		super();
		this.roadId = roadId;
		this.speed = speed;
		this.date = date;
	}

	@Column(name = "road_id")
	private int roadId;
	private int speed;
	private Date date;

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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
