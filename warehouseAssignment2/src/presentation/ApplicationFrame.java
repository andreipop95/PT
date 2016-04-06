package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ApplicationFrame extends JFrame implements ActionListener{
	
	
	private final PresentationLevelUtilities presentationUtility;
	private JPanel contentPane;
	private UserPanel userPanel;
	private AdminPanel adminPanel;
	private JPanel topPanel;
	private JButton btnAdmin, btnUser;

	
	public ApplicationFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setVisible(true);
		setLocationRelativeTo(null);
		
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnUser = new JButton("User");
		btnUser.addActionListener(this);
		
		btnUser.setHorizontalAlignment(SwingConstants.LEFT);
		topPanel.add(btnUser);
		
		
		btnAdmin = new JButton("Admin");
		topPanel.add(btnAdmin);
		btnAdmin.addActionListener(this);
		btnAdmin.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.validate();
		
		presentationUtility = new PresentationLevelUtilities();
		userPanel = new UserPanel(presentationUtility);
		adminPanel = new AdminPanel(presentationUtility); // give access to all the functions
		presentationUtility.setAdminPanel(adminPanel);
		presentationUtility.setUserPanel(userPanel);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		contentPane.removeAll();
		contentPane.add(topPanel, BorderLayout.NORTH);
		if(e.getSource() == btnAdmin) {
			contentPane.add(adminPanel, BorderLayout.CENTER);
		}
		else {
			contentPane.add(userPanel, BorderLayout.CENTER);
		}
		contentPane.validate();
		contentPane.repaint();
	}

}
