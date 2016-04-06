package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import presentation.entities.Customer;
import presentation.entities.Order;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class OrderForm extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField orderIDfield, customerIDField, subtotalField, statusField;
	private JButton okButton, cancelButton;
	private PresentationLevelUtilities presentationUtility;
	private Order selectedOrder;
	
	public OrderForm(PresentationLevelUtilities presentationUtility, Order selectedOrder ) {
		this.presentationUtility = presentationUtility;
		this.selectedOrder = selectedOrder;
		
		setBounds(100, 100, 450, 197);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		{
			JLabel lblOrder = new JLabel("OrderID:");
			lblOrder.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPanel.add(lblOrder, "cell 0 0,alignx trailing");
		}
		{
			orderIDfield = new JTextField();
			contentPanel.add(orderIDfield, "cell 1 0,growx");
			orderIDfield.setColumns(10);
		}
		{
			JLabel lblOrderCustomerId = new JLabel("Customer ID:");
			lblOrderCustomerId.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPanel.add(lblOrderCustomerId, "cell 0 1,alignx trailing");
		}
		{
			customerIDField = new JTextField();
			contentPanel.add(customerIDField, "cell 1 1,growx");
			customerIDField.setColumns(10);
		}
		{
			JLabel lblSubtotal = new JLabel("Subtotal:");
			lblSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPanel.add(lblSubtotal, "cell 0 2,alignx trailing");
		}
		{
			subtotalField = new JTextField();
			contentPanel.add(subtotalField, "cell 1 2,growx");
			subtotalField.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Status:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPanel.add(lblNewLabel, "cell 0 3,alignx trailing");
		}
		{
			statusField = new JTextField();
			contentPanel.add(statusField, "cell 1 3,growx");
			statusField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
		setTheFields();
		
	}
	
	private void updateSelectedOrder() {
		selectedOrder.setStatus(statusField.getText());
	}
	
	private void setTheFields() {
		orderIDfield.setText(Integer.toString(selectedOrder.getOrderId()));
		customerIDField.setText(Integer.toString(selectedOrder.getOrder_customerID()));
		subtotalField.setText(Integer.toString(selectedOrder.getSubtotal()));
		orderIDfield.setEditable(false);
		customerIDField.setEditable(false);
		subtotalField.setEditable(false);
		statusField.setText(selectedOrder.getStatus());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == okButton) {
			updateSelectedOrder();
			presentationUtility.updateOrder(selectedOrder);
			presentationUtility.updateAdminTables();
			presentationUtility.updateUserTables();
			this.setVisible(false);
		}
		
	}

}
