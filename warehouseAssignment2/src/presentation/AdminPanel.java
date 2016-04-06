package presentation;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import presentation.entities.Customer;
import presentation.entities.Order;
import presentation.entities.Product;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import dal.AccessLevelUtilities;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends JPanel implements ActionListener{
	
	private PresentationLevelUtilities presentationUtility;
	private JTable prodTable;
	private JTable custTable;
	private JButton btnDeleteCustomer;
	private JButton btnUpdateCustomer, btnCreateCustomer, btnDeleteProduct, btnUpdateProduct, btnCreateProduct;
	private JPanel panel_2;
	private JButton btnUpdateOrderStatus;
	private JPanel panel_3;
	private JScrollPane scrollPane;
	private JPanel panel_4;
	private JScrollPane scrollPane_1;
	private JTable orderTable;
	private JPanel panel_5;
	private JScrollPane scrollPane_2;

	public AdminPanel(PresentationLevelUtilities presentationUtility) {
		
		this.presentationUtility = presentationUtility;
		setLayout(new MigLayout("", "[grow][grow]", "[200px:n:200px,grow][][grow][]"));
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Customers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_3, "cell 0 0,grow");
		panel_3.setLayout(new MigLayout("", "[grow]", "[grow]"));
		scrollPane = new JScrollPane();
		panel_3.add(scrollPane, "cell 0 0,grow");
		custTable = new JTable();
		scrollPane.setViewportView(custTable);
		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Products", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_4, "cell 1 0,grow");
		panel_4.setLayout(new MigLayout("", "[grow]", "[grow]"));
		scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1, "cell 0 0,grow");
		prodTable = new JTable();
		scrollPane_1.setViewportView(prodTable);
		JPanel panel = new JPanel();
		add(panel, "cell 0 1,grow");
		btnDeleteCustomer = new JButton("Delete Customer");
		btnDeleteCustomer.addActionListener(this);
		panel.add(btnDeleteCustomer);
		btnUpdateCustomer = new JButton("Update Customer");
		btnUpdateCustomer.addActionListener(this);
		panel.add(btnUpdateCustomer);
		btnCreateCustomer = new JButton("Add Customer");
		btnCreateCustomer.addActionListener(this);
		panel.add(btnCreateCustomer);
		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 1 1,grow");
		btnDeleteProduct = new JButton("Delete Product");
		btnDeleteProduct.addActionListener(this);
		panel_1.add(btnDeleteProduct);
		btnUpdateProduct = new JButton("Update product");
		btnUpdateProduct.addActionListener(this);
		panel_1.add(btnUpdateProduct);
		btnCreateProduct = new JButton("Add Product");
		btnCreateProduct.addActionListener(this);
		panel_1.add(btnCreateProduct);
		
		panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Orders", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_5, "cell 0 2 2 1,grow");
		panel_5.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		scrollPane_2 = new JScrollPane();
		panel_5.add(scrollPane_2, "cell 0 0,grow");
		
		orderTable = new JTable();
		scrollPane_2.setViewportView(orderTable);
		
		panel_2 = new JPanel();
		add(panel_2, "cell 0 3 2 1,grow");
		
		btnUpdateOrderStatus = new JButton("Update Order Status");
		btnUpdateOrderStatus.addActionListener(this);
		panel_2.add(btnUpdateOrderStatus);
		
		presentationUtility.updateProductTable(prodTable);
		presentationUtility.updateCustomerTable(custTable);
		presentationUtility.updateOrderTable(orderTable);
	}
	
	public JTable getOrderTable() {
		return orderTable;
	}
	
	public JTable getCusomerTable() {
		return custTable;
	}
	
	public JTable getProductTable() {
		return prodTable;
	}
	
	private Product getSelectedProduct() {
		
		int row = prodTable.getSelectedRow();
		if(row < 0) {
			JOptionPane.showMessageDialog(this, "Select a product from the product table", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else {
			Product p = presentationUtility.getTheProducts().get(row);
			return p;
		}
	}
	
	private Customer getSelectedCustomer() {
		int row = custTable.getSelectedRow();
		
		if(row < 0) {
			JOptionPane.showMessageDialog(this, "Select a customer from the customer table", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else {
			Customer c = presentationUtility.getTheCustomers().get(row);
			return c;
		}
	}
	
	private Order getSelectedOrder() {
		int row = orderTable.getSelectedRow();
		
		if(row < 0) {
			JOptionPane.showMessageDialog(this, "Select a order from the orders table", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else {
			Order o = presentationUtility.getTheOrders().get(row);
			return o;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnCreateProduct) {
			ProductForm pf = new ProductForm(presentationUtility, null);
			pf.setVisible(true);
			pf.setLocationRelativeTo(this);
		}
		else if(e.getSource() == btnUpdateProduct) {
			Product p = getSelectedProduct();
			if(p != null) {
				ProductForm pf = new ProductForm(presentationUtility, p);
				pf.setVisible(true);
				pf.setLocationRelativeTo(this);
			}
		}
		else if(e.getSource() == btnDeleteProduct) {
			Product p = getSelectedProduct();
			if(p!=null) {
				presentationUtility.deleteProduct(p);
				presentationUtility.updateAdminTables();
				presentationUtility.updateUserTables();
			}
		}
		else if(e.getSource() == btnCreateCustomer) {
			CustomerForm cf = new CustomerForm(presentationUtility, null);
			cf.setVisible(true);
			cf.setLocationRelativeTo(this);
		}
		else if(e.getSource() == btnUpdateCustomer) {
			Customer c = getSelectedCustomer();
			if(c != null) {
				CustomerForm cf = new CustomerForm(presentationUtility, c);
				cf.setVisible(true);
				cf.setLocationRelativeTo(this);
			}
		}
		else if(e.getSource() == btnDeleteCustomer) {
			Customer c = getSelectedCustomer();
			if(c!=null) {
				presentationUtility.deleteCustomer(c);
				presentationUtility.updateAdminTables();
				presentationUtility.updateUserTables();
			}
		}
		else if(e.getSource() == btnUpdateOrderStatus) {
			Order o = getSelectedOrder();
			if(o != null) {
				OrderForm of = new OrderForm(presentationUtility, o);
				of.setVisible(true);
				of.setLocationRelativeTo(this);
			}
		}
		
	}

}
