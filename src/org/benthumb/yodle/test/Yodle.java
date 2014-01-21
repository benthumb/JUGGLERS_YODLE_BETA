package org.benthumb.yodle.test;

//Algorithm ... start with circuit that has highest values and go in order winnowing all the way
//So first task after getting a list of lists of circuits ordered by scores is to order
//the list of lists itself based on the top score of each list it contains ... also each array list has to be secondarily ordered by
//preference ... multi-column sort is the key to this exercise...

//Also have to add preferences, may be required in order to decide between two equal values, etc.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import org.benthumb.yodle.test.CircuitDataContainer;
import org.benthumb.yodle.test.JugglerDataContainer;

public class Yodle {

	private static Comparator<JugglerDataContainer> CIRCUIT_ORDER = new Comparator<JugglerDataContainer>() {
		// This is where the sorting happens.
		public int compare(JugglerDataContainer o1, JugglerDataContainer o2) {
			return o1.getCircuitNumber() - o2.getCircuitNumber();
		}
	};

	// ** set up comparator for dot product **
	static final Comparator<JugglerDataContainer> DOT_PRODUCT_ORDER = new Comparator<JugglerDataContainer>() {
		public int compare(JugglerDataContainer j1, JugglerDataContainer j2) {
			return j1.getDotProduct() - j2.getDotProduct();
		}
	};

	static Logger logMsg = Logger.getLogger("Yodle");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<CircuitDataContainer> listOfCircuits = new ArrayList<CircuitDataContainer>();
		List<JugglerDataContainer> listOfJugglers = new ArrayList<JugglerDataContainer>();
		File f = new File(
				"C:\\Users\\Paul\\workspace\\JUGGLERS_YODLE\\src\\org\\benthumb\\yodle\\test\\jugglefest_sample.txt");
		try {
			// **************** For each line: split on " " **************** //
			BufferedReader bufr = new BufferedReader(new FileReader(f));
			for (int i = 0; i < f.length(); i++) {
				String line = bufr.readLine();
				if (line != null) {
					if (line.startsWith("J")) {
						listOfJugglers.add(Yodle.processJuggStr(line));
					} else if (line.startsWith("C")) {
						listOfCircuits.add(Yodle.processCircStr(line));
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
		// JugglerDataContainer[] result = new
		// JugglerDataContainer[listOfCircuits.size() * listOfJugglers.size()];
		ArrayList<JugglerDataContainer> result = new ArrayList<JugglerDataContainer>();

		for (CircuitDataContainer circuitData : listOfCircuits) {
			for (JugglerDataContainer jugglerData : listOfJugglers) {

				jugglerData.setCircuitNumber(circuitData.getCircuitNumber());
				jugglerData.setDotProduct(circuitData);
				try {
					JugglerDataContainer dotProductDC = (JugglerDataContainer) jugglerData
							.clone();
					// result[plc] = dotProductDC;
					// plc++;
					result.add(dotProductDC);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		// System.out.println("Test result of result: " + result.length);
		// System.out.print("Test result of result: " + result[0][0]);
		// System.out.print(" " + result[0][1]);
		// System.out.print(" " + result[0][2]);
		// System.out.print(" " + result[0][3]);
		// System.out.print(" " + result[0][4]);
		// System.out.println(" " + result[0][5]);

		ArrayList<ArrayList<JugglerDataContainer>> testResult = Yodle
				.detCircuit(result);
		// testResult = sortListOfLists(testResult); // doing the right thing?
		// testResult = filterLists(testResult);
		System.out.println("Size of our test result: " + testResult.size());
		ArrayList<JugglerDataContainer> insideTestRslt = testResult.get(0);
		ArrayList<JugglerDataContainer> insideTestRslt1 = testResult.get(1);
		ArrayList<JugglerDataContainer> insideTestRslt2 = testResult.get(2);
		System.out.println("First list of circuits: " + insideTestRslt.size());
		System.out
				.println("Second list of circuits: " + insideTestRslt1.size());
		for (JugglerDataContainer juggArr : insideTestRslt) {
			logMsg.log(java.util.logging.Level.INFO, "Result: ");
			System.out.println("Circuit number: " + juggArr.getCircuitNumber());
			System.out.println("Dot product score: " + juggArr.getDotProduct());
			System.out.println("Juggler number: " + juggArr.getJugglerNumber());
		}
		for (JugglerDataContainer juggArr1 : insideTestRslt1) {
			logMsg.log(java.util.logging.Level.INFO, "Result: ");
			System.out.println("Circuit number: " + juggArr1.getCircuitNumber());
			System.out.println("Dot product score: " + juggArr1.getDotProduct());
			System.out.println("Juggler number: " + juggArr1.getJugglerNumber());
		}
		for (JugglerDataContainer juggArr2 : insideTestRslt2) {
			logMsg.log(java.util.logging.Level.INFO, "Result: ");
			System.out.println("Circuit number: " + juggArr2.getCircuitNumber());
			System.out.println("Dot product score: " + juggArr2.getDotProduct());
			System.out.println("Juggler number: " + juggArr2.getJugglerNumber());
		}

		// testResult = sortListOfLists(testResult);
	}

	static JugglerDataContainer processJuggStr(String juggStr) {
		int[] extractedValues = new int[7]; // 7 fields to populate

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
					extractedValues[i] = Integer.parseInt(val);
				} else if (i >= 1 && i < 4) {
					// split on ':'
					String[] scores = val.split(":");
					extractedValues[i] = Integer.parseInt(scores[1]);
				} else {
					// split on ','
					String[] circuits = val.split(",");
					for (String tmp : circuits) {
						tmp = tmp.replace("C", "");
						extractedValues[i] = Integer.parseInt(tmp);
						i++;
					}
				}
			}
			// iterate loop counter
			i++;
		}
		logMsg.log(java.util.logging.Level.INFO,
				"Here's our reconstituted Juggler string: ");
		for (int j : extractedValues) {
			System.out.print(j + " ");
		}

		System.out.println("");

		// Initialize and populate juggler data container ...
		JugglerDataContainer jdc = new JugglerDataContainer();
		jdc.setJugglerNumber(extractedValues[0]);
		jdc.setJugglerHandToEyeCoordination(extractedValues[1]);
		jdc.setJugglerEndurance(extractedValues[2]);
		jdc.setJugglerPizzazz(extractedValues[3]);
		jdc.setJugglerCircuitPreferenceFirst(extractedValues[4]);
		jdc.setJugglerCircuitPreferenceSecond(extractedValues[5]);
		jdc.setJugglerCircuitPreferenceThird(extractedValues[6]);
		return jdc;
	}

	static CircuitDataContainer processCircStr(String circStr) {
		int[] extractedValues = new int[4];

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
					extractedValues[i] = Integer.parseInt(val);
				} else {
					// split on ':'
					String[] scores = val.split(":");
					extractedValues[i] = Integer.parseInt(scores[1]);
				}
			}
			// iterate loop counter
			i++;
		}
		logMsg.log(java.util.logging.Level.INFO,
				"Here's our reconstituted Circuit string: ");
		for (int j : extractedValues) {
			System.out.print(j + " ");

		}
		System.out.println("");
		// Initialize and populate circuit data ...
		CircuitDataContainer cdc = new CircuitDataContainer();
		cdc.setCircuitNumber(extractedValues[0]);
		cdc.setJugglerHandToEyeCoordination(extractedValues[1]);
		cdc.setJugglerEndurance(extractedValues[2]);
		cdc.setJugglerPizzazz(extractedValues[3]);
		return cdc;
	}

