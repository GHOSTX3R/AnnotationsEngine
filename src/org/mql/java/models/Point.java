package org.mql.java.models;

import org.mql.java.annotations.GenerateData;
import org.mql.java.annotations.GenerateInteger;
import org.mql.java.annotations.XMLField;
import org.mql.java.annotations.XMLObject;

@GenerateData(count = 2)
@XMLObject(root = "objects")
public class Point {
	@GenerateInteger(min = 0, max = 1000)
	@XMLField(name = "pointX", type = "attribute")
	private int x;
	@GenerateInteger(min = 0, max = 2000)
	@XMLField(name = "pointY", type = "attribute")
	private int y;
	
	public Point() {
	}

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}
