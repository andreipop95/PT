package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import models.Account;
import models.Bank;
import models.Person;
import models.SavingAccount;
import models.SpendingAccount;

public class BankTest {

		
		Bank bank = new Bank();
		Person p1 = new Person();
		Person p2 = new Person();
		Person p3 = new Person();
		Person p4 = new Person();
		Account a1 = new SpendingAccount();
		Account a2 = new SavingAccount();
		Account a3 = new SpendingAccount();
		Account a4 = new SavingAccount();
		
		@Before
		public void justInit() {
			p1.setPersonId(0);
			p1.setName("Andrei");
			p2.setPersonId(1);
			p2.setName("Alex");
			 
			p3.setPersonId(2);
			p3.setName("Andreea");
			
			 
			a1.setAccountId(0);
			a2.setAccountId(1);
			a3.setAccountId(2);
			a4.setAccountId(3);
			
			
			bank.addAccountForPerson(p1, a1);
			bank.addAccountForPerson(p1, a2);
			
			bank.addAccountForPerson(p2, a3);
			bank.addAccountForPerson(p2, a4);
			
			bank.addAccountForPerson(p3, a3);
			bank.addAccountForPerson(p3, a1);
			
			bank.depositMoney(100, 0, p1);
			bank.depositMoney(200, 1, p1);
		}
		
		@Test 
		public void testAddPerson() {
			
			
			bank.addAccountForPerson(p4, a1);
			
			Set<Account> accounts = bank.findAccountsForPerson(p4);
			
			assertEquals(accounts.size(), 1);
		}
		
		@Test
		public void testRemoveAccount() {
			
			boolean accountDeleted = true;
			
			bank.deleteAccount(p1, 0);
			
			Set<Account> accounts = bank.findAccountsForPerson(p1);
			
			for(Account a : accounts) {
				if(a.equals(a1)) {
					accountDeleted = false;
				}
			}
			
			assertTrue(accountDeleted);
		}
		
		@Test
		public void deletePerson() {
			
			bank.deletePerson(p1);
			
			Set<Account> accounts = bank.findAccountsForPerson(p1);
			
			assertEquals(null, accounts);
		}
		
		
		@Test
		public void testDepositMoney() {
			
			double addSum = 20;
			double preSum = a2.getMoney();
			
			bank.depositMoney(20, 1, p1); // add in the account of p1 
			
			double postSum = a2.getMoney();
			
			boolean ok = false;
			
			if(postSum >= preSum + addSum) {
				ok = true;
			}
			
			assertTrue(ok);
		}
		
		@Test
		public void testFindAccounts() {
			
			boolean foundAccounts = false;
			
			Set<Account> accounts = bank.findAccountsForPerson(p2);
			
			
			if(accounts.size() == 2 && accounts.contains(a3) && accounts.contains(a4)) {
				foundAccounts = true;
			}
			
			assertTrue(foundAccounts);
		}
		
		@Test
		public void testFindPersons() {
			
			boolean foundAllPersons = false;
			
			List<Person> persons = bank.findAllPersons();
			
			if(persons.contains(p2) && persons.contains(p3)) {
				foundAllPersons = true;
			}
			
			assertTrue(foundAllPersons);
			
		}
		
	}

