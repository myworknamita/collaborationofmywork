package org.chipmonk.smartcity.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="light")
public class Light 
{
	
	public Light(int street_id, String name, Date last_on_time, Date last_off_time, float power_rating,
			Date manufactured_date, Date installed_date, int actualStatus, int requiredStatus, double longitude,
			double latitude) {
		super();
		this.street_id = street_id;
		this.name = name;
		this.last_on_time = last_on_time;
		this.last_off_time = last_off_time;
		this.power_rating = power_rating;
		this.manufactured_date = manufactured_date;
		this.installed_date = installed_date;
		this.actualStatus = actualStatus;
		this.requiredStatus = requiredStatus;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public int getRequiredStatus() {
		return requiredStatus;
	}
	public void setRequiredStatus(int requiredStatus) {
		this.requiredStatus = requiredStatus;
	}
	public Light() {
		super();
	}
	@Id
	@GeneratedValue
	@Column(name="id",nullable=false,unique=true)
	private int id;
	private int street_id;
	private String name;
	@Column(name="last_on_time")
	//@JsonRawValue
	private Date last_on_time;
	@Column(name="last_off_time")
	private Date last_off_time;
	private float power_rating;
	private Date manufactured_date;
	private Date installed_date;
	@Override
	public String toString() {
		return "Light [id=" + id + ", street_id=" + street_id + ", name=" + name + ", last_on_time=" + last_on_time
				+ ", last_off_time=" + last_off_time + ", power_rating=" + power_rating + ", manufactured_date="
				+ manufactured_date + ", installed_date=" + installed_date + ", actualStatus=" + actualStatus
				+ ", requiredStatus=" + requiredStatus + ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	@Column(name="actual_status")
	private int actualStatus;
	@Column(name="required_status")
	private int requiredStatus;
	private double longitude;
	private double latitude;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStreet_id() {
		return street_id;
	}
	public void setStreet_id(int street_id) {
		this.street_id = street_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss",timezone = "IST")
	public String getLast_on_time() {
		return last_on_time.toString();
	
	}
	public void setLast_on_time(Date last_on_time) {
		this.last_on_time = last_on_time;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss",timezone = "IST")
	public String getLast_off_time() {
		return last_off_time.toString();
		
	}
	public void setLast_off_time(Date last_off_time) {
		this.last_off_time = last_off_time;
	}
	public float getPower_rating() {
		return power_rating;
	}
	public void setPower_rating(float power_rating) {
		this.power_rating = power_rating;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss",timezone = "IST")
	public String getManufactured_date() {
		return manufactured_date.toString();
	
	}
	public void setManufactured_date(Date manufactured_date) {
		this.manufactured_date = manufactured_date;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss",timezone = "IST")
	public String getInstalled_date() {
		return installed_date.toString();
		
	}
	public void setInstalled_date(Date installed_date) {
		this.installed_date = installed_date;
	}
	public int getActualStatus() {
		return actualStatus;
	}
	public void setActualStatus(int status) {
		this.actualStatus = status;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
		
}
