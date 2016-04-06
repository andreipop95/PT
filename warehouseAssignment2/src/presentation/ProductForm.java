package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import presentation.entities.Product;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProductForm extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField idLabel;
	private JTextField nameLabel;
	private JTextField quantityLabel;
	private JTextField priceLabel;
	JButton okButton, cancelButton;
	private final PresentationLevelUtilities presentationUtility;
	private boolean isInsert = false; // if it is true it means that we're performing an update
	private Product updateProduct;

	public ProductForm(PresentationLevelUtilities presentationUtility, Product updateProduct) {
		this.updateProduct = updateProduct;
		this.presentationUtility = presentationUtility;
		
		setBounds(100, 100, 450, 197);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][][][]"));
		{
			JLabel lblProductid = new JLabel("ProductID:");
			lblProductid.setFont(new Font("Arial", Font.PLAIN, 14));
			contentPanel.add(lblProductid, "cell 0 0");
		}
		{
			idLabel = new JTextField();
			contentPanel.add(idLabel, "cell 2 0,growx");
			idLabel.setColumns(10);
		}
		{
			JLabel lblProductName = new JLabel("Product Name:");
			lblProductName.setFont(new Font("Arial", Font.PLAIN, 15));
			contentPanel.add(lblProductName, "cell 0 1");
		}
		{
			nameLabel = new JTextField();
			contentPanel.add(nameLabel, "cell 2 1,growx");
			nameLabel.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Quantity:");
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
			contentPanel.add(lblNewLabel, "cell 0 2");
		}
		{
			quantityLabel = new JTextField();
			contentPanel.add(quantityLabel, "cell 2 2,growx");
			quantityLabel.setColumns(10);
		}
		{
			JLabel lblPrice = new JLabel("Price:");
			lblPrice.setFont(new Font("Arial", Font.PLAIN, 15));
			contentPanel.add(lblPrice, "cell 0 3");
		}
		{
			priceLabel= new JTextField();
			contentPanel.add(priceLabel, "cell 2 3,growx");
			priceLabel.setColumns(10);
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
		
		if(updateProduct != null) {
			idLabel.setText(Integer.toString(updateProduct.getProductID()));
			idLabel.setEditable(false);
			nameLabel.setText(updateProduct.getProductName());
			nameLabel.setEditable(false);
			priceLabel.setText(Integer.toString(updateProduct.getProductPrice()));
			quantityLabel.setText(Integer.toString(updateProduct.getQuantity()));
		}
	}
	

	
	private Product createNewProduct() {
		
		Product p = new Product();
		try {
			p.setProductName(nameLabel.getText());
			p.setProductPrice(Integer.parseInt(priceLabel.getText()));
			p.setQuantity(Integer.parseInt(quantityLabel.getText()));
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Please enter numeric values for quantity and for price");
		}
		
		return p;
		
	}
	
	
	private void updateActiveProduct() {
		updateProduct.setProductPrice(Integer.parseInt(priceLabel.getText()));
		updateProduct.setQuantity(Integer.parseInt(quantityLabel.getText()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			if(updateProduct == null) {
				Product p = createNewProduct();
				presentationUtility.insertProduct(p);
				presentationUtility.updateAdminTables();
				presentationUtility.updateUserTables();
				this.setVisible(false);
			}
			else {
				updateActiveProduct();
				presentationUtility.updateProduct(updateProduct);
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
