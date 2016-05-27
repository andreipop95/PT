package models;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import constants.Constants;

public class Server extends Thread{
	
	private int serverId;
	AtomicInteger waitingTime;
	AtomicInteger averageTime;
	private BlockingQueue<Task> serverTasks = new LinkedBlockingQueue<Task>(15);
	private volatile Task currentTask;
	private volatile String status;
	private int totalWaitedTime, totalNrOfTasks;
	
	public Server() {
		totalWaitedTime = 0;
		totalNrOfTasks = 0;
		waitingTime = new AtomicInteger(0);
		averageTime = new AtomicInteger(0);
	}
	
	public AtomicInteger getAverageTime() {
		return averageTime;
	}
	
	public synchronized void setStatus(String status) {
		this.status = status;
	}
	
	public synchronized String getStatus() {
		return this.status;
	}
	
	public synchronized Task getCurrentTask() {
		return currentTask;
	}
	
	public BlockingQueue<Task> getServerTasks() {
		return serverTasks;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	
	public AtomicInteger getWaitingTime() {
		return waitingTime;
	}
	
	public void addTask(Task t) {
		try {
			serverTasks.put(t);
			waitingTime.addAndGet(t.getProcessDuration());
			this.status = Constants.Server.OPEN; // it means that it was reopened even if initially it was closing
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isDone() {
		if(this.getState().equals(Thread.State.WAITING))
			return true;
		else 
			return false;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Task t = serverTasks.take();
				this.currentTask = t;
				
				System.out.println(this.getName() + " The process with duration: " + t.getProcessDuration() + " executed");
				Thread.sleep(t.getProcessDuration() * 1000); // convert it into seconds
				waitingTime.addAndGet(-1 * t.getProcessDuration());
				
				System.out.println(this.getName() + " Task with the duration: " + t.getProcessDuration() + " ended");
				
				totalWaitedTime += t.getProcessDuration();
				totalNrOfTasks += 1;
				averageTime.set(totalWaitedTime/totalNrOfTasks);
				
			} catch (InterruptedException e) {
				
				System.out.println("An error occured while executing the next task");
				e.printStackTrace();
			}
		}
	}

}
