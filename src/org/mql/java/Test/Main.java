package org.mql.java.Test;

import java.util.List;
import java.util.Vector;

import org.mql.java.models.Date;
import org.mql.java.models.Document;
import org.mql.java.models.Point;

public class Main {

	public Main() {
		exp01();
	}
	
	void exp01() {
		Point object1 = new Point();
		Document object2 = new Document();
		Date object3 = new Date();
		
		List<Object> objects = new Vector<Object>();
		objects.add(object1);
		objects.add(object2);
		objects.add(object3);
		
		ObjectXmlGenerator xmlGenerator = new ObjectXmlGenerator(objects);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
