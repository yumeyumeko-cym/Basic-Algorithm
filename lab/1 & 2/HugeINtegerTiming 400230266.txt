package lab1;

public class HugeIntTiming {
	public static void main(String[] args) {
		HugeInteger huge1, huge2, huge3;
		long startTime, endTime;
		double runTime = 0.0;
		int MAXNUMINTS = 500;
		int MAXRUN = 100;
		for (int numInts = 0; numInts < MAXNUMINTS; numInts++) {
			huge1 = new HugeInteger(10000); // creates a random integer of n digits
			huge2 = new HugeInteger(10000); // creates a random integer of n digits
			startTime = System.currentTimeMillis();
			for (int numRun = 0; numRun < MAXRUN; numRun++) {
				int time = huge1.compareTo(huge2);
			}
			endTime = System.currentTimeMillis();
			runTime += (double) (endTime - startTime) / ((double) MAXRUN);
		}
		runTime = runTime / ((double) MAXNUMINTS);
		System.out.println(runTime);
		
	}
}
