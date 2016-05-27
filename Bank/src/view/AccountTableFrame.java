package view;

import java.util.List;

import models.Account;

public class AccountTableFrame extends AbstractTableFrame{
	
	public AccountTableFrame(List<?> items, String[] columnNames) {
		super(items, columnNames);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		
		Account tempAccount = (Account) items.get(row);
		
		switch(col) {
			case 0 : return tempAccount.getAccountId();
			case 1 : return tempAccount.getMoney();
			default : return tempAccount.getAccountId();
		}
		
	}
}
