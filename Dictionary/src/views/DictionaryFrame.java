package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import frontController.FrontController;
import json.JsonSerialization;
import net.miginfocom.swing.MigLayout;
import word.AbstractWord;
import word.NullWord;
import word.Word;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;



public class DictionaryFrame extends JFrame {

	private JPanel contentPane;
	private JTextField searchField;
	private JPanel panel;
	private JButton addWord;
	private JButton addSynonym;
	private JButton deleteWord;
	private FrontController controller;
	private JTable wordsTable;
	private JScrollPane scrollPane;
	private List<AbstractWord> theWords;

	public DictionaryFrame(FrontController controller) {
		
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 518, 414);
		this.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][]", "[][grow][grow]"));
		
		searchField = new JTextField();
		contentPane.add(searchField, "cell 0 0,growx");
		searchField.setColumns(10);
		
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(searchField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill the search field", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					controller.search(searchField.getText());
				}
			}
		});
		contentPane.add(search, "cell 1 0");
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 1 2 1,grow");
		
		wordsTable = new JTable();
		scrollPane.setViewportView(wordsTable);
		
		panel = new JPanel();
		contentPane.add(panel, "cell 0 2 2 1,grow");
		
		addWord = new JButton("Add Word");
		addWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.showNewWordForm();
			}
		});
		panel.add(addWord);
		
		addSynonym = new JButton("Add Synonym");
		addSynonym.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = wordsTable.getSelectedRow();
				
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Please select a word", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					Word theWord = (Word) theWords.get(row);
					controller.showNewSynonymForm(theWord);
				}
			}
		});
		panel.add(addSynonym);
		
		deleteWord = new JButton("Delete Word");
		deleteWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = wordsTable.getSelectedRow();
				
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Please select a word", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					Word refferenceWord = (Word) theWords.get(row);
					controller.deleteWord(refferenceWord);
				}
			}
		});
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    		controller.saveData(); // save the current sate of the bank 
		            System.exit(0);
		    }
		});
		panel.add(deleteWord);
		
		setVisible(true);
	}
	
	private String buildTheDefinition(Set<Word> theWords) {
		
		StringBuilder definition = new StringBuilder();
		
		int index = 0;
		
		for(Word w : theWords) {
			
			if(index == 0) {
				definition.append(w.getName());
			}
			else {
				definition.append(" ," +w.getName());
			}
			
			index += 1;
		}
		
		return definition.toString();
	}
	
	private String buildTheDefinitionLambda(Set<Word> theWords) {
		
		String definition = theWords.stream()
				.filter (item -> item.getName().length() > 3)
				.map(item -> item.getName())
				.reduce("", (acc, item) -> item +" " + acc);
		
		return definition;
	}
	
	public void updateTableAfterSearch(Map<AbstractWord, Set<Word>> result) {
		
		theWords = new ArrayList<AbstractWord>();
		theWords.addAll(result.keySet());
		
		// create the string that will represent the set of synonymes displayed in the table
		List<String> synonymes = new ArrayList<String>();
		
		if(theWords.get(0) instanceof NullWord) {
			synonymes.add("");
		}
		else {
		
			for(Entry<AbstractWord, Set<Word>> entry : result.entrySet()) {
				
				Set<Word> equivalentWords = entry.getValue();
				synonymes.add(buildTheDefinitionLambda(equivalentWords));
			}
		}
		
		WordsTableModel tableModel = new WordsTableModel(theWords, synonymes);
		wordsTable.setModel(tableModel);
	}

}
