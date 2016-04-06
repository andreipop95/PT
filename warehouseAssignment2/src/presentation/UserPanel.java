package presentation;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import presentation.Jtables.CustomerTableFrame;
import presentation.Jtables.ProductTableFrame;
import presentation.entities.Customer;
import presentation.entities.CustomerOrder;
import presentation.entities.Order;
import presentation.entities.OrderItem;
import presentation.entities.Product;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.synth.SynthSpinnerUI;

import bll.CommandManager;
import dal.AccessLevelUtilities;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserPanel extends JPanel implements ActionListener {
	private final PresentationLevelUtilities presentationUtility;
	private JTable custTable;
	private JTable prodTable;
	private JTable orderTable;
	private JButton btnSetCustomer, btnAddProduct, btnDeleteProduct, btnSubmitOrder;
	private JLabel activeCustomerLabel, subtotalLabel;
	private List<Product> products = new ArrayList<Product>();
	private List<Customer> customers = new ArrayList<Customer>();
	private CustomerOrder order = new CustomerOrder(); // A customer can have only one order active at the time
	private Product selectedProduct;
	/**
	 * Create the panel.
	 */
	public UserPanel(PresentationLevelUtilities presentationUtility) {
		this.presentationUtility = presentationUtility;
		setLayout(new MigLayout("", "[400px:n:400px,grow][400px:n:400px,grow]", "[::200px,grow][][::230px,grow][][]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Customers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, "cell 0 0,grow");
		
		//TableFrame modelProd = new TableFrame(customerFields);
		custTable = new JTable();
		scrollPane_1.setViewportView(custTable);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Products", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_1, "cell 1 0,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_1.add(scrollPane_2, "cell 0 0,grow");
		
		prodTable = new JTable();
		scrollPane_2.setViewportView(prodTable);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, "cell 0 1 2 1,grow");
		
		JLabel lblActiveCustomer = new JLabel("  Active Customer: ");
		lblActiveCustomer.setFont(new Font("Arial", Font.PLAIN, 14));
		lblActiveCustomer.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_3.add(lblActiveCustomer);
		
		activeCustomerLabel = new JLabel("");
		panel_3.add(activeCustomerLabel);
		
		JLabel lblNewLabel = new JLabel("Subtotal:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_3.add(lblNewLabel);
		
		subtotalLabel = new JLabel("");
		subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_3.add(subtotalLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "My order", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_2, "cell 0 2 2 1,grow");
		panel_2.setLayout(new MigLayout("", "[400px:n:400px,grow][400px:n:400px,grow]", "[::230px,grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, "cell 0 0 2 1,grow");
		
		orderTable = new JTable();
		scrollPane.setViewportView(orderTable);
		
		JPanel panel_4 = new JPanel();
		add(panel_4, "cell 0 3 2 1,grow");
		
		btnSetCustomer = new JButton("Set Customer");
		btnSetCustomer.addActionListener(this);
		panel_4.add(btnSetCustomer);
		
		btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(this);
		panel_4.add(btnAddProduct);
		
		btnDeleteProduct = new JButton("Delete Product");
		btnDeleteProduct.addActionListener(this);
		panel_4.add(btnDeleteProduct);
		
		JPanel panel_5 = new JPanel();
		add(panel_5, "cell 0 4 2 1,grow");
		
		btnSubmitOrder = new JButton("Submit Order");
		btnSubmitOrder.addActionListener(this);
		panel_5.add(btnSubmitOrder);
		
		presentationUtility.updateProductTable(prodTable);
		presentationUtility.updateCustomerTable(custTable);
		
		updateLists();
	}
	
	public void updateLists() {
		products = presentationUtility.getTheProducts();
		customers = presentationUtility.getTheCustomers();
	}
	
	protected JTable getProductTable(){
		return prodTable;
	}
	
	protected JTable getOrderTable(){
		return orderTable;
	}
	
	protected CustomerOrder getOrder() {
		return order;
	}
	
	protected JTable getCustomerTable() {
		return custTable;
	}
	
	private void setSelectedCustomer() {
		
		int row =custTable.getSelectedRow();
		
		if (row < 0) {
			JOptionPane.showMessageDialog(UserPanel.this, "You must select a customer", "Error", JOptionPane.ERROR_MESSAGE);				
			return;
		}
		
		Customer c = customers.get(row);
		activeCustomerLabel.setText(c.getName());
		order.setOderCustomerId(c.getCustId());
		order.setOrderCustomerName(c.getName());
		
	}
	
	private void addSelectedProduct() {
		
		int row = prodTable.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(UserPanel.this, "You must select a product", "Error", JOptionPane.ERROR_MESSAGE);				
			return;
		}
		
		Product p = products.get(row);
		
		AddProductForm form = new AddProductForm(p, presentationUtility, this);
		
		form.setLocationRelativeTo(this);
		form.setTitle("Add product");
		form.setVisible(true);
		
	}
	
	private void deleteSelectedProduct() {
		
		int row = orderTable.getSelectedRow();
		if(row < 0) {
			JOptionPane.showMessageDialog(UserPanel.this, "Select a product from the order table", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	
		List<OrderItem> items = order.getItems();
		selectedProduct = items.get(row).getProduct();
		selectedProduct.setQuantity(selectedProduct.getQuantity() + items.get(row).getQuantity());
		items.remove(row);
		presentationUtility.updateCustomerOrderTable(orderTable, order);
	
	}
	
	public void refreshSubtotal() {
		subtotalLabel.setText(Integer.toString(order.getSubtotal()));
	}
	
	private void parseTheCustomerOrder() {
		presentationUtility.insertOrder(order);
	}
	
	private void resetTheOrderDetails() {
		subtotalLabel.setText("");
		activeCustomerLabel.setText("");
		order = new CustomerOrder();
		presentationUtility.updateCustomerOrderTable(orderTable, order);
	}
	
	private boolean hasSelectedCustomer() {
		if(activeCustomerLabel.getText().equals("")) {
			return false;
		}
		else 
			return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnSetCustomer) {
			 setSelectedCustomer();
		}
		else if(e.getSource() == btnAddProduct) {
			addSelectedProduct();
		}
		else if(e.getSource() == btnDeleteProduct) {
			deleteSelectedProduct();
			presentationUtility.updateProduct(selectedProduct);
			presentationUtility.updateProductTable(prodTable);
			presentationUtility.updateAdminTables();
			this.validate();
		}
		else if(e.getSource() == btnSubmitOrder) {
			if(hasSelectedCustomer()) {
				parseTheCustomerOrder();
				new DocumentPrinter(order);
				resetTheOrderDetails();
				presentationUtility.updateAdminTables();
				JOptionPane.showMessageDialog(this, "Your order has been submited and the bill was printed");
			}
			else {
				JOptionPane.showMessageDialog(this, "Please select a customer!!");
			}
		}
	}
}
	
	
