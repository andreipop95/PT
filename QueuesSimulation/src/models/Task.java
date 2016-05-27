package models;

import org.joda.time.DateTime;

public class Task {
	
	private DateTime arrivalTime, finishTime;
	private int processDuration;
	
	public DateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(DateTime startTime) {
		this.arrivalTime = startTime;
	}

	public int getProcessDuration() {
		return processDuration;
	}

	public void setProcessDuration(int processDuration) {
		this.processDuration = processDuration;
	}

	public DateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(DateTime finishTime) {
		this.finishTime = finishTime;
	}
	
	@Override
	public String toString() {
		return "T: " + arrivalTime.getMinuteOfHour()+ ": " + arrivalTime.getSecondOfMinute() + " D: " + processDuration; 
	}

}
