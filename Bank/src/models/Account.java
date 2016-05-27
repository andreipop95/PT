package models;

import java.util.Observable;

public abstract class Account extends Observable implements java.io.Serializable{
	
	private int accountId;
	protected double money;
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
	
	public abstract void addMoney(double sum);
	public abstract void withdrawMoney(double sum);
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId != other.accountId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + "]";
	}
	
}