	static ArrayList<ArrayList<JugglerDataContainer>> detCircuit(
			ArrayList<JugglerDataContainer> scores) {
		// static ArrayList<JugglerDataContainer[]> detCircuit(
		// JugglerDataContainer[] scores) {
		// ** add all copied arrays here **
		ArrayList<ArrayList<JugglerDataContainer>> circuitL = new ArrayList<ArrayList<JugglerDataContainer>>();

		// ** need to dynamically calculate # partitions **
		List<JugglerDataContainer>copiedArray0 = new ArrayList<JugglerDataContainer>();
		List<JugglerDataContainer>copiedArray1 = new ArrayList<JugglerDataContainer>();
		List<JugglerDataContainer>copiedArray2 = new ArrayList<JugglerDataContainer>();

		copiedArray0 = new ArrayList<JugglerDataContainer>(scores.subList(0, 11));
		copiedArray1 = new ArrayList<JugglerDataContainer>(scores.subList(12, 23));
		copiedArray2 = new ArrayList<JugglerDataContainer>(scores.subList(24, 35));

		// ** sort by circuit **
		// scores = Yodle.getSorted(scores);
		Collections.sort(scores, CIRCUIT_ORDER);
		
		// ** sort by dot product score **
		Collections.sort(copiedArray0, DOT_PRODUCT_ORDER);
		Collections.sort(copiedArray1, DOT_PRODUCT_ORDER);
		Collections.sort(copiedArray2, DOT_PRODUCT_ORDER);

		// int x0 = copiedArray0.length;
		// int x1 = copiedArray1.length;
		// int x2 = copiedArray2.length;

		circuitL.add((ArrayList<JugglerDataContainer>) copiedArray0);
		circuitL.add((ArrayList<JugglerDataContainer>) copiedArray1);
		circuitL.add((ArrayList<JugglerDataContainer>) copiedArray2);

		// circuitL = sortListOfLists(circuitL);

		return circuitL;
	}

