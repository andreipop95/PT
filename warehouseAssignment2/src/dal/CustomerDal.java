package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import presentation.entities.Customer;
import presentation.entities.Product;


public class CustomerDal{
	
	private AccessLevelUtilities accessUtility;
	
	public CustomerDal(AccessLevelUtilities accessUtility) {
		this.accessUtility = accessUtility;
	}
	
private final String CUSTOMER = "customer";

	
	private Customer convertRowToCustomer(ResultSet rs) {
		
		Customer p = new Customer();
		
		try {
			p.setCustId(rs.getInt("customerId"));
			p.setName(rs.getString("name"));
			p.setCity(rs.getString("city"));
			p.setPhone(rs.getString("phone"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	protected List<Customer> parseTheResult(ResultSet rs) {
		
		List<Customer> products = new ArrayList<Customer>();
		try{
			while(rs.next()) {
				Customer p = convertRowToCustomer(rs);
				products.add(p);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could not read the result set");
		}
		
		return products;
	}
	
	public List<Customer> getTheCustomers() {
		
		List<Customer> products;
		
		PreparedStatement querry = accessUtility.createSelectQuerry(Customer.class);
		ResultSet rs = accessUtility.executeCommand(querry);
		products = parseTheResult(rs);
		return products;
	}
	
	public void insertCustomer(Customer c) {
		
		PreparedStatement querry = accessUtility.createInsertQuerry(Customer.class);
		
		try {
			querry.setString(1, c.getName());
			querry.setString(2,c.getCity());
			querry.setString(3, c.getPhone());
			querry.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(querry);
	}
	
	public void updateCustomer(Customer c) {
		
		PreparedStatement querry = accessUtility.createUpdateQuerry(Customer.class);
		
		try {
			querry.setString(1, c.getName());
			querry.setString(2,c.getCity());
			querry.setString(3, c.getPhone());
			querry.setInt(4, c.getCustId());
			querry.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(querry);
		
	}
	
	public void deleteCustomer(Customer c) {
		
		PreparedStatement querry = accessUtility.createDeleteQuerry(Customer.class);
		
		try {
			querry.setInt(1, c.getCustId());
			querry.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println(querry);
		
		//accessUtility.executeCommand(querry);
		
	}

}
