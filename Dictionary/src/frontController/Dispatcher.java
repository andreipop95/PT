package frontController;

import java.util.Map;
import java.util.TreeSet;

import constants.Commands;

import java.util.Map.Entry;
import java.util.Set;

import dictionary.Dictionary;
import json.JsonSerialization;
import views.AddSynonymForm;
import views.AddWordFrame;
import views.DictionaryFrame;
import word.AbstractWord;
import word.Word;

public class Dispatcher {
	
	private FrontController controller;
	private Dictionary dictionary;
	private DictionaryFrame dictionaryFrame;
	
	public Dispatcher(FrontController controller) {
		this.controller = controller;
		dictionaryFrame = new DictionaryFrame(controller);
		
		this.dispatch(Commands.RestoreData, null, null);
		
		//dictionary = new Dictionary();
	}
	
	
	private void addNewWord(Word theWord, Word theSynonym) {
		
		dictionary.addSynonymForWord(theWord, theSynonym);
		
		//System.out.println("the words added " + theWord.getName() + " " + theSynonym.getName());
		
	}
	
	private void searchWord(String theFormat) {
		Map<AbstractWord, Set<Word>> result = dictionary.searchForWord(theFormat);
		dictionaryFrame.updateTableAfterSearch(result);
		
		for(Entry<AbstractWord, Set<Word>> entry : result.entrySet()) {
			 System.out.println(entry.getKey().getName());
		}
	}
	
	public void dispatch(String command, Word theWord, Word theSynonym) {
		
		switch(command) {
		
			case Commands.NewWordForm : {
				new AddWordFrame(controller); 
				break;
			}
			case Commands.AddNewWord : {
				addNewWord(theWord, theSynonym);
				break;
			}
			case Commands.Search : {
				searchWord(theWord.getName());
				break;
			}
			case Commands.SynoynmForm : {
				new AddSynonymForm(controller, theWord);
				break;
			}
			case Commands.AddNewSynonym : {
				//System.out.println("acuma");
				
				dictionary.addSynonymForWord(theWord, theSynonym);
				break;
			}
			case Commands.DeleteWord : {
				dictionary.deleteWord(theWord);
				break;
			}
			case Commands.SaveData : {
				
				dictionary.printDictionary();
				
				try {
					new JsonSerialization().writeJSON(dictionary);
					System.out.println("The dictionary data was saved");
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("Could not save the data");
				}
				break;
			}
			
			case Commands.RestoreData : {
				try {
					Dictionary d = new JsonSerialization().readJSON();
					this.dictionary = d;
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("Could not restore the data");
				}
				break;
			}
		}
	}
}
