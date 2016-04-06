package presentation.Jtables;

import java.util.List;

import presentation.entities.Order;

public class OrderTableFrame extends AbstractTableFrame{
	
	public OrderTableFrame(List<?> items,String[] columnNames) {
		super(items, columnNames);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		
		Order tempOrder = (Order) items.get(row);
		
		switch(col) {
			
		case 0: return tempOrder.getOrderId();
		case 1: return tempOrder.getOrder_customerID();
		case 2: return tempOrder.getSubtotal();
		case 3: return tempOrder.getStatus();
		default : return tempOrder.getOrderId();
		}
	}
	
}
