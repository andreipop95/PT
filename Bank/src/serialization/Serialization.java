package serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import models.Bank;

public class Serialization {
	
	public void serializeBank(Bank bank) {
		
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("D:\\School\\PT\\bank.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(bank);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized was  saved in D:\\School\\PT\\bank.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	
}


