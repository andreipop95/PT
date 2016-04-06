package presentation.entities;

public class Customer {
	
	private int customerId;
	private String name, city, phone;
	
	public int getCustId() {
		return customerId;
	}
	public void setCustId(int custId) {
		this.customerId = custId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
