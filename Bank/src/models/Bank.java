package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Bank implements EBank, java.io.Serializable{
	
	
	private Map<Person, Set<Account>> records;
	
	public Bank() {
		records = new HashMap<Person, Set<Account>>();
	}
	
	public void restoreObservers() { // this method is used after the deserialization because the observers were lost for the accounts
		
		for(Entry<Person, Set<Account>> entry : records.entrySet()) {
			
			Set<Account> accounts = entry.getValue();
			Person p = entry.getKey();
			
			for(Account a : accounts) {
				a.addObserver(p);
			}
		}
		
	}
	
	@Override
	public void addAccountForPerson(Person p, Account assocAcc) {
		
		assert isWellFormed() : "Bank is not well formed";
		assert p != null : "person is null" ;
		assert assocAcc != null : "acc is null";
		
		Set<Account> accounts;
		
		int preSize = 0, postSize;
		
		assocAcc.addObserver(p); // Person p will be notified
		// account add a list of observers for each account
		
		if(records.containsKey(p)) {
			accounts = records.get(p);
			
			preSize = accounts.size();
			accounts.add(assocAcc);
		}
		else {
			accounts = new HashSet<Account>();
			accounts.add(assocAcc);
			records.put(p, accounts);
		}
		
		postSize = accounts.size();
		
		assert isWellFormed() : "Bank is not well formed";
		assert preSize + 1 == postSize : "The size didn't increase";
	}
	
	@Override
	public void depositMoney(double sum, int accId, Person p) {
		
		assert isWellFormed() : "Bank is not well formed";
		
		assert p != null : "person is null";
		assert accId >= 0 : "invalid account id";
		assert sum >0 : "the sum is <= 0";
		
		Set<Account> accounts;
		double preSum = 0f, postSum;
		Account theAccount = null;
		
		if(records.containsKey(p)) {
			accounts = records.get(p);
			for(Account a : accounts) {
				if(a.getAccountId() == accId) {
					preSum = a.getMoney();
					theAccount = a;
					a.addMoney(sum);
				}
			}
		}
		else {
			assert isWellFormed() : "Bank is not well formed";
			 // coult not find the specified set
		}
		
		postSum = theAccount.getMoney();
		
		assert postSum >  preSum : "The sum could not be added to the account";
		assert isWellFormed() : "The bank is not well formed";
	
	}
	
	
	@Override
	public void withdrawMoney(double sum, int accId, Person p) {
		
		assert isWellFormed() : "Bank is not well formed";
		
		assert p != null : "person is null";
		assert accId >= 0 : "invalid account id";
		assert sum > 0 : "the sum is <= 0";
		
		Set<Account> accounts;
		double preSum = 0f, postSum;
		Account theAccount = null;
		
		if(records.containsKey(p)) {
			accounts = records.get(p);
			for(Account a : accounts) {
				if(a.getAccountId() == accId) {
					preSum = a.getMoney();
					theAccount = a;
					a.withdrawMoney(sum);
				}
			}
		}
		else {
			assert isWellFormed() : "Bank is not well formed";
			 // coult not find the specified set
		}
		
		postSum = theAccount.getMoney();
		
		assert postSum < preSum: "The sum could not be withdrawn to the account";
		assert isWellFormed() : "The bank is not well formed";
	}
	
	
	@Override
	public void deletePerson(Person p) {
		
		assert isWellFormed() : "Bank is not well formed";
		assert p != null : "The person is null";
		
		int preSize, postSize;
		preSize = records.size();
		
		if(records.containsKey(p)) {
			records.remove(p);
		}
		else {
			assert isWellFormed() : "Bank is not well formed";
		}
		
		
		postSize = records.size();
		
		assert preSize - 1 == postSize : "The person was not deleted correctly";
		assert isWellFormed() : "Bank is not well formed";
	}

	@Override
	public void deleteAccount(Person p, int accId) { // find all the persons that have access to that account and delete the account
		
		assert isWellFormed() : "Bank is not well formed";
		assert p != null : "The person is null";
		assert accId >= 0 : "invalid account id";
		
		int preSize = 0, postSize = 0;
		Set<Account> accounts = null;
		Account a;
		boolean found = false;
		
		if(records.containsKey(p)) {
			accounts = records.get(p);
			preSize = accounts.size();
			
			Iterator<Account> it = accounts.iterator();
			
			while(it.hasNext()) {
				a = it.next();
				if(a.getAccountId() == accId) {
					it.remove();
					found = true;
					break;
				}
			}
		}
		
		postSize = accounts.size();
	
		assert found : "The account could not be found";
		assert preSize == postSize + 1 : "The account was not deleted";
		assert isWellFormed() : "Bank is not well formed";
	}

	@Override
	public List<Person> findAllPersons() {
		
		assert isWellFormed() : "Bank is not well formed";
		assert !records.isEmpty() : "records are empty";
		
		List<Person> persons = new LinkedList<Person>();
		
		for(Entry<Person, Set<Account>> entry : records.entrySet()) {
			persons.add(entry.getKey());
		}
		
		assert persons.size() == records.size() : "the list wasn't built successfully";
		assert isWellFormed() : "Bank is not well formed";
		
		return persons;
	}

	@Override
	public Set<Account> findAccountsForPerson(Person p) {
		
		assert isWellFormed() : "Bank is not well formed";
		assert p != null : "The person is null";
		
		Set<Account> accounts = null;
		
		if(records.containsKey(p)) {
			accounts = records.get(p);
		}
		
		assert accounts.size() > 0 : "The accounts could not be found";
		assert isWellFormed() : "Bank is not well formed";
		
		return accounts;
	}
	
	
	@Override
	public boolean isWellFormed() { // make sure that there are no persons that don't have active accounts
		
		for(Entry<Person, Set<Account>> entry : records.entrySet()) {
			if(entry.getValue().isEmpty() || entry.getValue() == null)
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Bank [records=" + records + "]";
	}
	
}
