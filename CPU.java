import java.io.*;
import java.util.*;

public class CPU {

	private String filePath; // file path string --> test file path:   C:\Users\willa\OneDrive\Desktop\OS_CPU_Schedule.csv
	private String holdS; // use with scanner
	private Scanner reader; // to read the file
	private Scanner in; // scanner
	private boolean fileAccepted; // for first part of program
	private boolean processesCreated; // for creating processes
	private boolean quantumAccepted;
	private RRSimulator scheduler;
	private int quantum;
	private int clock;
	 
	public CPU() throws FileNotFoundException, IOException{
		in = new Scanner(System.in);
		fileAccepted = false;
		processesCreated = false;
		quantumAccepted = false;
		holdS = "";
		filePath = "";
		clock = 0;
	}
	
	public void start() throws IOException, FileNotFoundException {
		
		// first part of program: accepting the file path
		while(!fileAccepted) { 
			
			System.out.print("Welcome to the CPU Scheduler. Please input a file path of file type CSV to be processed.");
			holdS = in.nextLine();
			filePath = holdS;
			System.out.println();
			System.out.print("You inputted: " + holdS + "\n" + "Please press c to confirm. \n");
			holdS = in.nextLine();
			if(holdS.equalsIgnoreCase("c")) {
				System.out.println("Success. Inputting the file from " + filePath);
				reader = new Scanner(new File(filePath));
				fileAccepted = true;
			}
			else {
				System.out.print("Error: could not confirm. Please try again. \n");
			}
			clock++;
			System.out.println("Clock: " + clock + "s");
		}
		
		//second part of the program: accepting a time quantum and creating the scheduler accordingly
		while(!quantumAccepted) { 
			System.out.println("Please enter a time quantum (s) for the round robin algorithm: \n");
			holdS = in.next();
			if(holdS != null && isInt(holdS)) {
				System.out.println("You inputted: " + holdS);
				quantum = Integer.parseInt(holdS);
				System.out.print("Please press c to confirm. \n");
				holdS = in.next();
				if(holdS != null && holdS.equalsIgnoreCase("c")) {
					quantumAccepted = true;
					System.out.print("Success. Inputting time quantum of " + quantum + "s.\n");
				}
				else
					System.out.print("Error: could not confirm. Please try again. \n");
			}
			clock++;
			System.out.println("Clock: " + clock + "s");
		}
		
		scheduler = new RRSimulator(quantum, clock); // create the scheduler
		
		// This algorithm creates all the processes necessary, adding them to an ArrayList in which they will be queued 
		// and 
		while(!processesCreated) {
			reader.useDelimiter("\n");
			String line = "";
			String[] nums = new String[3];
			int arrive; 
			int burst;
			String pid = "";
			System.out.print("File contents: ");
			while(reader.hasNext()) {
				line = reader.nextLine();
				holdS = line;
				
				if(holdS != null) { // checking that the next line is not null
					nums = line.split(",");
					System.out.print("\n");
					if(isInt(nums[0])) { // checking that the first instance of a line is of variable type integer
						System.out.print(holdS);
						pid = String.valueOf(nums[0]);
						arrive = Integer.parseInt(nums[1]);
						burst = Integer.parseInt(nums[2]);
						scheduler.addProcess(arrive, burst, pid);
						
					} else  // else it only prints the strings and moves on
						System.out.print(holdS);
				}
				
			}
			processesCreated = true;
			System.out.println("\n");
		}
		
		scheduler.run(clock);
		scheduler.calculate();
		
	}
			
	
	public static boolean isInt(String s) {
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
}
