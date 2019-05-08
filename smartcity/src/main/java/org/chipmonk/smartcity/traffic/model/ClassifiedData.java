package org.chipmonk.smartcity.traffic.model;

import java.util.List;

public class ClassifiedData {
private List<TrainingSet> lowList;
private List<TrainingSet> highList;
private List<TrainingSet> midList;
public List<TrainingSet> getLowList() {
	return lowList;
}
public void setLowList(List<TrainingSet> lowList) {
	this.lowList = lowList;
}
public List<TrainingSet> getHighList() {
	return highList;
}
public void setHighList(List<TrainingSet> highList) {
	this.highList = highList;
}
public List<TrainingSet> getMidList() {
	return midList;
}
public void setMidList(List<TrainingSet> midList) {
	this.midList = midList;
}
}
