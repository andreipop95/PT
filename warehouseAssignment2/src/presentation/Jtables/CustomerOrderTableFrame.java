package presentation.Jtables;

import java.util.List;

import presentation.entities.CustomerOrder;
import presentation.entities.OrderItem;
import presentation.entities.Product;

public class CustomerOrderTableFrame extends AbstractTableFrame{

	public CustomerOrderTableFrame(List<?> items,String[] columnNames) {
		super(items, columnNames);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		
		OrderItem item = (OrderItem) items.get(row);
		
		switch(col) {
			
		case 0: return item.getProduct().getProductName();
		case 1: return item.getQuantity();
		case 2: return item.getProduct().getProductPrice();
		default : return 0;
		
		}
		
	}

}
