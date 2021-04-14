package org.mql.java.models;

import org.mql.java.annotations.GenerateData;
import org.mql.java.annotations.GenerateDouble;
import org.mql.java.annotations.GenerateInteger;
import org.mql.java.annotations.GenerateString;
import org.mql.java.annotations.XMLField;
import org.mql.java.annotations.XMLObject;

@GenerateData(count = 3)
@XMLObject
public class Document {
	@GenerateInteger(min = 1, max = 3000)
	@XMLField(name = "id", type = "element")
	private int id;
	@GenerateString(min = 'a', max = 'n', length = 5)
	@XMLField(name = "title", type = "element")
	private String title;
	@GenerateDouble(min = 1.0, max = 999.99)
	@XMLField(name = "price", type = "element")
	private double price;
	
	public Document() {
	}

	public Document(int id, String title, double price) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", title=" + title + ", price=" + price + "]";
	}
}
