package models;

import java.util.List;
import java.util.Set;

public interface EBank {
	
	/**
	 * @precondition person!=NULL && assocAcc != NULL
	 * @postcondition pre p.accounts.size + 1 = post p.accounts.size + 1
	 */
	public void addAccountForPerson(Person p, Account assocAcc);
	
	/*
	 * @precondition person != NULL && accId >= 0 && sum > 0
	 * @postcondition pre p.accId.sum > post p.accId.sum
	 */
	public void depositMoney(double sum, int accId, Person p);
	
	
	/*
	 * @precondition person != NULL && accId >= 0 && sum > 0 && sum < money
	 * @postcondition pre p.accId.sum - sum <  post p.accId.sum
	 */
	public void withdrawMoney(double sum, int accId, Person p);
	
	/*
	 * @precondition p != null
	 * @postcondition pre records.size == post records.size - 1
	 */
	public void deletePerson(Person p);
	
	
	/*
	 * @precondition p != null && accId > 0 
	 * @postcondition pre p.accounts.size == post p.accounts.size - 1 && the account was found
	 */
	public void deleteAccount(Person p, int accId);
	
	/*
	 * @precondition records.size != null
	 * @postcondition records.size == list.size
	 */
	public List<Person> findAllPersons(); 
	
	/*
	 * @precondition p != null
	 * @postcondition accounts.size() > 0
	 */
	public Set<Account> findAccountsForPerson(Person p);
	
	public boolean isWellFormed();
}
