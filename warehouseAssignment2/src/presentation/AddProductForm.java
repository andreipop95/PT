package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import presentation.entities.CustomerOrder;
import presentation.entities.OrderItem;
import presentation.entities.Product;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;

public class AddProductForm extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private final PresentationLevelUtilities presentationUtility;
	private final UserPanel userPanel;
	private JTextField enteredQuantity;
	private Product product;
	JLabel infoLabel, productNameLabel;
	JButton addButton, cancelButton;
	/**
	 * Create the dialog.
	 */
	public AddProductForm(Product prod, PresentationLevelUtilities presentationUtility, UserPanel userPanel) {
		this.userPanel = userPanel;
		this.product = prod;
		this.presentationUtility = presentationUtility;
		setBounds(100, 100, 296, 166);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[1px][1px][][][][][grow]", "[1px][][][]"));
		{
			JLabel lblProductName = new JLabel("Product name:");
			lblProductName.setFont(new Font("Arial", Font.PLAIN, 14));
			contentPanel.add(lblProductName, "cell 2 1 2 1");
		}
		{
			productNameLabel = new JLabel("");
			productNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			contentPanel.add(productNameLabel, "cell 6 1");
		}
		{
			JLabel someLabel = new JLabel("Quantity");
			someLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			someLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			contentPanel.add(someLabel, "cell 2 2 2 1");
		}
		{
			enteredQuantity = new JTextField();
			contentPanel.add(enteredQuantity, "cell 5 2 2 1,growx");
			enteredQuantity.setColumns(10);
		}
		{
			infoLabel = new JLabel("");
			infoLabel.setForeground(Color.RED);
			infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			contentPanel.add(infoLabel, "cell 2 3 5 1");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				addButton = new JButton("Add");
				buttonPane.add(addButton);
				addButton.addActionListener(this);
				getRootPane().setDefaultButton(addButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
		updateProduct();
	}
	
	private void updateProduct() {
		productNameLabel.setText(product.getProductName());
		contentPanel.validate();
	}
	
	private void addNewItemToTheOrder(OrderItem item) {
		
		CustomerOrder order = userPanel.getOrder();
		List<OrderItem> itemsList = order.getItems();
		boolean foundItemInList = false;
		
		for(OrderItem it : itemsList) {
			if(it.getProduct().getProductID() == item.getProduct().getProductID()) {
				it.setQuantity(it.getQuantity() + item.getQuantity());
				order.setSubtotal(order.getSubtotal() + item.getQuantity() * item.getProduct().getProductPrice());
				foundItemInList = true;
			}
		}
		
		if(foundItemInList == false) {
			order.addItem(item);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addButton) {
			if(enteredQuantity.getText().equals("")) {
				infoLabel.setText("Please enter a quantity");
			}
			else {
				int nrItems = 0;
				
				try {
					nrItems = Integer.parseInt(enteredQuantity.getText());
				}
				catch(Exception ex) {
					infoLabel.setText("Please enter a numeric value");
					return;
				}
				
				if(presentationUtility.isValidProductOrder(product, nrItems) && nrItems > 0) {
					infoLabel.setText("");
					product.setQuantity(product.getQuantity() - nrItems);
					presentationUtility.updateProduct(product);
					presentationUtility.updateProductTable(userPanel.getProductTable());
					
					CustomerOrder order = userPanel.getOrder();
					
					addNewItemToTheOrder(new OrderItem(product, nrItems));
					userPanel.refreshSubtotal();
					
					System.out.println(product.getProductPrice());
					
					presentationUtility.updateCustomerOrderTable(userPanel.getOrderTable(), order);
					presentationUtility.updateAdminTables(); // update all the tables in order to work synchronously
					this.setVisible(false);
					//this.dispose();
				}
				else {
					infoLabel.setText("There aren't enough products");
				}
			}
			
			
		}
		else if(e.getSource() == cancelButton) {
			this.setVisible(false);
			this.dispose();
		}
		
	}

}
