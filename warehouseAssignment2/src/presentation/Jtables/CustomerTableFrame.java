package presentation.Jtables;

import java.util.List;

import presentation.entities.Customer;
import presentation.entities.Product;


public class CustomerTableFrame extends AbstractTableFrame{

	public CustomerTableFrame(List<?> items,String[] columnNames) {
		super(items, columnNames);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		
		Customer tempCustomer = (Customer) items.get(row);
		
		switch(col) {
			
		case 0: return tempCustomer.getCustId();
		case 1: return tempCustomer.getName();
		case 2: return tempCustomer.getPhone();
		case 3: return tempCustomer.getCity();
		default : return tempCustomer.getCustId();
		}
	}

}
