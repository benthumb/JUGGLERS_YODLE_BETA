package org.benthumb.yodle.test;

// Algorithm ... start with circuit that has highest values and go in order winnowing all the way
// So first task after getting a list of lists of circuits ordered by scores is to order
// the list of lists itself based on the top score of each list it contains ... also each array list has to be secondarily ordered by
// preference ... multi-column sort is the key to this exercise...

// Also have to add preferences, may be required in order to decide between two equal values, etc.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class Jugglers {
	static Logger logMsg = Logger.getLogger("Jugglers");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<int[]> listOfCircuits = new ArrayList<int[]>();
		List<int[]> listOfJugglers = new ArrayList<int[]>();
		File f = new File(
				"C:\\Users\\Paul\\workspace\\JUGGLERS_YODLE\\src\\org\\benthumb\\yodle\\test\\jugglefest_sample.txt");
		try {
			// **************** For each line: split on " " **************** //
			BufferedReader bufr = new BufferedReader(new FileReader(f));
			for (int i = 0; i < f.length(); i++) {
				String line = bufr.readLine();
				if (line != null) {
					if (line.startsWith("J")) {
						listOfJugglers.add(Jugglers.processJuggStr(line));
					} else if (line.startsWith("C")) {
						listOfCircuits.add(Jugglers.processCircStr(line));
					} else {
						// iterate don't pay attention
					}
				}
			}
			bufr.close();
		} catch (Exception e) {
			// System.out.println("Routine failed: " + e.getStackTrace());
			e.printStackTrace();
		}
		int plc = 0;
		int[][] result = new int[listOfCircuits.size() * listOfJugglers.size()][6];
		for (int[] circuit : listOfCircuits) {
			for (int[] juggler : listOfJugglers) {
				int dProd = Jugglers.dotProduct(circuit, juggler);
				// assign
				result[plc][0] = circuit[0];
				result[plc][1] = dProd;
				result[plc][2] = juggler[0];
				result[plc][3] = juggler[4];
				result[plc][4] = juggler[5];
				result[plc][5] = juggler[6];

				// increment
				plc++;
			}
		}

		//		System.out.println("Test result of result: " + result.length);
		//		System.out.print("Test result of result: " + result[0][0]);
		//		System.out.print(" " + result[0][1]);
		//		System.out.print(" " + result[0][2]);
		//		System.out.print(" " + result[0][3]);
		//		System.out.print(" " + result[0][4]);
		//		System.out.println(" " + result[0][5]);

		ArrayList<int[][]> testResult = Jugglers.detCircuit(result);
				System.out.println("Size of our test result: " + testResult.size());
				int[][] insideTestRslt = testResult.get(0);
				int[][] insideTestRslt1 = testResult.get(1);
				int[][] insideTestRslt2 = testResult.get(2);
				System.out.println("First list of circuits: " + insideTestRslt.length);
				System.out
				.println("Second list of circuits: " + insideTestRslt1.length);
				for (int i = 0; i < insideTestRslt.length; i++) {
					logMsg.log(java.util.logging.Level.INFO, "Result: ");
					System.out.println("Result 0: " + insideTestRslt[i][0] + " "
							+ insideTestRslt[i][1] + " " + insideTestRslt[i][2] + " "
							+ insideTestRslt[i][3] + " " + insideTestRslt[i][4] + " "
							+ insideTestRslt[i][5]);
					System.out.println("");
				}
		
				for (int i = 0; i < insideTestRslt1.length; i++) {
					logMsg.log(java.util.logging.Level.INFO, "Result: ");
					System.out.println("Result 1: " + insideTestRslt1[i][0] + " "
							+ insideTestRslt1[i][1] + " " + insideTestRslt1[i][2] + " "
							+ insideTestRslt1[i][3] + " " + insideTestRslt1[i][4] + " "
							+ insideTestRslt1[i][5]);
					System.out.println("");
				}
		
				for (int i = 0; i < insideTestRslt2.length; i++) {
					logMsg.log(java.util.logging.Level.INFO, "Result: ");
					System.out.print("Result 2: " + insideTestRslt2[i][0] + " "
							+ insideTestRslt2[i][1] + " " + insideTestRslt2[i][2] + " "
							+ insideTestRslt2[i][3] + " " + insideTestRslt2[i][4] + " "
							+ insideTestRslt2[i][5]);
					System.out.println("");
				}

		//testResult = sortListOfLists(testResult);
	}

	static int dotProduct(int[] cirArr, int[] juggArr) {
		// The concept: (2i + 4j + 5k) * (5i + 22j + 90k) = 548

		int[] vO = cirArr;
		int[] vT = juggArr;

		int I = 0;
		int J = 0;
		int K = 0;
		int dP;

		for (int i = 1; i < 4; i++) {
			if (i == 1) {
				I = vO[i] * vT[i];
				// System.out.println("I value: " + I);
			} else if (i == 2) {
				J = vO[i] * vT[i];
				// System.out.println("J value: " + J);
			} else {
				K = vO[i] * vT[i];
				// System.out.println("K value: " + K);
			}
		}

		dP = I + J + K;
		// System.out.println("The dot product is: " + dP + " !!");
		return dP;
	}

	static int[] processJuggStr(String juggStr) {
		int[] recept = new int[7];

		// String sampStr = "J J8 H:8 E:2 P:3 C1,C0,C2";
		String sampStr = juggStr;
		String[] tempStr = sampStr.split(" ");
		int i = -1;
		for (String val : tempStr) {
			if (i != -1) {
				if (i == 0) {
					val = val.replace("J", "");
					// keep 1-end : assign to first slot in recept
					// System.out.println("Removed 'J' from 1st position: " +
					// val);
					recept[i] = Integer.parseInt(val);
				} else if (i >= 1 && i < 4) {
					// split on ':'
					String[] scores = val.split(":");
					recept[i] = Integer.parseInt(scores[1]);
				} else {
					// split on ','
					String[] circuits = val.split(",");
					for (String tmp : circuits) {
						tmp = tmp.replace("C", "");
						recept[i] = Integer.parseInt(tmp);
						i++;
					}
				}
			}
			// iterate loop counter
			i++;
		}
		logMsg.log(java.util.logging.Level.INFO,
				"Here's our reconstituted Juggler string: ");
		for (int j : recept) {
			System.out.print(j + " ");
		}
		System.out.println("");
		return recept;
	}

	static int[] processCircStr(String circStr) {
		int[] recept = new int[4];

		// String sampStr = "C C2 H:7 E:6 P:4";
		String sampStr = circStr;
		String[] tempStr = sampStr.split(" ");
		int i = -1;
		for (String val : tempStr) {
			if (i != -1) {
				if (i == 0) {
					// id = id.replace(".xml", "");
					val = val.replace("C", "");
					// keep 1-end : assign to first slot in recept
					// System.out.println("Removed 'C' from 1st position: " +
					// val);
					recept[i] = Integer.parseInt(val);
				} else {
					// split on ':'
					String[] scores = val.split(":");
					recept[i] = Integer.parseInt(scores[1]);
				}
			}
			// iterate loop counter
			i++;
		}
		logMsg.log(java.util.logging.Level.INFO,
				"Here's our reconstituted Circuit string: ");
		for (int j : recept) {
			System.out.print(j + " ");

		}
		System.out.println("");
		return recept;
	}

	static ArrayList<int[][]> detCircuit(int[][] scores) {
		// ** add all copied arrays here **
		ArrayList<int[][]> circuitL = new ArrayList<int[][]>();

		// ** need to dynamically calculate # partitions **
		int[][] copiedArray0 = new int[12][6];
		int[][] copiedArray1 = new int[12][6];
		int[][] copiedArray2 = new int[12][6];

		// ** sort by circuit **
		scores = Jugglers.getSorted(scores, 0);

		// ** copy circuits into newly allocated arrays **
		System.arraycopy(scores, 0, copiedArray0, 0, 12);
		System.arraycopy(scores, 12, copiedArray1, 0, 12);
		System.arraycopy(scores, 24, copiedArray2, 0, 12);

		// ** sort by dot product score **
		copiedArray0 = Jugglers.getSorted(copiedArray0, 1);
		copiedArray1 = Jugglers.getSorted(copiedArray1, 1);
		copiedArray2 = Jugglers.getSorted(copiedArray2, 1);

		//		int x0 = copiedArray0.length;
		//		int x1 = copiedArray1.length;
		//		int x2 = copiedArray2.length;

		circuitL.add(copiedArray0);
		circuitL.add(copiedArray1);
		circuitL.add(copiedArray2);

		circuitL = sortListOfLists(circuitL);

		return circuitL;
	}

	private static int[][] getSorted(int[][] arrToSort, final int col) {
		Arrays.sort(arrToSort, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o2[col] - o1[col];
			}
		});
		return arrToSort;
	}

	private static ArrayList<int[][]> sortListOfLists(ArrayList<int[][]> lOl){		
		Collections.sort(lOl, new Comparator<int[][]>(){		
			@Override
			public int compare(int[][] o1, int[][] o2) {
				return o2[0][1] - o1[0][1];
			}
		});	
		int[][] thing1 = lOl.get(0);
		int[][] thing2 = lOl.get(1);
		int[][] thing3 = lOl.get(2);
		//int[][] thing4 = lOl.get(3);

		logMsg.log(java.util.logging.Level.INFO, "Got val 1: " + thing1[0][0]);
		logMsg.log(java.util.logging.Level.INFO, "Got val 2: " + thing2[0][0]);
		logMsg.log(java.util.logging.Level.INFO, "Got val 3: " + thing3[0][0]);
		//logMsg.log(java.util.logging.Level.INFO, "Got val 4: " + thing4[0][0]);
		
		anaList(lOl);

		return lOl;
	}

	private static void anaList(ArrayList<int[][]> targL){
		// analyze: current circuit vs preference
	    int numAssJuggs = (12/targL.size());
		ArrayList<int[][]> remainder = new ArrayList<int[][]>();
		ArrayList<int[][]> assigned = new ArrayList<int[][]>();
		for(int i = 0; i < targL.size(); i++){
			int[][] testCircuit = targL.get(i);
			for(int x = 0; x < numAssJuggs; x++){
				if(testCircuit[x][0] == testCircuit[x][3]){
					assigned.add(testCircuit);
					logMsg.log(java.util.logging.Level.INFO, "Assign Juggler: " + testCircuit[x][2] + " to circuit " + testCircuit[x][0]);
				}else{
					remainder.add(testCircuit);
				}
			}
			int sze = remainder.size();
			//int[][] 
		}
	}
}
