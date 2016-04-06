package bll;

import java.util.ArrayList;
import java.util.List;

import dal.AccessLevelUtilities;
import dal.CustomerDal;
import dal.OrderDal;
import dal.ProductDal;
import presentation.entities.Customer;
import presentation.entities.CustomerOrder;
import presentation.entities.Order;
import presentation.entities.Product;

public class CommandManager {
	
	private final AccessLevelUtilities accessUtility = new AccessLevelUtilities();
	
	public List<Product> getAllProducts() {
		
		ProductDal accessLayer = new ProductDal(accessUtility);
		return accessLayer.getTheProducts();
	}
	
	public List<Customer> getAllCustomers() {
		
		CustomerDal accessLayer = new CustomerDal(accessUtility);
		return accessLayer.getTheCustomers();
	}
	
	public List<Order> getAllOrders() {
		
		OrderDal accessLayer = new OrderDal(accessUtility);
		return accessLayer.getAllOrders();
	}
	
	
	public void updateProduct(Product p) {
		
		ProductDal accessLayer = new ProductDal(accessUtility);
		accessLayer.updateProduct(p);
		
	}
	
	public boolean isValidProductOrder(Product prod, int quantity) {
		
		List<Product> products = getAllProducts();
		
		for(Product p : products) {
			if(p.getProductID() == prod.getProductID()) {
				if(quantity <= prod.getQuantity()) {
					return true;
				}
				else
					return false;
			}
		}
		return false;
	}
	
	public void insertOrder(CustomerOrder o) {
		
		OrderDal accessLayer = new OrderDal(accessUtility);
		accessLayer.insertOrder(o);
	}
	
	
	public void insertProduct(Product p) {
		ProductDal accessLayer = new ProductDal(accessUtility);
		accessLayer.insertProduct(p);
	}
	
	public void deleteProduct(Product p) {
		ProductDal accessLayer = new ProductDal(accessUtility);
		accessLayer.deleteProduct(p);
	}
	
	public void insertCustomer(Customer c) {
		CustomerDal accessLayer = new CustomerDal(accessUtility);
		accessLayer.insertCustomer(c);
	}
	
	public void updateCustomer(Customer c) {
		CustomerDal accessLayer = new CustomerDal(accessUtility);
		accessLayer.updateCustomer(c);
	}
	
	public void deleteCustomer(Customer c) {
		CustomerDal accessLayer = new CustomerDal(accessUtility);
		accessLayer.deleteCustomer(c);
	}
	
	public void updateOrder(Order o) {
		OrderDal accessLayer = new OrderDal(accessUtility);
		accessLayer.updateOrder(o);
	}
	
	/*
	public List<Customer> getAllOrders() {
		
		ProductDal accessLayer = new OrderDal();
		return accessLayer.getTheProducts();
	}
	
	*/
	
}
