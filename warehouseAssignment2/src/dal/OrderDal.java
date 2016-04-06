package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import presentation.entities.CustomerOrder;
import presentation.entities.Order;


public class OrderDal {
	
	private final AccessLevelUtilities accessUtility;
	
	public OrderDal(AccessLevelUtilities accessUtility) {
		this.accessUtility = accessUtility;
	}
	
	private Order convertRowToOrder(ResultSet rs) {
		
		Order o = new Order();
		
		try {
			o.setOrder_customerID(rs.getInt("order_customerID"));
			o.setOrderId(rs.getInt("orderId"));
			o.setStatus(rs.getString("status"));
			o.setSubtotal(rs.getInt("subtotal"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return o;
	}
	
	protected List<Order> parseTheResult(ResultSet rs) {
		
		List<Order> orders = new ArrayList<Order>();
		try{
			while(rs.next()) {
				Order p = convertRowToOrder(rs);
				orders.add(p);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Could not read the result set");
		}
		
		return orders;
	}
	
	public List<Order> getAllOrders() {
		
		List<Order> orders;
		
		PreparedStatement querry = accessUtility.createSelectQuerry(Order.class);
		ResultSet rs = accessUtility.executeCommand(querry);
		
		orders = parseTheResult(rs);
		return orders;
	}
	
	
	public void insertOrder(CustomerOrder order) {
		
		PreparedStatement querry = accessUtility.createInsertQuerry(Order.class);
		
		try {
			querry.setInt(1, order.getOderCustomerId());
			querry.setInt(2, order.getSubtotal());
			querry.setString(3, order.getOrderStatus());
			querry.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(querry);
	}
	
	public void updateOrder(Order order) {
		
		PreparedStatement querry = accessUtility.createUpdateQuerry(Order.class);
		
		try {
			querry.setInt(1, order.getOrder_customerID());
			querry.setInt(2, order.getSubtotal());
			querry.setString(3, order.getStatus());
			querry.setInt(4, order.getOrderId());
			querry.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(querry);
	}
	
	

}
