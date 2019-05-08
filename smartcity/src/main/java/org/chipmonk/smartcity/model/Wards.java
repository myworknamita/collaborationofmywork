package org.chipmonk.smartcity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wards")
public class Wards {
	
 public Wards() {
		super();
	}
 @Id
 @GeneratedValue
 @Column(name="id",nullable=false,unique=true)
private int id;
 private String name;
 private int smartcity_id;
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
public int getSmartcity_id() {
	return smartcity_id;
}
public void setSmartcity_id(int smartcity_id) {
	this.smartcity_id = smartcity_id;
}
 
 
}
