package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import presentation.entities.Customer;
import presentation.entities.Product;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class CustomerForm extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField idLabel;
	private JTextField nameLabel;
	private JTextField cityLabel;
	private JTextField phoneLabel;
	JButton okButton, cancelButton; 
	private PresentationLevelUtilities presentationUtility;
	private Customer selectedCustomer;

	
	public CustomerForm(PresentationLevelUtilities presentationUtility, Customer selectedCustomer) {
		
		this.presentationUtility = presentationUtility;
		this.selectedCustomer = selectedCustomer;
		
		setBounds(100, 100, 450, 188);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		{
			JLabel lblNewLabel = new JLabel("CustomerID:");
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
			contentPanel.add(lblNewLabel, "cell 0 0,alignx trailing");
		}
		{
			idLabel = new JTextField();
			contentPanel.add(idLabel, "cell 1 0,growx");
			idLabel.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Name:");
			lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 15));
			contentPanel.add(lblNewLabel_1, "cell 0 1,alignx trailing");
		}
		{
			nameLabel = new JTextField();
			contentPanel.add(nameLabel, "cell 1 1,growx");
			nameLabel.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("City:");
			lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 15));
			contentPanel.add(lblNewLabel_2, "cell 0 2,alignx trailing");
		}
		{
			cityLabel = new JTextField();
			contentPanel.add(cityLabel, "cell 1 2,growx");
			cityLabel.setColumns(10);
		}
		{
			JLabel lblPhone = new JLabel("Phone:");
			lblPhone.setFont(new Font("Arial", Font.PLAIN, 15));
			contentPanel.add(lblPhone, "cell 0 3,alignx trailing");
		}
		{
			phoneLabel = new JTextField();
			contentPanel.add(phoneLabel, "cell 1 3,growx");
			phoneLabel.setColumns(10);
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
		if(selectedCustomer != null) {
			idLabel.setText(Integer.toString(selectedCustomer.getCustId()));
			idLabel.setEditable(false);
			nameLabel.setText(selectedCustomer.getName());
			nameLabel.setEditable(false);
			cityLabel.setText(selectedCustomer.getCity());
			phoneLabel.setText(selectedCustomer.getPhone());
		}
	}
	
	private Customer createNewCustomer() {
		
		Customer c = new Customer();
		try {
			c.setName(nameLabel.getText());
			c.setCity(cityLabel.getText());
			c.setPhone(phoneLabel.getText());
		}
		catch(Exception e) {
			//JOptionPane.showMessageDialog(this, "Please enter numeric values for quantity and for price");
		}
		
		return c;
	}
	
	private void updateSelectedCustomer() {
		selectedCustomer.setName(nameLabel.getText());
		selectedCustomer.setCity(cityLabel.getText());
		selectedCustomer.setPhone(phoneLabel.getText());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			if(selectedCustomer == null) {
				Customer p = createNewCustomer();
				presentationUtility.insertCustomer(p);
				presentationUtility.updateAdminTables();
				presentationUtility.updateUserTables();
				this.setVisible(false);
			}
			else {
				updateSelectedCustomer();
				presentationUtility.updateCustomer(selectedCustomer);
				presentationUtility.updateAdminTables();
				presentationUtility.updateUserTables();
				this.setVisible(false);
			}
		}
		else {
			this.setVisible(false);
		}
		
	}

}
