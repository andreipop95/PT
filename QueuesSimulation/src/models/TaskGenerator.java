package models;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import view.SimulatorFrame;

public class TaskGenerator extends Thread{
	
	private final SimulatorFrame simulatorFrame;
	private int minProcTime;
	private int maxProcTime;
	private double minTimeBetweenTasks;
	private double maxTimeBetweenTasks;
	private boolean isEnabled; // will be accessed only by the SimulationControl unit
	private BlockingQueue<Task> generatedTasks; // the tasks that will be generated
	
	public TaskGenerator(BlockingQueue<Task> generatedTasks, SimulatorFrame simulatorFrame) {
		this.simulatorFrame = simulatorFrame;
		this.generatedTasks = generatedTasks;
	}
	
	public synchronized void setEnabled(boolean state) { // only one thread can access it at the time
		this.isEnabled = state;
	}
	
	public void setMinTimeBetweenTaks(double time) {
		this.minTimeBetweenTasks = time;
	}
	
	public void setMaxTimeBetweenTaks(double time) {
		this.maxTimeBetweenTasks = time;
	}
	
	public void setMinProcTime(int time) {
		this.minProcTime = time;
	}
	
	public void setMaxProcTime(int time) {
		this.maxProcTime = time;
	}
	
	public void generateTask() {
		
		Random rand = new Random();
		
		Task theTask = new Task();
		
		theTask.setProcessDuration(minProcTime + rand.nextInt(maxProcTime - minProcTime));
		
		try { // add the task into the blocking queue
			generatedTasks.put(theTask);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void run() {
		
		while(isEnabled) {
			
			generateTask();
			
			//int time = (int)((maxTimeBetweenTasks - minTimeBetweenTasks) * 1000);
			
			int time = simulatorFrame.getSliderValue();
			
			//System.out.println("time differnece " + time);
			
			//int timeBetweenTasks = (int)(minTimeBetweenTasks*1000) + new Random().nextInt(time);
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
