package org.chipmonk.smartcity.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="power_info")
public class Power
{
	public Power() {
		super();
	}
	public Power(int light_id, float power, Date date) {
		super();
		this.light_id = light_id;
		this.power = power;
		this.date = date;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int light_id;
	private float power;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss",timezone = "IST")
	private Date date;
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
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + light_id;
		result = prime * result + Float.floatToIntBits(power);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Power other = (Power) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (light_id != other.light_id)
			return false;
		if (Float.floatToIntBits(power) != Float.floatToIntBits(other.power))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Power [id=" + id + ", light_id=" + light_id + ", power=" + power + ", date=" + date + "]";
	}

}
