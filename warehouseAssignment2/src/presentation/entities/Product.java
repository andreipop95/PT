package presentation.entities;

public class Product {
	
	private int productID;
	private String productName;
	private int quantity;
	private int price;
	
	public Product() {
		this.productID = 0;
		this.productName = "Andrei";
		this.quantity = 5;
		this.price = 10;
	}
	
	
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getProductPrice() {
		return price;
	}
	public void setProductPrice(int productPrice) {
		this.price = productPrice;
	}
	
}
