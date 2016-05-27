package view;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import constants.Constants;
import controller.SimulationControl;
import models.Task;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SimulatorFrame extends JFrame {
	private JPanel contentPane, panel_11;
	private JLabel [] status = new JLabel[Constants.MAX_NR];
	private JPanel [] panel = new JPanel[Constants.MAX_NR];
	private JLabel [] wt = new JLabel[Constants.MAX_NR];
	private JLabel [] avgWaitTime = new JLabel[Constants.MAX_NR];
	private JLabel [] currentProcess = new JLabel[Constants.MAX_NR];
	private JScrollPane [] scrollPane = new JScrollPane[Constants.MAX_NR];
	private JTextField maxTimeBetweenTasks;
	private JTextField minTimeBetweenTasks;
	private JTextField minProcessTime;
	private JTextField maxProcessTime;
	private JTextField maxNrQueues;
	private final SimulationControl simulator;
	private JTextField maxWaitTimeForServer;
	private JTextField minWaitTimeForServer;
	private JTextField startInput, endInput;
	private JLabel remainingTime;
	private int maxNrOfServers;
	private JSlider slider;
	

	public SimulatorFrame(SimulationControl simulator) {
		this.simulator = simulator;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][100px:n:100px,grow][100px:n:100px,grow][100px:n:100px,grow][100px:n:100px,grow][100px:n:100px,grow][100px:n:100px,grow][100px:n:100px,grow][100px:n:100px,grow]", "[][][grow][][][][][][][][]"));
		
		JLabel lblNewLabel_1 = new JLabel("Server Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_1, "cell 0 0");
		
		JLabel lblServer = new JLabel("Server1");
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblServer, "cell 1 0,alignx center");
		
		JLabel lblServer_1 = new JLabel("Server2");
		lblServer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblServer_1, "cell 2 0,alignx center,aligny center");
		
		JLabel lblServer_2 = new JLabel("Server3");
		lblServer_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblServer_2, "cell 3 0,alignx center");
		
		JLabel lblServer_3 = new JLabel("Server4");
		lblServer_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblServer_3, "cell 4 0,alignx center");
		
		JLabel lblServer_4 = new JLabel("Server5");
		lblServer_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblServer_4, "cell 5 0,alignx center,aligny center");
		
		JLabel lblServer_5 = new JLabel("Server6");
		lblServer_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblServer_5, "cell 6 0,alignx center");
		
		JLabel lblServer_6 = new JLabel("Server7");
		lblServer_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblServer_6, "cell 7 0,alignx center");
		
		JLabel lblServer_7 = new JLabel("Server8");
		lblServer_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblServer_7, "cell 8 0,alignx center");
		
		generateStatusLabels();
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblStatus, "cell 0 1");
		
		generateThePanels();
		
		JLabel lblCurrentProcess = new JLabel("Current process: ");
		contentPane.add(lblCurrentProcess, "cell 0 3");
		
		generateCurrentProcessLabels();
		
	
		JLabel lblNewLabel = new JLabel("Waiting time: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblNewLabel, "cell 0 4");
		
		generateWaitingTimeLabels();
		
		JLabel lblAvgWt = new JLabel("Avg WT: ");
		contentPane.add(lblAvgWt, "cell 0 5");
		
		generateAverageTimeLabels();
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "cell 0 6 3 1,grow");
		
		JLabel lblMinTimeBetween = new JLabel("Min Time Between Customers:");
		panel_1.add(lblMinTimeBetween);
		
		minTimeBetweenTasks = new JTextField();
		minTimeBetweenTasks.setColumns(8);
		panel_1.add(minTimeBetweenTasks);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, "cell 3 6 3 1,grow");
		
		JLabel lable_min = new JLabel("Min Process Time: ");
		panel_3.add(lable_min);
		
		minProcessTime = new JTextField();
		panel_3.add(minProcessTime);
		minProcessTime.setColumns(8);
		
		JPanel panel_7 = new JPanel();
		contentPane.add(panel_7, "cell 6 6 3 1,grow");
		
		JLabel lblMaxWaitTime = new JLabel("Max Wait Time / Server: ");
		panel_7.add(lblMaxWaitTime);
		
		maxWaitTimeForServer = new JTextField();
		panel_7.add(maxWaitTimeForServer);
		maxWaitTimeForServer.setColumns(8);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, "cell 0 7 3 1,grow");
		
		JLabel lblMaxTimeBetween = new JLabel("Max Time Between Customers:");
		panel_2.add(lblMaxTimeBetween);
		
		maxTimeBetweenTasks = new JTextField();
		panel_2.add(maxTimeBetweenTasks);
		maxTimeBetweenTasks.setColumns(8);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, "cell 3 7 3 1,grow");
		
		JLabel lblMaxProcessTime = new JLabel("Max Process Time: ");
		panel_4.add(lblMaxProcessTime);
		
		maxProcessTime = new JTextField();
		panel_4.add(maxProcessTime);
		maxProcessTime.setColumns(8);
		
		JPanel panel_8 = new JPanel();
		contentPane.add(panel_8, "cell 6 7 3 1,grow");
		
		JLabel lblMinWaitTimeserver = new JLabel("Min Wait Time / Server: ");
		panel_8.add(lblMinWaitTimeserver);
		
		minWaitTimeForServer = new JTextField();
		panel_8.add(minWaitTimeForServer);
		minWaitTimeForServer.setColumns(8);
		
		slider = new JSlider();
		slider.setValue(60);
		slider.setVisible(false);
		contentPane.add(slider, "cell 0 8 3 1");
		
		JPanel panel_9 = new JPanel();
		contentPane.add(panel_9, "cell 3 8 3 1,grow");
		
		JLabel lblStartTime = new JLabel("Start time: ");
		panel_9.add(lblStartTime);
		
		startInput = new JTextField();
		panel_9.add(startInput);
		startInput.setColumns(10);
		
		JPanel panel_10 = new JPanel();
		contentPane.add(panel_10, "cell 6 8 3 1,grow");
		
		JLabel lblEndTime = new JLabel("End time: ");
		panel_10.add(lblEndTime);
		
		endInput = new JTextField();
		panel_10.add(endInput);
		endInput.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		contentPane.add(panel_5, "cell 0 9 3 1,grow");
		
		JLabel lblMaxNrOf = new JLabel("Max Nr Of Queues");
		panel_5.add(lblMaxNrOf);
		
		maxNrQueues = new JTextField();
		panel_5.add(maxNrQueues);
		maxNrQueues.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		contentPane.add(panel_6, "cell 2 10 4 1,grow");
		
		JButton btnStartSimulation = new JButton("Start Simulation");
		btnStartSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				maxNrOfServers = Integer.parseInt(maxNrQueues.getText());
				activateNrOfServers();
				simulator.initializeSimulation();
				configureSlider();
			}
		});
		panel_6.add(btnStartSimulation);
		
		JButton btnStartStandardSimulation = new JButton("Start Standard Simulation");
		btnStartStandardSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				maxNrOfServers = 5;
				activateNrOfServers();
				simulator.initializeStandardSimulation();
			}
		});
		panel_6.add(btnStartStandardSimulation);
		
		panel_11 = new JPanel();
		contentPane.add(panel_11, "cell 6 10 3 1,grow");
		
		JLabel lblRemainingSeconds = new JLabel("Remaining seconds:  ");
		panel_11.add(lblRemainingSeconds);
		
		remainingTime = new JLabel("");
		panel_11.add(remainingTime);
		
		this.setVisible(true);
		this.setSize(970, 609);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void configureSlider() {
		int maxValue = (int) (Double.parseDouble(maxTimeBetweenTasks.getText()) * 1000);
		int minValue = (int) (Double.parseDouble(minTimeBetweenTasks.getText()) * 1000);
		
		slider.setMaximum(maxValue);
		slider.setMinimum(minValue);
		slider.setValue((minValue + maxValue) /2);
		slider.setVisible(true);

	}
	
	public void generateAverageTimeLabels() {
		
		for(int i = 0; i<Constants.MAX_NR; i++) {
			avgWaitTime[i] = new JLabel("0");
			int newCoord = new Integer(i + 1);
			contentPane.add(avgWaitTime[i], "cell " + newCoord + " 5, alignx center");
		}
	}
	
	public void setRemainingTime(int time){
		remainingTime.setText(String.valueOf(time));
		panel_11.revalidate();
		panel_11.repaint();
	}
	
	private void generateCurrentProcessLabels() {
		
		for(int i = 0; i < Constants.MAX_NR; i++) {
			currentProcess[i] = new JLabel("-");
			int newCoord = new Integer(i + 1);
			contentPane.add(currentProcess[i], "cell " + newCoord + " 3, alignx center");
		}
	}
	
	private void generateWaitingTimeLabels() {
		
		for(int i = 0; i < Constants.MAX_NR; i++) {
			wt[i] = new JLabel("0");
			int newCoord = new Integer(i + 1);
			contentPane.add(wt[i], "cell " + newCoord + " 4, alignx center");
		}
	}
	
	private void generateThePanels() {
		
		for(int i = 0; i < Constants.MAX_NR; i++) {
			int newCoord = new Integer(i + 1);
			scrollPane[i] = new JScrollPane();
			contentPane.add(scrollPane[i], "cell " + newCoord + " 2,grow");
			
			panel[i] = new JPanel();
			scrollPane[i].setViewportView(panel[i]);
		}
	}
	
	private void generateStatusLabels() {
		
		for(int i = 0; i < Constants.MAX_NR; i++) {
			int newCoord = new Integer(i + 1);
			status[i] = new JLabel("closed");
			contentPane.add(status[i], "cell " + newCoord + " 1,alignx center");
		}
	}
	
	public String getStartInput() {
		return startInput.getText();
	}

	public String getEndInput() {
		return endInput.getText();
	}
	
	private void activateNrOfServers() {
		
		for(int i = maxNrOfServers; i < Constants.MAX_NR; i++) {
			scrollPane[i].setVisible(false);
			status[i].setVisible(false);
			wt[i].setVisible(false);
			currentProcess[i].setVisible(false);
			avgWaitTime[i].setVisible(false);
			scrollPane[i].revalidate();
			scrollPane[i].repaint();
		}
	}
	
	public int getSliderValue() {
		return slider.getValue();
	}
	
	public double getMinTimeBetweenTasks() {
		return Double.parseDouble(minTimeBetweenTasks.getText());
	}
	
	public double getMaxTimeBetweenTasks() {
		return Double.parseDouble(maxTimeBetweenTasks.getText());
	}
	
	public int getMaxWaitTimeForServer(){
		return Integer.parseInt(maxWaitTimeForServer.getText());
	}
	
	public int getMinWaitTimeForServer(){
		return Integer.parseInt(minWaitTimeForServer.getText());
	}
	
	public int getMinProcessTime() {
		return Integer.parseInt(minProcessTime.getText());
	}
	
	public int getMaxProcessTime() {
		return Integer.parseInt(maxProcessTime.getText());
	}
	
	public int getNrOfQueues() {
		return Integer.parseInt(maxNrQueues.getText());
	}
	
	public void displayAverageWaitingTime(int time, int index) {
		avgWaitTime[index].setText(Integer.toString(time));
	}
	
	public void displayServersStatus(String currentState, int index) {
		status[index].setText(currentState);
	}
	
	public void displayWaitingTime(int time, int index) {
		wt[index].setText("" + time);
	}
	
	public void displayCurrenTask(Task t, int serverIndex) {
		if(t != null)
			currentProcess[serverIndex].setText(t.toString());
		else {
			currentProcess[serverIndex].setText("-");
		}
	}
	
	public void setClosedServer(int index) {
		status[index].setText("closed");
	}
	
	public void displayTasks(Task[] tasks, int serverIndex) {
		
		panel[serverIndex].removeAll();
		panel[serverIndex].revalidate();
		JList<Task> jTasks = new JList<Task>(tasks);
		panel[serverIndex].add(jTasks);
		panel[serverIndex].repaint();
		panel[serverIndex].revalidate();
	}
}