	private static ArrayList<int[][]> sortListOfLists(ArrayList<int[][]> lOl) {
		Collections.sort(lOl, new Comparator<int[][]>() {
			@Override
			public int compare(int[][] o1, int[][] o2) {
				return o2[0][1] - o1[0][1];
			}
		});
		int[][] thing1 = lOl.get(0);
		int[][] thing2 = lOl.get(1);
		int[][] thing3 = lOl.get(2);
		// int[][] thing4 = lOl.get(3);

		logMsg.log(java.util.logging.Level.INFO, "Got val 1: " + thing1[0][0]);
		logMsg.log(java.util.logging.Level.INFO, "Got val 2: " + thing2[0][0]);
		logMsg.log(java.util.logging.Level.INFO, "Got val 3: " + thing3[0][0]);
		// logMsg.log(java.util.logging.Level.INFO, "Got val 4: " +
		// thing4[0][0]);

		anaList(lOl);

		return lOl;
	}

	private static void anaList(ArrayList<int[][]> targL) {
		// analyze: current circuit vs preference
		int numAssJuggs = (12 / targL.size());
		ArrayList<int[][]> remainder = new ArrayList<int[][]>();
		ArrayList<int[][]> assigned = new ArrayList<int[][]>();
		for (int i = 0; i < targL.size(); i++) {
			int[][] testCircuit = targL.get(i);
			for (int x = 0; x < numAssJuggs; x++) {
				if (testCircuit[x][0] == testCircuit[x][3]) {
					assigned.add(testCircuit);
					// logMsg.log(java.util.logging.Level.INFO,
					// "Assign Juggler: "
					// + testCircuit[x][2] + " to circuit "
					// + testCircuit[x][0]);
				} else {
					remainder.add(testCircuit);
				}
			}
			int sze = remainder.size();
			// int[][]
		}
	}

	private static ArrayList<int[][]> filterLists(ArrayList<int[][]> sortedLists) {
		int[][] insideTestRslt = sortedLists.get(0);
		int[][] insideTestRslt1 = sortedLists.get(1);
		int[][] insideTestRslt2 = sortedLists.get(2);

		int count = 0;
		int count_a = 0;
		int nxt = 0;
		int currCircuit = 0;
		int sentinel = -1;
		boolean notAssigned = true;
		for (int i = 0; i < insideTestRslt.length; i++) {
			nxt = i + 1;
			currCircuit = insideTestRslt[i][0];
			if (currCircuit == insideTestRslt[i][3] && count < 4) {
				int currJuggler = insideTestRslt[i][2];
				count++;
				logMsg.log(java.util.logging.Level.INFO, "Current count: "
						+ count);
				for (int j = 0; j < insideTestRslt1.length; j++) {
					if (insideTestRslt1[j][2] == currJuggler) {
						insideTestRslt1[j][3] = -1; // selected ...
					}
				}
			}
		}
		for (int j = 0; j < insideTestRslt1.length; j++) {
			nxt = j + 1;
			currCircuit = insideTestRslt1[j][0];
			notAssigned = (insideTestRslt1[j][3] == sentinel) ? false : true;
			if (nxt < insideTestRslt1.length && count_a < 4 && notAssigned) {
				// if (count == 3) {
				// && insideTestRslt[i][1] >= insideTestRslt[nxt][1]
				// && insideTestRslt[i][4] == currCircuit) {
				// if (insideTestRslt[i][1] >= insideTestRslt[nxt][1]
				// && insideTestRslt[i][4] == currCircuit) {
				logMsg.log(java.util.logging.Level.INFO,
						"PASSED: in final TEST!");
				logMsg.log(java.util.logging.Level.INFO, "Current juggler: "
						+ insideTestRslt1[j][2]);
				logMsg.log(java.util.logging.Level.INFO,
						"Current juggler score: " + insideTestRslt1[j][1]);
				int currJuggler = insideTestRslt1[j][2];
				count_a++;
				for (int k = 0; k < insideTestRslt2.length; k++) {
					if (insideTestRslt2[k][2] == currJuggler) {
						insideTestRslt2[k][3] = -1; // selected ...
					}
				}
				// }
			}
		}

		// logMsg.log(java.util.logging.Level.INFO, "Result: ");
		// System.out.println("Result 0: " + insideTestRslt[i][0] + " "
		// + insideTestRslt[i][1] + " " + insideTestRslt[i][2] + " "
		// + insideTestRslt[i][3] + " " + insideTestRslt[i][4] + " "
		// + insideTestRslt[i][5]);
		// System.out.println("");

		ArrayList<int[][]> filteredLLst = new ArrayList<int[][]>();
		filteredLLst.add(insideTestRslt);
		filteredLLst.add(insideTestRslt1);
		filteredLLst.add(insideTestRslt2);
		return filteredLLst;
	}
}
