package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Account;
import models.Bank;
import models.Person;
import models.SavingAccount;
import models.SpendingAccount;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddAccountForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField accountId;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JRadioButton spending, saving;
	private Bank bank;
	private Person person;
	private AccountsForm accountsForm;
	
	public AddAccountForm(Bank bank, Person person, AccountsForm accountsForm) {
		
		this.bank = bank;
		this.person = person;
		this.accountsForm = accountsForm;
		
		setBounds(100, 100, 450, 174);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.setVisible(true);
		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][][]"));
		{
			JLabel lblNewLabel = new JLabel("Account Id");
			contentPanel.add(lblNewLabel, "cell 1 1,alignx trailing");
		}
		{
			accountId = new JTextField();
			contentPanel.add(accountId, "cell 2 1,growx");
			accountId.setColumns(10);
		}
		{
			spending = new JRadioButton("Spending");
			buttonGroup.add(spending);
			contentPanel.add(spending, "cell 1 2");
		}
		{
			saving = new JRadioButton("Saving");
			buttonGroup.add(saving);
			contentPanel.add(saving, "cell 2 2");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Account a;
						
						if(spending.isSelected()) {
							a = new SpendingAccount();
						}
						else {
							a = new SavingAccount();
						}
						
						a.setAccountId(Integer.parseInt(accountId.getText()));
						
						bank.addAccountForPerson(person, a);
						
						accountsForm.refreshAccountsTable();
						
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
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
