package view;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import models.SpendingAccount;

public class ReflectionUtilities {
	
	public List<String> getTheFields(Class myClass) {
		
		Object o = null;
		
		try {
			Constructor<Object> ctor = myClass.getConstructor();
			o = ctor.newInstance();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		List<String> fields  = new ArrayList<String>();
		Field[] allFields;
		
		if(myClass.equals(SpendingAccount.class)) {
			 allFields = o.getClass().getSuperclass().getDeclaredFields();
		}
		else {
			 allFields = o.getClass().getDeclaredFields();
		}
	   
	    for (Field field : allFields) {
	        if (Modifier.isPrivate(field.getModifiers()) || Modifier.isProtected(field.getModifiers()) || Modifier.isPublic(field.getModifiers())) {
	            fields.add(field.getName());
	        }
	    }
	    return fields;
	}

}
