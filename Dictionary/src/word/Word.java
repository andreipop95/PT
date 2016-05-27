package word;

import java.util.Comparator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Word extends AbstractWord implements Comparable{

	
	public Word(String name) {
		this.name = name;
	}
	
	public Word() {
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public int compareTo(Object o) { // sort in lexicographic order the synonym associated to another word
		
		Word w1 = (Word) o;
		
		return this.name.compareTo(w1.name);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean isNull() {
		return false;
	}
	
}
