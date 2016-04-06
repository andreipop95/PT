package presentation.entities;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrder {
	
	private int oderCustomerId;
	private String orderCustomerName;
	
	public String getOrderCustomerName() {
		return orderCustomerName;
	}

	public void setOrderCustomerName(String orderCustomerName) {
		this.orderCustomerName = orderCustomerName;
	}

	List<OrderItem> items = new ArrayList<OrderItem>();
	private String status;
	private int subtotal = 0;
	
	public CustomerOrder() {
		status = new String("Processing");
	}
	
	public String getOrderStatus() {
		return status;
	}
	public void setOrderStatus(String status) {
		this.status = status;
	}
	
	public int getOderCustomerId() {
		return oderCustomerId;
	}
	public void setOderCustomerId(int oderCustomerId) {
		this.oderCustomerId = oderCustomerId;
	}
	
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	
	public void addItem (OrderItem item) {
		subtotal += item.getProduct().getProductPrice() * item.getQuantity();
		items.add(item);
	}

}
