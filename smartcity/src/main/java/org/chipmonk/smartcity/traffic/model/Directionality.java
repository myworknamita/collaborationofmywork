package org.chipmonk.smartcity.traffic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "directionality")
public class Directionality {
	@Id
	@GeneratedValue
	private int id;
	private String directionality;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDirectionality() {
		return directionality;
	}

	public void setDirectionality(String directionality) {
		this.directionality = directionality;
	}
}
