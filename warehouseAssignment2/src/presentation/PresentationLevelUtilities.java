package presentation;

import java.util.List;

import javax.swing.JTable;

import bll.CommandManager;
import presentation.Jtables.CustomerTableFrame;
import presentation.Jtables.OrderTableFrame;
import presentation.Jtables.CustomerOrderTableFrame;
import presentation.Jtables.ProductTableFrame;
import presentation.entities.Customer;
import presentation.entities.CustomerOrder;
import presentation.entities.Order;
import presentation.entities.OrderItem;
import presentation.entities.Product;
import reflection.ReflectionUtilities;

public class PresentationLevelUtilities {
	
	private final CommandManager commandManager;
	private final ReflectionUtilities reflection;
	private String[] prodFields;
	private String[] customerFields;
	private String[] customerOrderFields;
	private String[] orderFields;
	private  UserPanel userPanel;
	private  AdminPanel adminPanel;
	
	public PresentationLevelUtilities() {
		
		commandManager = new CommandManager();
		reflection = new ReflectionUtilities();

		getProdFields();
		getCustFields();
		getCustomerOrderFields();
		getOrderFields();
	}
	
	public void setUserPanel(UserPanel userPanel) {
		this.userPanel = userPanel;
	}
	
	public void setAdminPanel(AdminPanel adminPanel) {
		this.adminPanel = adminPanel;
	}
	
	protected void updateUserTables() {
		this.updateCustomerTable(userPanel.getCustomerTable());
		this.updateProductTable(userPanel.getProductTable());
		userPanel.updateLists();
	}
	
	protected void updateAdminTables() {
		this.updateOrderTable(adminPanel.getOrderTable());
		this.updateCustomerTable(adminPanel.getCusomerTable());
		this.updateProductTable(adminPanel.getProductTable());
	}
	
	protected void getProdFields() {
		
		List<String> fields = reflection.getTheFields(Product.class);
		prodFields = (String[]) fields.toArray(new String[fields.size()]);
		
	}
	
	protected void getCustFields() {
		
		List<String> fields = reflection.getTheFields(Customer.class);
		customerFields = (String[]) fields.toArray(new String[fields.size()]);
	
	}
	
	protected void getOrderFields() {
		
		List<String> fields = reflection.getTheFields(Order.class);
		orderFields = (String[]) fields.toArray(new String[fields.size()]);
		
		for(String f : fields) {
			System.out.println(f);
		}
	
	}
	
	protected void getCustomerOrderFields() {
		customerOrderFields = new String[3];
		
		customerOrderFields[0] = prodFields[1];
		customerOrderFields[1] = new String("quantity");
		customerOrderFields[2] = new String("price/unit");
		
	}

	
	protected List<Customer> getTheCustomers() {
		return commandManager.getAllCustomers();
	}
	
	protected List<Product> getTheProducts() {
		return commandManager.getAllProducts();
	}
	
	protected List<Order> getTheOrders() {
		return commandManager.getAllOrders();
	}
	
	protected void updateProductTable(JTable prodTable) {
		
		List<Product> products = commandManager.getAllProducts();

		ProductTableFrame t = new ProductTableFrame(products, prodFields);
		prodTable.setModel(t);
	}
	
	protected void updateCustomerTable(JTable custTable) {
		
		List<Customer>customers = commandManager.getAllCustomers();
		
		CustomerTableFrame t = new CustomerTableFrame(customers, customerFields);
		custTable.setModel(t);
	}
	
	protected void updateCustomerOrderTable(JTable customerOrderTable, CustomerOrder order) {
		
		List<OrderItem> items = order.getItems();
		
		CustomerOrderTableFrame t = new CustomerOrderTableFrame(items, customerOrderFields);
		customerOrderTable.setModel(t);
		
	}
	
	protected void updateOrderTable(JTable orderTable) {
		
		List<Order> orders = commandManager.getAllOrders();
		
		OrderTableFrame t = new OrderTableFrame(orders, orderFields);
		orderTable.setModel(t);
	}
	
	protected boolean isValidProductOrder(Product prod, int quantity){
		return commandManager.isValidProductOrder(prod, quantity);
	}
	
	protected void updateProduct(Product p) {
		commandManager.updateProduct(p);
	}
	
	protected void insertOrder(CustomerOrder o) {
		commandManager.insertOrder(o);
	}
	
	protected void insertProduct(Product p) {
		commandManager.insertProduct(p);
	}
	
	protected void deleteProduct(Product p) {
		commandManager.deleteProduct(p);
	}
	
	protected void insertCustomer(Customer c) {
		commandManager.insertCustomer(c);
	}
	
	protected void updateCustomer(Customer c) {
		commandManager.updateCustomer(c);
	}
	
	protected void deleteCustomer(Customer c) {
		commandManager.deleteCustomer(c);
	}
	
	protected void updateOrder(Order o) {
		commandManager.updateOrder(o);
	}
	
}
