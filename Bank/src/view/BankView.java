package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Set;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Account;
import models.Bank;
import models.Person;
import net.miginfocom.swing.MigLayout;
import serialization.Serialization;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class BankView extends JFrame implements ActionListener{

	private JPanel contentPane;
	private  Map<Person, Set<Account>> records;
	private final Bank bank;
	private List<Person> persons;
	private Set<Account> accounts;
	private final ReflectionUtilities reflection;
	private JTable personTable;
	private JButton addPerson, showAccounts, deletePerson;
	
	public BankView(Bank bank) {
		
		this.bank = bank;
		reflection = new ReflectionUtilities();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 0,grow");
		
		personTable = new JTable();
		personTable.setCellSelectionEnabled(true);
		personTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(personTable);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "cell 0 1,grow");
		
		addPerson = new JButton("Add Person");
		addPerson.addActionListener(this);
		panel_1.add(addPerson);
		
		showAccounts = new JButton("Show Accounts");
		showAccounts.addActionListener(this);
		panel_1.add(showAccounts);
		
		deletePerson = new JButton("Delete Person");
		deletePerson.addActionListener(this);
		panel_1.add(deletePerson);
		
		refreshPersonsTable();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 421);
		setVisible(true);
	
	this.addWindowListener(new java.awt.event.WindowAdapter() {
	    @Override
	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	    		new Serialization().serializeBank(bank); // save the current sate of the bank 
	            System.exit(0);
	    }
	});
	
	}
	
	public void refreshPersonsTable() {
		
		persons = bank.findAllPersons();
		
		List<String> person = reflection.getTheFields(Person.class); 
		String[] personFields = (String[]) person.toArray(new String[person.size()]);
		
		PersonTableFrame personModel = new PersonTableFrame(persons, personFields);
		personTable.setModel(personModel);
	}
	
	private Person getSelectedPerson() {
		int index = personTable.getSelectedRow();
		if(index < 0) {
			JOptionPane.showMessageDialog(this, "Select a person from the table", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else {
			return persons.get(index);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addPerson) {
			new PersonForm(bank, this);
		}
		else if(e.getSource() == showAccounts) {
			
			Person p = getSelectedPerson();
			
			if(p != null) {
				new AccountsForm(bank, p);
			}
		}
		else if(e.getSource() == deletePerson) {
			
			Person p = getSelectedPerson();
			
			if(p != null) {
				bank.deletePerson(p);
				refreshPersonsTable();
			}
		}
	}
}
