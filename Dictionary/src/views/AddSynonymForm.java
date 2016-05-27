package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import frontController.FrontController;
import net.miginfocom.swing.MigLayout;
import word.Word;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddSynonymForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField wordField;
	private JTextField synonymField;
	private FrontController controller;
	private Word theWord;

	public AddSynonymForm(FrontController controller, Word theWord) {
		this.controller = controller;
		this.theWord = theWord;
		setBounds(100, 100, 450, 237);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][][][]"));
		{
			JLabel lblTheWord = new JLabel("The word");
			lblTheWord.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentPanel.add(lblTheWord, "cell 0 1");
		}
		{
			wordField = new JTextField();
			contentPanel.add(wordField, "cell 2 1,growx");
			wordField.setColumns(10);
		}
		{
			JLabel lblNewSynonym = new JLabel("New Synonym");
			lblNewSynonym.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentPanel.add(lblNewSynonym, "cell 0 3");
		}
		{
			synonymField = new JTextField();
			contentPanel.add(synonymField, "cell 2 3,growx");
			synonymField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton savButton = new JButton("Save");
				savButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(!synonymField.equals("")) {
							
							Word synonym = new Word(synonymField.getText());
							
							controller.addNewSynonym(theWord, synonym);
							setVisible(false);
						}
					}
				});
				savButton.setActionCommand("OK");
				buttonPane.add(savButton);
				getRootPane().setDefaultButton(savButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		wordField.setText(theWord.getName());
		wordField.setEditable(false);
		setVisible(true);
	}

}
