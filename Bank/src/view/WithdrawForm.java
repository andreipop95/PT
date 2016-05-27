package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import models.Account;
import models.Bank;
import models.Person;
import net.miginfocom.swing.MigLayout;

public class WithdrawForm extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private JTextField money;
	private Bank bank;
	private AccountsForm accountsForm;
	private Account a;
	private Person p;
	
	public WithdrawForm(Bank bank, AccountsForm accountsForm, Account a, Person p) {
		
		this.accountsForm = accountsForm;
		this.bank = bank;
		this.a = a;
		this.p = p;
		
		setBounds(100, 100, 450, 144);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setVisible(true);
		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][]"));
		{
			JLabel lblSumToDeposit = new JLabel("Sum To Withraw");
			contentPanel.add(lblSumToDeposit, "cell 1 1,alignx trailing");
		}
		{
			money = new JTextField();
			contentPanel.add(money, "cell 2 1,growx");
			money.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton withdraw = new JButton("Withdraw");
				withdraw.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						double sum = Double.parseDouble(money.getText());
						bank.withdrawMoney(sum, a.getAccountId(), p);
						accountsForm.refreshAccountsTable();
						setVisible(false);
					}
				});
				withdraw.setActionCommand("OK");
				buttonPane.add(withdraw);
				getRootPane().setDefaultButton(withdraw);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
