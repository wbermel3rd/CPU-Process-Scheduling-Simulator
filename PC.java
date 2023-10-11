import java.io.FileNotFoundException;
import java.io.IOException;

public class PC {
	public static void main(String[] args) throws FileNotFoundException, IOException{
		CPU myCPU = new CPU();
		myCPU.start();
	}
} 
