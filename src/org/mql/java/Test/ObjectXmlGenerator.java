package org.mql.java.Test;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.mql.java.annotations.GenerateData;
import org.mql.java.annotations.GenerateDouble;
import org.mql.java.annotations.GenerateInteger;
import org.mql.java.annotations.GenerateString;
import org.mql.java.annotations.XMLField;
import org.mql.java.annotations.XMLObject;
import org.mql.java.models.Date;
import org.mql.java.models.Document;
import org.mql.java.models.Point;

public class ObjectXmlGenerator {
	private List<Object> inputs;
	public List<Object> generatedData;
	Map<String, String> xmlObjectProperties;
	Map<String, String> xmlObjectFields;
	
	public ObjectXmlGenerator(List<Object> objects) {
		this.inputs = objects;
		generetaXml(inputs);
	}

	public void generetaXml(List<Object> inputs) {
		for (Object object : inputs) {
			Class<?> clz = object.getClass();
			String objectClassName = clz.getSimpleName().toLowerCase();
			GenerateData gd = null;
			int objectCount = 0;
			XMLObject xmlObj = null;
			
			if(isRecognisedObject(objectClassName)) {
				gd = clz.getAnnotation(GenerateData.class);
				
				if(gd != null) {
					objectCount = gd.count();
					generateData(clz, objectCount);
				}
			}
		}
	}
	
	public void generateXml() {
		PrintWriter printer = null;
		
		try {

			printer = new PrintWriter("resources/" + xmlObjectProperties.get("fileName"));
			
			//start root tag without \t
			printer.println("<" + xmlObjectProperties.get("root") + ">");
			
			
			// print childs
			for(int i = 0; i < generatedData.size(); i++) {
				boolean isOpeningTagElementClosed = false;
				
				printer.print("\t<" + xmlObjectProperties.get("elementName"));
				Field fields[] = generatedData.get(i).getClass().getDeclaredFields();
				String fieldName = "";
				String fieldType = "";
				for(Field f : fields ) {
					XMLField xmlFieldAnnotation = f.getDeclaredAnnotation(XMLField.class);
					if(xmlFieldAnnotation != null) {
						fieldName = xmlFieldAnnotation.name();
						fieldType = xmlFieldAnnotation.type();
						
						if(fieldType.equals("element")) {
							if(!isOpeningTagElementClosed) {
								printer.println(">");
								isOpeningTagElementClosed = true;
							}
						
							printer.print("\t\t<" + fieldName + ">");
							printer.print(getFieldValue(f, generatedData.get(i)));
							printer.println("</" + fieldName + ">");
							
						}
						else if(fieldType.equals("attribute")) {
							Object obj = getFieldValue(f, generatedData.get(i));
							printer.print(" " + fieldName + "=\"" + obj + "\"");
						}
					}
				}
				if(fieldType.equals("element")) {
					printer.println("\t</" + xmlObjectProperties.get("elementName") + ">");
				}
				else if(fieldType.equals("attribute")) {
					printer.println(">");
				}
			}
			
			// end root tag without \t
			printer.print("</" + xmlObjectProperties.get("root") + ">");
			
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			printer.close();
		}
		
		printer.close();
	}
	
	public Object getFieldValue(Field f, Object context) {
		Object obj = null;
		try {
			f.setAccessible(true);
			obj = f.get(context);
			f.setAccessible(false);
		} catch (Exception e) {
			System.out.println("Error r  : " + e.getMessage());
		} 
		return obj;
	}
	
