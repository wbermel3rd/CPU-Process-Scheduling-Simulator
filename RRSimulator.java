
import java.util.*;

public class RRSimulator {
	private Queue<Process> readyQ;
	private Queue<Process> waitQ;
	private Queue<Process> completeQ;
	private int quantum;
	private int contextSwitches;
	private int clock; // CPU total time
	private int idleTime; // CPU idle time
	private int runningTime; // CPU time spent executing processes
	private int numProcesses;
	
	
	public RRSimulator(int q, int c) {
		readyQ = new LinkedList<>();
		waitQ = new LinkedList<>();
		completeQ = new LinkedList<>();
		clock = c;
		quantum = q;
		contextSwitches = 0;
		numProcesses = 0;
	}
	
	public void addProcess(int arrive, int burst, String pid) {
		Process p = new Process(arrive, burst, pid);
		waitQ.add(p);
		numProcesses++;
	}
	
	// @Param uClock is the updated clock after processes have been created
	public void run(int uClock) {
		clock = uClock;
		contextSwitches = 0;
		boolean firstProcess = true; // checking if it is the first process to be run: if not, that means a context switch occurs
        while (!readyQ.isEmpty() || !waitQ.isEmpty()) { // the program ends when both wait and ready queues are empty, meaning
        												// all processes have been executed successfully 
        	
            while (!readyQ.isEmpty()) { // get next process from ready queue, run it for the quantum. if the time left reaches 0, 
            							// the process is terminated and it moves onto the next process in readyQ. if no more processes
            							// exist in readyQ, it begins to check waitQ to add more processes to readyQ.
                Process process = readyQ.remove(); 
                
                for(Process p : readyQ) { // each process that is not currently being executed is waiting in the ready queue to be executed.
                	int wait = p.getWait();
                	wait++;
                	p.setWait(wait);
                }
                
                if(firstProcess) {		// checks if this is the first process to start being executed. if not, a "context switch" occurs. 
                	firstProcess = false;
                } else {
                	contextSwitches++;
                	clock++; // context switch time = 1s
                	idleTime++;
                	System.out.println("CPU was idle for 1s while a context switch occurred.");
                }
                
                for(int i = 0; i < quantum; i++) { // Starts running the current process for the time quantum. 
                								   // Each pass of this loop is one unit of time (s).
                	clock++;
                	runningTime++;
                	process.setBurst(process.getTimeBurst() - 1);
                	if(process.getTimeBurst() > 0) { // if the process still needs time to complete
            			if(i == quantum - 1) { // check if the process has run for the time quantum yet
	                		System.out.print(process.toString() + "\nProcess " + process.getPid() + " ran for " + quantum + "s." );
	                		System.out.print(" Clock: " + clock + "\n");
	                		process.setRun(process.getRun() + quantum);
	            			waitQ.add(process);
            			}
                	} else { // if the process needs no more time to complete, it completes
                		System.out.println("Process: " + process.getPid() + " finished at time: " + clock + "s");
                		completeQ.add(process);
                		i = quantum;
                	}
                }
            }
            while(!waitQ.isEmpty()) { 	// get next process from wait queue, and add the process arrival time to clock and idleTime,
            							// then add the process to the ready queue
            	
            	Process process = waitQ.remove();
            	int prevIdle = idleTime;
            	for(int i = 0; i < process.getTimeArrive(); i++) { // to arrive back to the ready queue, each process must wait its arrive time
            		clock++;
            		idleTime++;
            		process.setWait(process.getWait() + 1);
            	}
            	System.out.println("CPU was idle for " + (idleTime - prevIdle) + "s, as Process " + process.getPid() + " arrived.  Clock: " + clock);
            	readyQ.add(process);
            }
            
            
         System.out.println("Total CPU running time: " + runningTime + "s");
         System.out.println("Total CPU idle time: " + idleTime + "s");
         System.out.println("Total context switches: " + contextSwitches);
         System.out.println();
    
        }
	}
	
	public void calculate() {
		int totalTurnaroundTime = 0; // total turnaround time of all processes combined
		int totalWaitingTime = 0; // total time of all processes spent waiting to start/continue execution
		for(Process p : completeQ) {
			totalWaitingTime += p.getWait();
			totalTurnaroundTime += (p.getRun() + p.getWait());
		}
		int avgTurnaround = (totalTurnaroundTime / numProcesses);
		int avgWaitingTime = (totalWaitingTime / numProcesses);
		System.out.println("Process Performance Evaluation: ");
		System.out.println("Clock: " + clock + "s");
		System.out.println("Idle time: " + idleTime);
		
		double cpuUtil = 1 - ((double) idleTime / (double) clock);
		cpuUtil *= 100;
		String copy = "" + cpuUtil;
		String util = "";
		if(copy.length() > 5) {
			util = copy.substring(0, 5);
		} else {
			util = copy.substring(0, copy.length());
		}
		
		System.out.println("CPU Utilization: " + util + "%");
		System.out.println("Throughput: " + numProcesses + " total processes completed.");
		System.out.println("Average Process Waiting Time: " + avgWaitingTime + "s.");
		System.out.println("Average Process Turnaround Time: " + avgTurnaround + "s.");
	}
}

	
	
	

