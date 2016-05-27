package models;

public class SavingAccount extends Account{
	
	private float withdrawInterest, depositInterest;
	
	public SavingAccount() {
		money = 0;
		withdrawInterest = 0.05f;
		depositInterest = 0.02f;
	}
	
	public void addMoney(double sum) {
		
		money = money + sum + sum * depositInterest;
		setChanged(); // in order to notiy the associated persons to the account
		notifyObservers(sum);
		System.out.println("The sum " + sum + " was added to your saving account");
	}

	@Override
	public void withdrawMoney(double sum) {
		
		money =  money - sum - sum * withdrawInterest;
		setChanged();
		notifyObservers(sum);
		System.out.println("The sum " + sum + " was withdrawn from the saving account");
	}
}
