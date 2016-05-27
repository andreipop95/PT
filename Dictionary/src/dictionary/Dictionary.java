package dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Set;
import java.util.TreeSet;

import word.NullWord;
import word.Word;
import word.AbstractWord;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Dictionary implements DictionaryInterface{
	
	private Map<Word, Set<Word>> dictionaryWords;
	
	public Dictionary() {
		dictionaryWords = new HashMap<Word, Set<Word>>();
	}
	
	public Map<Word, Set<Word>> getData() {
		return dictionaryWords;
	}
	
	@Override
	public void addSynonymForWord(Word theWord, Word synonym) {
		
		assert isWellFormed() : "The dictionary is not well formed";
		assert theWord != null : "The word is null";
		assert synonym != null : "The synonym is null";
		
		int preSize = 0, postSize;
		Set<Word> synonyms;
		
		if(dictionaryWords.containsKey(theWord)) {
			
			synonyms = dictionaryWords.get(theWord);
			preSize = synonyms.size();
			synonyms.add(synonym);
		}
		
		else {
			
			synonyms = new TreeSet<Word>();
			synonyms.add(synonym);
			dictionaryWords.put(theWord, synonyms);
		}
		
		postSize = synonyms.size();
		
		assert postSize > preSize : "The list of synonymes didn't increase";
		assert isWellFormed() : "The dictionary is not well formed";
	}

	@Override
	public void deleteWord(Word theWord) {
		
		assert isWellFormed() : "The dictionary is not well formed";
		assert theWord != null : "The word is null";
		assert dictionaryWords.containsKey(theWord) : "The word is not in the dictionary";
		
		int preSize, postSize;
		
		preSize = dictionaryWords.size();
		
		if(dictionaryWords.containsKey(theWord)) {
			
			dictionaryWords.remove(theWord); // remove the word from the dictionary -- remove the word from all the lists of synonyms for other words
			
			Set<Word> synonyms;
			
			for(Entry<Word, Set<Word>> entry : dictionaryWords.entrySet()) {
				synonyms = entry.getValue();
				
				if(synonyms.contains(theWord)) {
					synonyms.remove(theWord);
				}
			}
		}
		
		postSize = dictionaryWords.size();
		
		assert postSize < preSize : "The number of words in the dictionary is still the same";
		assert isWellFormed() : "The dictionary is not well formed";
		
	}
	
	private String buildPattern(String searchedInfo) {
		
		StringBuilder pattern = new StringBuilder();		
		
		for(int i = 0; i < searchedInfo.length(); i++) {
			if(searchedInfo.charAt(i) == '*') {
				pattern.append("(.*)");
			}
			else if(searchedInfo.charAt(i) == '?') {
				pattern.append(".{1}");
			}
			else {
				pattern.append(searchedInfo.charAt(i));
			}
		}
		
		return pattern.toString();
	}

	@Override
	public Map<AbstractWord, Set<Word>> searchForWord(String searchedInfo) {
		
		assert isWellFormed() : "The dictionary is not well formed";
		assert searchedInfo != null : "The searched info is null";
		
		String pattern = buildPattern(searchedInfo);
		boolean found = false;
		Map<AbstractWord, Set<Word>> matchedWords = new HashMap<AbstractWord, Set<Word>>();
		
		System.out.println(dictionaryWords.size());
		
		for(Entry<Word, Set<Word>> entry : dictionaryWords.entrySet()) {
			if(entry.getKey().getName().matches(pattern)) {
				matchedWords.put(entry.getKey(), entry.getValue());
				found = true;
			}
		}
		
		if(found == false) {
			NullWord word = new NullWord();
			Set<Word> someSet = new TreeSet<Word>();
			matchedWords.put(new NullWord(), someSet);
		}
		
		assert isWellFormed() : "The dictionary is not well formed";
		assert matchedWords.size() >=1 : "The result is not ok :))";
		
		return matchedWords;
	}
	
	public void printDictionary() {
		
		for(Entry<Word, Set<Word>> entry : dictionaryWords.entrySet()) {
			System.out.print(entry.getKey().getName() + " ");
			
			Set<Word> theSet = entry.getValue();
			
			for(Word w : theSet) {
				System.out.print(w.getName() + " ");
			}
			
			System.out.println();
		}
	}
	
	public void restoreData(Map<Word, Set<Word>> result) {
		this.dictionaryWords = result;
	}
	
	@Override
	public String toString() {
		return "Dictionary [dictionaryWords=" + dictionaryWords + "]";
	}

	@Override
	public boolean isWellFormed() {
		
		for(Entry<Word, Set<Word>> entry : dictionaryWords.entrySet()) {
			if(entry.getValue() == null || entry.getKey() == null)
				return false;
		}
		
		return true;
	}

}
