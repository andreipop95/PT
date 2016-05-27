package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import frontController.Dispatcher;
import frontController.FrontController;
import net.miginfocom.swing.MigLayout;
import word.Word;

public class AddWordFrame extends JDialog {
	
	
	private FrontController controller;
	private final JPanel contentPanel = new JPanel();
	private JTextField wordField;
	private JTextField synonymField;

	public AddWordFrame(FrontController controller) {
		this.controller = controller;
		setBounds(100, 100, 450, 211);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][][]"));
		{
			JLabel lblNewWord = new JLabel("New Word:");
			lblNewWord.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentPanel.add(lblNewWord, "cell 0 0");
		}
		{
			wordField = new JTextField();
			contentPanel.add(wordField, "cell 2 0,growx");
			wordField.setColumns(10);
		}
		{
			JLabel lblSynonym = new JLabel("Synonym");
			lblSynonym.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentPanel.add(lblSynonym, "cell 0 2");
		}
		{
			synonymField = new JTextField();
			contentPanel.add(synonymField, "cell 2 2,growx");
			synonymField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton save = new JButton("Save");
				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!wordField.getText().equals("") && !synonymField.getText().equals("")) {
							Word newWord = new Word(wordField.getText());
							Word synonym = new Word(synonymField.getText());
							controller.addNewWord(newWord, synonym);
							setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "Please fill both fields", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				save.setActionCommand("OK");
				buttonPane.add(save);
				getRootPane().setDefaultButton(save);
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
		setVisible(true);
		
	}

}
