package org.chipmonk.smartcity.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="alert")
public class Alert {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="light_id")
	private int lightId;
	@Column(name="street_id")
	private int streetId;
	private String status;
	private String alert;
	@Column(name="active")
	private int active;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss",timezone = "IST")
	private Date date;
	public Alert(int lightId, int streetId, String status, String alert, int active, Date date) {
		super();
		this.lightId = lightId;
		this.streetId = streetId;
		this.status = status;
		this.alert = alert;
		this.active = active;
		this.date = date;
	}
	public Alert(int id, int lightId, int streetId, String status, String alert, int active, Date date) {
		super();
		this.id = id;
		this.lightId = lightId;
		this.streetId = streetId;
		this.status = status;
		this.alert = alert;
		this.active = active;
		this.date = date;
	}
	public Alert() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLightId() {
		return lightId;
	}
	public void setLightId(int lightId) {
		this.lightId = lightId;
	}
	public int getStreetId() {
		return streetId;
	}
	public void setStreetId(int streetId) {
		this.streetId = streetId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}
