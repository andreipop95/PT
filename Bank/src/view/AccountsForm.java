package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Account;
import models.Bank;
import models.Person;
import models.SpendingAccount;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountsForm extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTable accountsTable;
	private Bank bank;
	private Person person;
	private final ReflectionUtilities reflection;
	private JButton addAccount, removeAccount, deposit, withdraw;
	List<Account> accounts;

	
	public AccountsForm(Bank bank, Person person) {
		
		this.reflection = new ReflectionUtilities();
		this.bank = bank;
		this.person = person;
		
		setTitle("Accounts");
		setBounds(100, 100, 653, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.setVisible(true);
		
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "cell 0 0,grow");
			{
				accountsTable = new JTable();
				scrollPane.setViewportView(accountsTable);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				addAccount = new JButton("Add Account");
				addAccount.addActionListener(this);
				addAccount.setActionCommand("Cancel");
				buttonPane.add(addAccount);
			}
			{
				removeAccount = new JButton("Remove Account");
				removeAccount.addActionListener(this);
				buttonPane.add(removeAccount);
			}
			{
				deposit = new JButton("Deposit");
				buttonPane.add(deposit);
				deposit.addActionListener(this);
			}
			{
				withdraw = new JButton("Withdraw");
				buttonPane.add(withdraw);
				withdraw.addActionListener(this);
			}
		}
		refreshAccountsTable();
	}
	
	protected void refreshAccountsTable() {
		
		List<String> fields = reflection.getTheFields(SpendingAccount.class);
		String[] accountFields = fields.toArray(new String[fields.size()]);
		
		accounts = new LinkedList<Account>(bank.findAccountsForPerson(person));
		
		//System.out.println(accounts.size());
		
		AccountTableFrame accountModel = new AccountTableFrame(accounts, accountFields);
		
		accountsTable.setModel(accountModel);
	}
	
	private Account getSelectedAccount(){
		
		int row = accountsTable.getSelectedRow();
		
		if(row < 0) {
			return null;
		}
		else {
			return accounts.get(row);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addAccount) {
			new AddAccountForm(bank, person, this);
		}
		else if(e.getSource() == removeAccount) {
			Account a = getSelectedAccount();
			
			if(a != null) {
				bank.deleteAccount(person, a.getAccountId());
				refreshAccountsTable();
			}
		}
		else if(e.getSource() == deposit) {
			
			Account a = getSelectedAccount();
			
			if(a != null) {
				new DepositForm(bank, this, a, person);
			}
		}
		else if(e.getSource() == withdraw) {
			
			Account a = getSelectedAccount();
			
			if(a != null) {
				new WithdrawForm(bank, this, a, person);
			}
			
		}
	}
}
