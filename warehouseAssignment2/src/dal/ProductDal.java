package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import presentation.entities.CustomerOrder;
import presentation.entities.Order;
import presentation.entities.Product;

public class ProductDal {
	
	private final String PRODUCT = "product";
	
	private AccessLevelUtilities accessUtility;
	
	public ProductDal(AccessLevelUtilities accessUtility) {
		this.accessUtility = accessUtility;
	}
	
	private Product convertRowToProduct(ResultSet rs) {
		
		Product p = new Product();
		
		try {
			p.setProductID(rs.getInt("productID"));
			p.setProductName(rs.getString("productName"));
			p.setProductPrice(rs.getInt("price"));
			p.setQuantity(rs.getInt("quantity"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	protected List<Product> parseTheResult(ResultSet rs) {
		
		List<Product> products = new ArrayList<Product>();
		try{
			while(rs.next()) {
				Product p = convertRowToProduct(rs);
				products.add(p);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could not read the result set");
		}
		
		return products;
	}
	
	public List<Product> getTheProducts() {
		
		List<Product> products;
		
		PreparedStatement querry = accessUtility.createSelectQuerry(Product.class);
		ResultSet rs = accessUtility.executeCommand(querry);
		
		products = parseTheResult(rs);
		return products;
	}
	
	public void updateProduct(Product prod) {
		
		PreparedStatement querry = accessUtility.createUpdateQuerry(Product.class);
		
		try {
			querry.setString(1, prod.getProductName());
			querry.setInt(3, prod.getProductPrice());
			querry.setInt(2, prod.getQuantity());
			querry.setInt(4, prod.getProductID());
			querry.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertProduct(Product prod) {
		
		PreparedStatement querry = accessUtility.createInsertQuerry(Product.class);
		
		try {
			querry.setString(1, prod.getProductName());
			querry.setInt(3, prod.getProductPrice());
			querry.setInt(2, prod.getQuantity());
			querry.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(querry);
	}
	
	public void deleteProduct(Product p) {
		
		PreparedStatement querry = accessUtility.createDeleteQuerry(Product.class);
		
		try {
			querry.setInt(1, p.getProductID());
			querry.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println(querry);
		
		//accessUtility.executeCommand(querry);
		
	}
	

}
