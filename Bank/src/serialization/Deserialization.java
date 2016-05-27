package serialization;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import models.Bank;

public class Deserialization {
	
	public Bank deserializeBank() {
		
		Bank bank = null;
		
		try{
			FileInputStream fileIn = new FileInputStream("D:\\School\\PT\\bank.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			bank = (Bank) in.readObject();
			in.close();
			fileIn.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return bank;
	}

}
