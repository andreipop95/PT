package views;

import word.NullWord;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import word.AbstractWord;
import word.Word;

public class WordsTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"Word", "Synonyms"};
	private List<AbstractWord> words;
	private List<String> synonymes;
	
	public WordsTableModel(List<AbstractWord> words, List<String> synonymes) {
		this.words = words;
		this.synonymes = synonymes;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return words.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		if(words.get(0) instanceof NullWord) {
			if(col == 0)
				return words.get(0).getName();
			else {
				return "";
			}
		}
		else {
			Word theWord = (Word) words.get(row);
			String theSynonymes = (String) synonymes.get(row);
			
			switch(col) {
				case 0: return theWord.getName();
				case 1: return theSynonymes;
				default : return theWord.getName();
			}
		}
	}
}
