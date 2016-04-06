package presentation.entities;

public class Order {
	
	private int orderId;
	private int order_customerID;
	private int subtotal;
	private String status;
	
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getOrder_customerID() {
		return order_customerID;
	}
	public void setOrder_customerID(int order_customerID) {
		this.order_customerID = order_customerID;
	}

}
