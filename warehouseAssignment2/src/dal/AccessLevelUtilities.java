package dal;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import reflection.ReflectionUtilities;

public class AccessLevelUtilities {
	private final int FIRST = 0;
	protected static Connection myConnection;
	protected static Statement myStatement;
	private final ReflectionUtilities reflection;
	
	public AccessLevelUtilities() {
		
		connectToDatabase();
		reflection = new ReflectionUtilities();
		
		try{
			myStatement = myConnection.createStatement();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void connectToDatabase() {
		
		System.out.println("Starting connection");
		
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
		
		try {
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "andrei", "andrei");
			System.out.println("Connected");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected ResultSet executeCommand(PreparedStatement querry){
		
		ResultSet rs = null;
		
		try {
			rs = querry.executeQuery();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could not execute the command");
		}
		
		return rs;
	}
	
	protected PreparedStatement createSelectQuerry(Class myClass) {
		
		String tableName = identifyTable(myClass);
		PreparedStatement querry = null;
		
		try {
			querry = myConnection.prepareStatement("select * from " + tableName);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return querry;
	}
	
	private String identifyTable(Class myClass) {
		
		if(myClass.getSimpleName().equals("Product")) {
			return "product";
		}
		else if(myClass.getSimpleName().equals("Customer")){
			return "customer";
		}
		else if(myClass.getSimpleName().equals("Order")) {
			return "order1";
		}
		else {
			return "order";
		}
	}
	
	private StringBuffer prepareAttributes(List<String> fields) {
		
		StringBuffer attributes = new StringBuffer("");
		
		int i;
		for(i = 1; i < fields.size() - 1; i++) { // don't want the id of the element to be inserted here;
			attributes.append(fields.get(i) + "=?, ");
		}
		
		attributes.append(fields.get(i) + "=? ");
		
		return attributes;
	}
	
	private StringBuffer prepareUpdateAttributes(List<String> fields) {
		
		StringBuffer attributes = new StringBuffer("");
		
		int i;
		for(i = 1; i < fields.size() - 1; i++) { // don't want the id of the element to be inserted here;
			attributes.append(fields.get(i) + ", ");
		}
		
		attributes.append(fields.get(i));
		
		return attributes;
	}
	
	private StringBuffer prepareUpdateValues(List<String> fields) { 
		
		StringBuffer values = new StringBuffer("");
		
		for(int i = 0; i < fields.size() - 2; i++) {
			values.append("?, ");
		}
		values.append("? ");
		return values;
	}
	
	protected PreparedStatement createUpdateQuerry(Class myClass) {
		
		List<String> fields = reflection. getTheFields(myClass);
		String tableName = identifyTable(myClass);
		
		PreparedStatement myStmt = null;
		
		StringBuffer attributes = prepareAttributes(fields);
		
		try {
			myStmt = myConnection.prepareStatement("update " + tableName + " set " + attributes + " where " + fields.get(0) + "=?");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return myStmt;
	}
	
	protected PreparedStatement createInsertQuerry(Class myClass) {
		
		List<String> fields = reflection.getTheFields(myClass);
		String tableName = identifyTable(myClass);
		StringBuffer attributes = prepareUpdateAttributes(fields);
		StringBuffer values = prepareUpdateValues(fields);
		// "insert into " + table + " ("+ fields + ") " + "values(" + values + ")";
		
		PreparedStatement querry = null;
		
		try {
			querry = myConnection.prepareStatement("insert into " + tableName +" (" + attributes + ") values (" + values + ")");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return querry;
	}
	
	protected PreparedStatement createDeleteQuerry(Class myClass) {
		List<String> fields = reflection.getTheFields(myClass);
		String tableName = identifyTable(myClass);
		PreparedStatement querry = null;
		try {
			querry = myConnection.prepareStatement("delete from " + tableName + " where " + fields.get(FIRST) + "=?");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(querry);
		
		return querry;
	
	}
}
	

/*
private Object createNewInstance(Class myClass) {
	
	Object o = null;
	
	try {
		Constructor<Object> ctor = myClass.getConstructor();
		o = ctor.newInstance();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return o;
}

protected ArrayList<Object> parseTheResult(Class myClass, ResultSet rs) {
	
	List<Object> parsedObjects = new ArrayList<Object>();
	Object o = createNewInstance(myClass);
	Field[] fields = o.getClass().getFields();
	
	try {
	
		while(rs.next()) {
			Object obj = createNewInstance(myClass);
			for(Field f : fields) {
				if(Modifier.isPrivate(f.getModifiers())) {
					
				}
			}
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Could not parse");
	}
}

*/






