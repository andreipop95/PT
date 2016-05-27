package models;

public class SpendingAccount extends Account{
	
	private double bonusPoints;
	private final int pointValue = 5;
	
	public SpendingAccount() {
		money = 0;
	}
	
	public double getBonusPoints() {
		return bonusPoints;
	}

	@Override
	public void addMoney(double sum) { 
		
		money += sum;
		bonusPoints = sum / pointValue;
		setChanged();
		notifyObservers(sum);
		System.out.println("The sum " + sum + " was added to your spending account");
	}

	@Override
	public void withdrawMoney(double sum) {
		
		money -= sum;
		bonusPoints = sum / pointValue;
		setChanged();
		notifyObservers(sum);
		System.out.println("The sum " + sum + " was withdrawn from your spending account");
	}

}
