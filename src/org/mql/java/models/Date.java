package org.mql.java.models;

import org.mql.java.annotations.GenerateData;
import org.mql.java.annotations.GenerateInteger;
import org.mql.java.annotations.XMLField;
import org.mql.java.annotations.XMLObject;

@GenerateData(count = 3)
@XMLObject
public class Date {
	@GenerateInteger(min = 1, max = 31)
	@XMLField(name = "day", type = "element")
	private int day;
	@GenerateInteger(min = 1, max = 12)
	@XMLField(name = "month", type = "element")
	private int month;
	@GenerateInteger(min = 2000, max = 2021)
	@XMLField(name = "year", type = "element")
	private int year;
	
	public Date() {
	}

	public Date(int day, int month, int year) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return day + "/" + month + "/" + year;
	}
}