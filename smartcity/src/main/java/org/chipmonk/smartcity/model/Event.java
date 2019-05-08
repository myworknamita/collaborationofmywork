package org.chipmonk.smartcity.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="event")
public class Event
{
	public Event() {
		super();
	}
	public Event(int light_id, Date time, String action) {
		super();
		this.light_id = light_id;
		this.time = time;
		this.action = action;
	}
	@Id
	@GeneratedValue
	@Column(name="id",nullable=false,unique=true)
	private int id;
	private int light_id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss",timezone = "IST")
	private Date time;
	private String action;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLight_id() {
		return light_id;
	}
	public void setLight_id(int light_id) {
		this.light_id = light_id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
