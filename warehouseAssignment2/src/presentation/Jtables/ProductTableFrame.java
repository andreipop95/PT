package presentation.Jtables;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import presentation.entities.Product;

public class ProductTableFrame extends AbstractTableFrame{


	public ProductTableFrame(List<Product> items,String[] columnNames) {
		super(items, columnNames);
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		Product tempProd = (Product) items.get(row);
		
		switch(col) {
			
		case 0: return tempProd.getProductID();
		case 1: return tempProd.getProductName();
		case 3: return tempProd.getProductPrice();
		case 2: return tempProd.getQuantity();
		default : return tempProd.getProductID();
		
		}
		
	}

}
