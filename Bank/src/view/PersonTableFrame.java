package view;

import java.util.List;

import models.Person;



public class PersonTableFrame extends AbstractTableFrame{
	
	public PersonTableFrame(List<?> items, String[] columnNames) {
		super(items, columnNames);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		
		Person tempPerson = (Person) items.get(row);
		
		switch(col) {
			case 1: return tempPerson.getPersonId();
			case 0: return tempPerson.getName();
			default : return tempPerson.getPersonId();
		}
	}
}
