package presentation;

import java.io.PrintWriter;
import java.util.List;

import presentation.entities.CustomerOrder;
import presentation.entities.OrderItem;

public class DocumentPrinter {
	
	private CustomerOrder order;
	private static int billNumber = 0;
	private PrintWriter writer;
	
	public DocumentPrinter(CustomerOrder order) {
		this.order = order;
		printTheDetails();
		billNumber += 1;
	}
	
	private void printTheProducts() {
		
		List<OrderItem> items = order.getItems();
		
		for(OrderItem it : items) {
			writer.println(it.getProduct().getProductName() + " price: " + it.getProduct().getProductPrice() + " nr items:  " + it.getQuantity());
		}
		
	}
	
	private void printTheDetails() {
		
		try {
			writer = new PrintWriter("bill" + billNumber +".txt", "UTF-8");
			writer.println("The name of the customer: " + order.getOrderCustomerName());
			printTheProducts();
			writer.println("The subtotal is: " + order.getSubtotal());
			writer.println("Thank you for your order");
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
