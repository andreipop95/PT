package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import models.Scheduler;
import models.Server;
import models.Task;
import models.TaskGenerator;
import view.SimulatorFrame;

public class SimulationControl extends Thread{
	
	private Scheduler scheduler;
	private final SimulatorFrame simulatorFrame;
	private final TaskGenerator taskGenerator;
	private List<Server> servers = new ArrayList<Server>();
	private int currentTimeMoment = 0;
	private int timeLimit = 15;
	private BlockingQueue<Task> generatedTasks = new LinkedBlockingQueue<Task>(20); // the list with all the clients that will be distributed
	private DateTime startTime, endTime, currentTime, peakTime;
	private int maxNrOfTasks;
	
	public SimulationControl() {
		
		peakTime = startTime;
		maxNrOfTasks = 0;
		
		simulatorFrame =new SimulatorFrame(this);
		scheduler = new Scheduler(simulatorFrame);
		
		taskGenerator = new TaskGenerator(generatedTasks, simulatorFrame);
		taskGenerator.setEnabled(true);
		
	}
	
	public void initializeStandardSimulation(){
		
		startTime = DateTime.now();
		endTime = startTime.plusSeconds(30);
		
		this.start();
		
		scheduler.setMaxNrOfServers(5);
		scheduler.setMaxWaitingTimeForServer(20);
		scheduler.setMinWaitingTimeForServers(15);
		
		taskGenerator.setMaxTimeBetweenTaks(0.8);
		taskGenerator.setMinTimeBetweenTaks(0.3);
		taskGenerator.setMinProcTime(1);
		taskGenerator.setMaxProcTime(5);
		
		taskGenerator.start();
		scheduler.startTheFirstServer();
	}
	
	public void  initializeSimulation() {
		
		setTheWorkingIntervals();
		this.start();
		
		setSchedulerAndGeneratorConstraints();
		scheduler.startTheFirstServer();
		taskGenerator.start(); // the thread generating the tasks
		
	}
	
	private void setTheWorkingIntervals() {
		
		String start = simulatorFrame.getStartInput();
		String end = simulatorFrame.getEndInput();
		
		DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm:ss"); 
		
		startTime = df.parseLocalTime(start).toDateTimeToday();
		endTime = df.parseLocalTime(end).toDateTimeToday();
		
	}
	
	private void setSchedulerAndGeneratorConstraints() {
		scheduler.setMaxNrOfServers(simulatorFrame.getNrOfQueues());
		scheduler.setMaxWaitingTimeForServer(simulatorFrame.getMaxWaitTimeForServer());
		scheduler.setMinWaitingTimeForServers(simulatorFrame.getMinWaitTimeForServer());
		
		taskGenerator.setMaxTimeBetweenTaks(simulatorFrame.getMaxTimeBetweenTasks());
		taskGenerator.setMinTimeBetweenTaks(simulatorFrame.getMinTimeBetweenTasks());
		taskGenerator.setMinProcTime(simulatorFrame.getMinProcessTime());
		taskGenerator.setMaxProcTime(simulatorFrame.getMaxProcessTime());
	}
	
	
	public Task[] getTasksForServer(Server s) {
		
		BlockingQueue<Task> bq = s.getServerTasks();
		
		Task tasks[] = new Task[bq.size()];
		bq.toArray(tasks);
		
		return tasks;
	}
	
	private int getWaitingTime(Server s) {
		
		return s.getWaitingTime().intValue();
	}
	
	private void displayTheWaitingTime() {
		
		for(Server s : servers) {
			simulatorFrame.displayWaitingTime(getWaitingTime(s), s.getServerId());
		}
	}
	
	private void displayTheTasks() {
		
		servers = scheduler.getServers();
		
		for(Server s : servers) {
			
			simulatorFrame.displayTasks(getTasksForServer(s), s.getServerId());
			simulatorFrame.displayServersStatus(s.getStatus(), s.getServerId());
			simulatorFrame.displayWaitingTime(getWaitingTime(s), s.getServerId());
			simulatorFrame.displayCurrenTask(s.getCurrentTask(), s.getServerId());
			simulatorFrame.displayAverageWaitingTime(s.getAverageTime().intValue(), s.getServerId());
		}
		
		try {
			Thread.sleep(1000);
			//System.out.println(currentTimeMoment);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void dispatchGeneratedTasks() {
		
		while(!generatedTasks.isEmpty()) { // do not try to take directly from the list because we don't want to block the current thread
			try {
				Task t = generatedTasks.take();
				t.setArrivalTime(DateTime.now());
				scheduler.dispatchTasksOnServers(t);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void checkIfBusiestMoment() {
		if(generatedTasks.size() > maxNrOfTasks) {
			maxNrOfTasks = new Integer(generatedTasks.size());
			System.out.println(scheduler.getTotalNrOfTasks());
			peakTime = new DateTime(currentTime);
		}
	}
	
	@Override
	public void run() {
		
		System.out.println(this.getName());
		
		currentTime = startTime;
		
		while(currentTime.isBefore(endTime)) { // generate all the tasks
			currentTimeMoment += 1;
			
			dispatchGeneratedTasks();
			
			displayTheTasks();
			
			servers = scheduler.getServers();
			scheduler.closeNonBusyServers();
			
			currentTime = currentTime.plusSeconds(1);
			checkIfBusiestMoment();
			simulatorFrame.setRemainingTime(endTime.getSecondOfDay() - currentTime.getSecondOfDay());
		}
		
		taskGenerator.setEnabled(false); // stop generating tasks after the time has passed
		scheduler.setSesionEnded(true);
		
		scheduler.closeTheServers();
		
		while(!scheduler.isDone()) { // keep showing the tasks until they finish the execution
			displayTheTasks();
			displayTheWaitingTime(); 
			scheduler.closeNonBusyServers();
		}
		
		displayTheTasks(); // in order to display the last task also
		displayTheWaitingTime(); // shoulg be 0 now
		scheduler.closeNonBusyServers();
		JOptionPane.showMessageDialog(simulatorFrame, "Peak time: " + peakTime.getMinuteOfHour() + ":" + peakTime.getSecondOfMinute() + "  " + maxNrOfTasks + " max nr of tasks");
	}
	
}