	public void generateData(Class<?> clz, int objectCount) {
		String className = clz.getSimpleName().toLowerCase();
		
		GenerateInteger giAnnotation = null;
		GenerateDouble gdAnnotation = null;
		GenerateString gsAnnotation = null;
		
		
		if(objectCount > 0) {
			
			generatedData = new Vector<Object>();
			
			if(className.equals("point")) {
				Field[] fields =  clz.getDeclaredFields();
				int minPointX = 0, maxPointX = 0, minPointY = 0, maxPointY = 0;
				
				for(Field f : fields) {
					giAnnotation = f.getDeclaredAnnotation(GenerateInteger.class);
					if(giAnnotation != null) {
						if(f.getName().equals("x")) {
							minPointX = giAnnotation.min();
							maxPointX = giAnnotation.max();
						}
						else if(f.getName().equals("y")) {
							minPointY = giAnnotation.min();
							maxPointY = giAnnotation.max();
						}
					}
				}
				
				for(int i = 0; i < objectCount; i++) {
					Point p = new Point();
					p.setX(generateRandomInteger(minPointX, maxPointX));
					p.setY(generateRandomInteger(minPointY, maxPointY));
					generatedData.add(p);	
				}
				
				if(isXMLObject(clz)) {
					generateXml();
				}
			}
			else if(className.equals("document")) {
				Field[] fields =  clz.getDeclaredFields();
				int minId = 0, maxId = 0, stringLength = 0;
				double minPrice = 0.0, maxPrice = 0.0;
				char minChar = 'a', maxChar = 'z';
				String alphabet = "abcdefghijklmnopqrstuvwxyz";
				
				
				for(Field f : fields) {
					giAnnotation = f.getDeclaredAnnotation(GenerateInteger.class);
					gdAnnotation = f.getDeclaredAnnotation(GenerateDouble.class);
					gsAnnotation = f.getDeclaredAnnotation(GenerateString.class);
					
					
					if(giAnnotation != null || gdAnnotation != null || gsAnnotation != null) {
						if(f.getName().equals("id")) {
							if(giAnnotation != null) {
								minId = giAnnotation.min();
								maxId = giAnnotation.max();
							}
						}
						else if(f.getName().equals("title")) {
							if(gsAnnotation != null) {
								minChar = gsAnnotation.min();
								maxChar = gsAnnotation.max();
								stringLength = gsAnnotation.length();
							}
						}
						else if(f.getName().equals("price")) {
							if(gdAnnotation != null) {
								minPrice = gdAnnotation.min();
								maxPrice = gdAnnotation.max();
							}
						}
					}
				}
				
				for(int i = 0; i < objectCount; i++) {
					Document d = new Document();
					d.setId(generateRandomInteger(minId, maxId));
					d.setTitle(generateRandomString(minChar, maxChar, stringLength));
					d.setPrice(Math.ceil(generateRandomDouble(minPrice, maxPrice)));
					generatedData.add(d);
				}
				
				if(isXMLObject(clz)) {
					generateXml();
				}
			}
			else if(className.equals("date")) {
				Field[] fields =  clz.getDeclaredFields();
				int minDay = 0, maxDay = 0, minMonth = 0, maxMonth = 0, minYear = 0, maxYear = 0;
				
				for(Field f : fields) {
					giAnnotation = f.getDeclaredAnnotation(GenerateInteger.class);
					if(giAnnotation != null) {
						if(f.getName().equals("day")) {
							minDay = giAnnotation.min();
							maxDay = giAnnotation.max();
						}
						else if(f.getName().equals("month")) {
							minMonth = giAnnotation.min();
							maxMonth = giAnnotation.max();
						}
						else if(f.getName().equals("year")) {
							minYear = giAnnotation.min();
							maxYear = giAnnotation.max();
						}
					}
				}
				
				for(int i = 0; i < objectCount; i++) {
					Date d = new Date();
					d.setDay(generateRandomInteger(minDay, maxDay));
					d.setMonth(generateRandomInteger(minMonth, maxMonth));
					d.setYear(generateRandomInteger(minYear, maxYear));
					generatedData.add(d);
				}
				
				if(isXMLObject(clz)) {
					generateXml();
				}
				
			}
		}
		
	}
	
	private int generateRandomInteger(int min, int max) {
		Random rn = new Random();
		return (int) (min + rn.nextInt(max - min + 1));
	}
	
	private double generateRandomDouble(double min, double max) {
		double random = new Random().nextDouble();
		return (double) (min + (random * (max - min)));
	}
	
	private String generateRandomString(char min, char max, int stringLength) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String slice = alphabet.substring(alphabet.indexOf((int)min), alphabet.indexOf((int)max) + 1);
		int minCharIndex = 0, maxCharIndex = slice.length();
		
		StringBuffer sb = new StringBuffer("");
		
		for(int i = 0; i < stringLength; i++) {
			sb.append(alphabet.charAt(generateRandomInteger(minCharIndex, maxCharIndex)));
		}
		
		return sb.toString();
	}

	public boolean isRecognisedObject(String ClassName) {
		String set[] = {"point", "document", "date"};
		for(int i = 0; i < set.length; i++) {
			if(set[i].equals(ClassName)) return true;
		}
		return false;
	}
	
	public boolean isXMLObject(Class<?> clz) {
		XMLObject xmlObjectAnnotation = clz.getDeclaredAnnotation(XMLObject.class);
		
		if(xmlObjectAnnotation != null) {
			xmlObjectProperties = new HashMap<String, String>();
			
			String root = xmlObjectAnnotation.root();
			String elementName = xmlObjectAnnotation.element();
			String fileName = xmlObjectAnnotation.file();
			
			if(root.equals("")) {
				root = clz.getSimpleName().toLowerCase() + 's';
				xmlObjectProperties.put("root", root);
			}
			if(elementName.equals("")) {
				elementName = clz.getSimpleName().toLowerCase();
				xmlObjectProperties.put("elementName", elementName);
			}
			if(fileName.equals("")) {
				fileName = root + ".xml";
				xmlObjectProperties.put("fileName", fileName);
			}
			
			return true;
		}
		
		return false;
	}
	
}
