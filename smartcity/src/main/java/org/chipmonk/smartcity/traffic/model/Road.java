package org.chipmonk.smartcity.traffic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "road")
public class Road {
	public Road() {
		super();
	}

	@Id
	@GeneratedValue
	private int id;
	private String name;
	@Column(name = "type_id")
	private int type;
	private int capacity;
	@Column(name = "directionality_id")
	private int directionality;

	public Road(String name, int type, int capacity, int directionality) {
		super();
		this.name = name;
		this.type = type;
		this.capacity = capacity;
		this.directionality = directionality;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getDirectionality() {
		return directionality;
	}

	public void setDirectionality(int directionality) {
		this.directionality = directionality;
	}
}
