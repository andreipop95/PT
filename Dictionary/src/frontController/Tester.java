package frontController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import word.Word;

public class Tester {
	
	public Tester() {
	
		new FrontController();	
		
		//runTest();
	}
	
	private void runTest() {
		Word w1 = new Word();
		w1.setName("chair");
		Word w2 = new Word();
		w2.setName("dog");
		Word w3 = new Word();
		w3.setName("cat");
		
		Word w4 = new Word();
		w4.setName("cow");
		Word w5 = new Word();
		w5.setName("pig");
		Word w6 = new Word();
		w6.setName("hat");
	
		List<Word> words = new LinkedList<Word>();
		words.add(w1);
		words.add(w2);
		words.add(w3);
		words.add(w4);
		words.add(w5);
		words.add(w6);
		
		String word = words.stream()
				//.filter( item -> item.getName().length() > 3)
				.map( item -> item.getName())
				.reduce("", (acc, item) -> acc + ", " + item);
		
		
		System.out.println(word);
		
	}

}
