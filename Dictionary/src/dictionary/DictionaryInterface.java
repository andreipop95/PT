package dictionary;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import word.AbstractWord;
import word.Word;

public interface DictionaryInterface {
	
	
	/**
	 * @param theWord, synonym
	 * @precondition theWord != null && synonym != null
	 * @postcondition synonym pre.theWord.synonyms.size() < pre.theWord.synonyms.size();
	 */
	public void addSynonymForWord(Word theWord, Word synonym);
	
	/**
	 * @param theWord
	 * @precondition theWord != null && theWord is in the dictionary
	 * @postcondition pre.dictionaryWords.size < post.DictionaryWords.size();
	 */
	
	public void deleteWord(Word theWord);
	
	/**
	 * @param searchedInfo
	 * @return a map with all the words that have matched the searching
	 * @precondition searchedInfo != null && !searchedInfo.equals("");
	 * @postcondition result.size() >= 1 
	 */
	public Map<AbstractWord, Set<Word>> searchForWord(String searchedInfo);
	
	public boolean isWellFormed();
	
}
