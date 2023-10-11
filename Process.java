
public class Process {
	private int timeArrive; // arrival time
	private int timeBurst; // burst time
	private String pid;
	private int originalBurst;
	private int waitingTime;
	private int runningTime;
	
	public Process(int nTimeA, int nTimeB, String nPid) {
		timeArrive = nTimeA;
		timeBurst = nTimeB;
		pid = nPid;
		originalBurst = nTimeB;
	}
	
	public int getTimeArrive() { // returns the process arrive time
		return timeArrive;
	}
	public int getTimeBurst() { // returns the process burst time
		return timeBurst;
	}
	public int getOriginalBurst() {
		return originalBurst;
	}
	public void setBurst(int b) {
		timeBurst = b;
	}
	public void setArrive(int a) {
		timeArrive = a;
	}
	public String getPid() {
		return pid;
	}
	public int getWait() {
		return waitingTime;
	}
	public void setWait(int nWait) {
		waitingTime = nWait;
	}
	public int getRun() {
		return runningTime;
	}
	public void setRun(int nRun) {
		runningTime = nRun;
	}
	public int getTotalTime() {
		return (runningTime + waitingTime);
	}
	public String toString() {
		String toReturn = "Process ID: " + getPid() + "\n";
		toReturn += "Arrive time: " + getTimeArrive() + "s\n";
		toReturn += "Original burst time: " + getOriginalBurst() + "s\n";
		toReturn += "Remaining burst time: " + getTimeBurst() + "s\n";
		return toReturn;
	}
}
