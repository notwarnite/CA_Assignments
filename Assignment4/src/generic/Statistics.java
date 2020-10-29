package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles = 1;
	static int numberOfStalls = 0;
	static int numWrPatch ;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of Stalls = " + numberOfStalls);
			writer.println("Number of Cycles taken = " + numberOfCycles);
			writer.println("Number of Instructions on wrong path= " + numWrPatch);
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics

	public static void updateDataStall()
	{
		numberOfStalls += 1;
	}

	public static void updateBranchStall()
	{
		numWrPatch += 1;
	}

	public static void updateInstruction() {
		numberOfInstructions += 1;
	}
	
	public static void updateCycles() {
		numberOfCycles += 1;
	}

}
