package main;

import models.Account;
import models.Bank;
import models.Person;
import models.SavingAccount;
import models.SpendingAccount;
import serialization.Deserialization;
import view.BankView;

public class Start {
	
	private Bank bank;
	private BankView applicationFrame;

	public Start() {
		//simulation();
		
		//*
		bank = new Deserialization().deserializeBank();
		if(bank == null) {
			bank = new Bank(); // create a new object if the last state of the bank could not be opened
			loadInitialState(); 
		}
		else {
			bank.restoreObservers();
		}
		//*/
		
		applicationFrame = new BankView(bank);
	}
	
	private void loadInitialState()   {
		
		Person p1 = new Person();
		p1.setPersonId(0);
		p1.setName("Andrei");
		Person p2 = new Person();
		p2.setPersonId(1);
		p2.setName("Alex");
		Person p3 = new Person();
		p3.setPersonId(2);
		p3.setName("Andreea");
		
		Account a1 = new SpendingAccount();
		a1.setAccountId(0);
		Account a2 = new SavingAccount();
		a2.setAccountId(1);
		Account a3 = new SpendingAccount();
		a3.setAccountId(2);
		Account a4 = new SavingAccount();
		a4.setAccountId(3);
		
		bank = new Bank();
		
		bank.addAccountForPerson(p1, a1);
		bank.addAccountForPerson(p1, a2);
		
		bank.addAccountForPerson(p2, a3);
		bank.addAccountForPerson(p2, a4);
		
		bank.addAccountForPerson(p3, a3);
		bank.addAccountForPerson(p3, a1);
		
		bank.depositMoney(100, 0, p1);
		bank.depositMoney(200, 1, p1);
		
		System.out.println(bank);
		
		//bank.deleteAccount(p3, 0);
		
		
		//bank.depositMoney(0, 0, p1);
		
		System.out.println(bank);
	}
}
