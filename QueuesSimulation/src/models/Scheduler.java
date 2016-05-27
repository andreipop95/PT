package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import constants.Constants;
import view.SimulatorFrame;

public class Scheduler {
	
	private int maxWaitingTimeForServer, minWaitingTimeForServers; // implement closing for a minimum waiting time treshold

	private int maxNrOfServers;
	private boolean serverEnabled[] = new boolean[Constants.MAX_NR];
	private int notBusyEnoughTimeServer[] = new int[Constants.MAX_NR];
	private final SimulatorFrame simulatorFrame;
	private List<Server> servers = new ArrayList<Server>();
	private boolean sesionEnded;

	public Scheduler(SimulatorFrame simulatorFrame) {
		this.simulatorFrame = simulatorFrame;
		sesionEnded = false;
	}
	
	public void setSesionEnded(boolean state) {
		sesionEnded = state;
	}
	
	public void initializeTheNotBusyTime() {
		for(int i = 0; i < Constants.MAX_NR; i++) {
			notBusyEnoughTimeServer[i] = 0;
		}
	}
	
	public void startTheFirstServer(){
		Server s = new Server();
		initializeTheNotBusyTime();
		s.start();
		s.setServerId(0);
		s.setStatus(Constants.Server.OPEN);
		servers.add(s); // always have an active server
		serverEnabled[0] = true;
	}
	
	public int getTotalNrOfTasks() {
		int sum = 0;
		
		for(Server s : servers) {
			sum += s.getServerTasks().size();
		}
		
		return sum;
	}
	
	public int getMinWaitingTime() {
		return minWaitingTimeForServers;
	}

	public void setMinWaitingTimeForServers(int minWaitingTime) {
		this.minWaitingTimeForServers = minWaitingTime;
	}
	
	public List<Server> getServers() {
		return servers;
	}
	
	public int getMaxWaitingTimeForServer() {
		return maxWaitingTimeForServer;
	}

	public void setMaxWaitingTimeForServer(int maxWaitingTimeForServer) {
		this.maxWaitingTimeForServer = maxWaitingTimeForServer;
	}
	public int getMaxNrOfServers() {
		return maxNrOfServers;
	}
	public void setMaxNrOfServers(int maxNrOfServers) {
		this.maxNrOfServers = maxNrOfServers;
	}
	
	public boolean isDone() {
		
		boolean isDone = true;
		
		for(Server server : servers) {
			if(server.isDone() == false) {
				isDone = false;
				break;
			}
		}
		
		return isDone;
	}
	
	private int getTheFirstDisabledServer() {
		
		for(int i = 0; i < maxNrOfServers; i++) {
			if(serverEnabled[i] == false)
				return i;
		}
		
		return 0;
	}
	
	private Server createNewServer() {
		
		Server s = new Server();
		int index =  getTheFirstDisabledServer();
		
		System.out.println("New server created");
		
		serverEnabled[index] = true;
		s.setServerId(index);
		s.setStatus(Constants.Server.OPEN);
		s.start();
		servers.add(s);
		return s;
	}
	
	private Server getAClosingServer() {
		
		for(Server s : servers) {
			if(s.getStatus().equals(Constants.Server.CLOSING))
				return s;
		}
		
		return null;
	}
	
	private Server determineMinWaitingServer(int processDuration) {
		
		int minWaitingTime = Integer.MAX_VALUE, waitingTime;
		Server minWaitingServer = null;
		
		for(Server s : servers) {
			
			//System.out.println("The waiting time: "+ s.getWaitingTime());
			
			waitingTime = s.getWaitingTime().intValue();
			if(waitingTime + processDuration < minWaitingTime && !s.getStatus().equals(Constants.Server.CLOSING)) {
				minWaitingTime = waitingTime + processDuration;
				minWaitingServer = s;
			}
			
		}
		
		if(minWaitingTime > maxWaitingTimeForServer) {
			
			minWaitingServer = getAClosingServer(); // keep open a closing server
			
			if(minWaitingServer == null) {
			
				if(maxNrOfServers > servers.size()) {
					minWaitingServer = createNewServer();
				}
				else
					minWaitingServer = null;
			}
			else {
				System.out.println("Reopened the server " + minWaitingServer.getServerId());
				minWaitingServer.setStatus(Constants.Server.OPEN); // not closing anymore
			}
		}
		
		return minWaitingServer;
	}
	
	public void closeNonBusyServers() {
		
		Iterator<Server> it = servers.iterator();
		while(it.hasNext()) {
			Server s = it.next();
			if(s.isDone() && servers.size() > 1){
				simulatorFrame.setClosedServer(s.getServerId());
				notBusyEnoughTimeServer[s.getServerId()] = 0;
				serverEnabled[s.getServerId()] = false;
				simulatorFrame.displayCurrenTask(null, s.getServerId());
				it.remove();
			}
			else if(sesionEnded && s.isDone()) {
				simulatorFrame.setClosedServer(s.getServerId());
				simulatorFrame.displayCurrenTask(null, s.getServerId());
				it.remove();
			}
		}
	}
	
	private void startClosingIfNotEnoughTasks(){
		for(Server s : servers) {
			if(notBusyEnoughTimeServer[s.getServerId()] > Constants.MAX_TIME_NOT_BUSY) { // then start closing it if it's not the last one
				if(servers.size() > 1) {
					//System.out.println("ServerId: " + s.getServerId());
					s.setStatus(Constants.Server.CLOSING); // close it only if it's not the last one
					return; // close only one at the time
				}
			}
		}
	}
	
	public void closeTheServers() {
		for(Server s : servers) {
			s.setStatus(Constants.Server.CLOSING);
		}
	}
	
	private void markTheNonBusyServers() {
		for(Server s : servers) {
			if(s.getWaitingTime().intValue() < minWaitingTimeForServers) {
				notBusyEnoughTimeServer[s.getServerId()] += 1;
			}
			else {
				notBusyEnoughTimeServer[s.getServerId()] = 0;
			}
		}
	}
	
	public void dispatchTasksOnServers(Task t) {
		
		Server minWaitingServer = determineMinWaitingServer(t.getProcessDuration());
		
		if(minWaitingServer == null) {
			System.out.println("All servers are full");
		}
		else {
			minWaitingServer.setStatus(Constants.Server.OPEN);
			notBusyEnoughTimeServer[minWaitingServer.getServerId()] = 0; // if adding a task on a closing server => will be opened again for some time
			minWaitingServer.addTask(t);
		}
		
		markTheNonBusyServers();
		startClosingIfNotEnoughTasks();// this will set the status flag -- closing on the servers where the waiting time is less than it should
		closeNonBusyServers();
		
	}
}
