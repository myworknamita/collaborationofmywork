package org.chipmonk.smartcity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="street")
public class Street
{
	public Street() {
		super();
	}
	@Id
	@GeneratedValue
	@Column(name="id",nullable=false, unique=true)
	private int id;
	private int wards_id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWards_id() {
		return wards_id;
	}
	public void setWards_id(int wards_id) {
		this.wards_id = wards_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
