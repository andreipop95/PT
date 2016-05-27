package frontController;

import constants.Commands;
import word.Word;

public class FrontController {
	
	Dispatcher dispatcher;
			
	public FrontController() {
		dispatcher = new Dispatcher(this);
		//runTest();
	}
	
	public void showNewWordForm() {
		dispatcher.dispatch(Commands.NewWordForm, null, null);
	}
	
	public void addNewWord(Word theWord, Word theSynonym) {
		System.out.println("new word and synonym added:  " + theWord.getName() + " " + theSynonym.getName());
		dispatcher.dispatch(Commands.AddNewWord, theWord, theSynonym);
	}
	
	public void search(String format) {
		Word searchedWord = new Word(format);
		dispatcher.dispatch(Commands.Search, searchedWord, null);
	}
	
	public void showNewSynonymForm(Word theWord) {
		dispatcher.dispatch(Commands.SynoynmForm, theWord, null);
	}
	
	public void addNewSynonym(Word refferenceWord, Word theSynonym) {
		
		System.out.println("new synonym added:  " + refferenceWord.getName() + " " + theSynonym.getName());
		
		dispatcher.dispatch(Commands.AddNewSynonym, refferenceWord, theSynonym);
	}
	
	public void deleteWord(Word refferenceWord) {
		dispatcher.dispatch(Commands.DeleteWord, refferenceWord, null);
	}
	
	public void saveData() {
		dispatcher.dispatch(Commands.SaveData, null, null);
	}
	
	public void restoreData() {
		dispatcher.dispatch(Commands.RestoreData, null, null);
	}

}
