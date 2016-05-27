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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

public class PersonForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField personName;
	private JTextField personId;
	private final Bank bank;
	private final BankView bankView;
	private JTextField accountId;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JRadioButton spending, saving;

	public PersonForm(Bank bank, BankView bankView) {
		
		this.bank = bank;
		this.bankView = bankView;
		
		setBounds(100, 100, 431, 225);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.setVisible(true);
		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][][][][]"));
		{
			JLabel lblPeronsId = new JLabel("Perons Id");
			contentPanel.add(lblPeronsId, "cell 1 0,alignx trailing");
		}
		{
			personId = new JTextField();
			contentPanel.add(personId, "cell 2 0,growx");
			personId.setColumns(10);
		}
		{
			JLabel lblPersonName = new JLabel("Person Name:");
			contentPanel.add(lblPersonName, "cell 1 1,alignx trailing");
		}
		{
			personName = new JTextField();
			contentPanel.add(personName, "cell 2 1,growx");
			personName.setColumns(10);
		}
		{
			JLabel lblAccountId = new JLabel("Account Id");
			contentPanel.add(lblAccountId, "cell 1 2,alignx trailing");
		}
		{
			accountId = new JTextField();
			contentPanel.add(accountId, "cell 2 2,growx");
			accountId.setColumns(10);
		}
		{
			spending = new JRadioButton("Spending Account");
			buttonGroup.add(spending);
			contentPanel.add(spending, "cell 1 4");
		}
		{
			saving = new JRadioButton("Saving Account");
			saving.setHorizontalAlignment(SwingConstants.CENTER);
			buttonGroup.add(saving);
			contentPanel.add(saving, "cell 2 4");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Person p = new Person();
						Account a = null;
						
						if(spending.isSelected()) {
							a = new SpendingAccount();
						}
						else {
							a = new SavingAccount();
						}
						
						p.setName(personName.getText());
						p.setPersonId(Integer.parseInt(personId.getText()));
						a.setAccountId(Integer.parseInt(accountId.getText()));
						bank.addAccountForPerson(p, a);
						bankView.refreshPersonsTable();
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
