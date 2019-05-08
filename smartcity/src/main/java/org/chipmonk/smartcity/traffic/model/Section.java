package org.chipmonk.smartcity.traffic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "section")
public class Section {
	public Section() {
		super();
	}

	@Id
	@GeneratedValue
	private int id;
	@Column(name = "road_id")
	private int roadId;
	private float length;
	private float width;

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

	

	public Section(int roadId,float length, float width) {
		super();
		this.roadId = roadId;
		this.length = length;
		this.width = width;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
}
