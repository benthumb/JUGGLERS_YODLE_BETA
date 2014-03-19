package org.benthumb.yodle;

//Algorithm ... start with circuit that has highest values and go in order winnowing all the way
//So first task after getting a list of lists of circuits ordered by scores is to order
//the list of lists itself based on the top score of each list it contains ... also each array list has to be secondarily ordered by
//preference ... multi-column sort is the key to this exercise...

//Also have to add preferences, may be required in order to decide between two equal values, etc.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

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

	// Juggler and Circuit : first initials
	static final String INITIAL_J = "J";
	static final String INITIAL_C = "C";

	// final position in array holding ... juggler data ?
	static final int MAX = 11;

	// list of circuit data containers
	static List<CircuitDataContainer> fListOfCircuits = new ArrayList<CircuitDataContainer>();

	// list of juggler data containers
	static List<JugglerDataContainer> fListOfJugglers = new ArrayList<JugglerDataContainer>();

	// Circuit / juggler data
	static File dataFile = new File(
			"C:\\Users\\Paul\\workspace\\JUGGLERS_YODLE\\src\\org\\benthumb\\yodle\\test\\jugglefest_sample.txt");

	// ** Logging **
	static Logger logMsg = Logger.getLogger("Yodle");

	// ** Scores : needed for calculating standard deviation **
	static int[] fStoredScores = new int[12];

	// ** Should be reset at 12 **
	static int fNumberOfStoredScores = 0;

	// ** TBD **
	static HashMap<Integer, Double> fCollectedMeans = new HashMap<Integer, Double>();

	// ** Use as basis to calculate standard deviation **
	static HashMap<Integer, Double> fCollectedStdDeviations = new HashMap<Integer, Double>();

	// ** List of **
	static ArrayList<JugglerDataContainer> fInitializedListOfJugglers = new ArrayList<JugglerDataContainer>();

	public static void initializeJugglerCircuitArrays() {
		try {
			// **************** For each line: split on " " **************** //
			BufferedReader bufr = new BufferedReader(new FileReader(dataFile));
			for (int i = 0; i < dataFile.length(); i++) {
				String line = bufr.readLine();
				if (line != null) {
					if (line.startsWith(INITIAL_C)) {
						fListOfCircuits.add(Yodle.processCircStr(line));
					} else if (line.startsWith(INITIAL_J)) {
						fListOfJugglers.add(Yodle.processJuggStr(line));
					} else {
						// do nada ...
					}
				}
			}
			bufr.close();
		} catch (Exception e) {
			// System.out.println("Routine failed: " + e.getStackTrace());
			e.printStackTrace();
		}
	}

	public static void calculateCircuitAssignmentCriteria() {
		// TODO Auto-generated method stub
		for (CircuitDataContainer circuitData : fListOfCircuits) {
			int cirNo = circuitData.getCircuitNumber();
			for (JugglerDataContainer jugglerData : fListOfJugglers) {
				jugglerData.setCircuitNumber(cirNo);
				jugglerData.setDotProduct(circuitData);

				// ** gather scores, standard deviations, means : **
				fStoredScores[fNumberOfStoredScores] = jugglerData.getDotProduct();
				if (fNumberOfStoredScores == MAX) {
					Double calculatedMean = Utilities.calculateMean(fStoredScores);
					logMsg.log(java.util.logging.Level.INFO,"Calculated mean: " + calculatedMean);

					int circuitNo = jugglerData.getCircuitNumber();
					fCollectedMeans.put(new Integer(circuitNo), calculatedMean);
					fCollectedStdDeviations.put(new Integer(circuitNo),Utilities.calculateStdDev(fStoredScores,calculatedMean));
					fNumberOfStoredScores = 0;
					Arrays.fill(fStoredScores, 0);
				} else {
					++fNumberOfStoredScores;
				}
				fInitializedListOfJugglers.add(jugglerData);
			}
		}
		logMsg.log(java.util.logging.Level.INFO,"*** Scores: " + Utilities.arrayToString(fStoredScores));
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
					extractedValues[i] = Integer.parseInt(val);
				} else if (i >= 1 && i < 4) {
					String[] scores = val.split(":");
					extractedValues[i] = Integer.parseInt(scores[1]);
				} else {
					String[] circuits = val.split(",");
					for (String tmp : circuits) {
						tmp = tmp.replace("C", "");
						extractedValues[i] = Integer.parseInt(tmp);
						i++;
					}
				}
			}
			i++;
		}
		logMsg.log(java.util.logging.Level.INFO, "Here's our reconstituted Juggler string: ");
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
					val = val.replace("C", "");
					extractedValues[i] = Integer.parseInt(val);
				} else {
					String[] scores = val.split(":");
					extractedValues[i] = Integer.parseInt(scores[1]);
				}
			}
			i++;
		}
		logMsg.log(java.util.logging.Level.INFO,"Here's our reconstituted Circuit string: ");
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

	public static ArrayList<ArrayList<JugglerDataContainer>> assignCircuits() {
		// static ArrayList<JugglerDataContainer[]> detCircuit(
		// JugglerDataContainer[] scores) {
		// ** add all copied arrays here **
		ArrayList<ArrayList<JugglerDataContainer>> circuitL = new ArrayList<ArrayList<JugglerDataContainer>>();

		// ** need to dynamically calculate # partitions **
		List<JugglerDataContainer> copiedArray0 = new ArrayList<JugglerDataContainer>();
		List<JugglerDataContainer> copiedArray1 = new ArrayList<JugglerDataContainer>();
		List<JugglerDataContainer> copiedArray2 = new ArrayList<JugglerDataContainer>();

		// ** fromIndex, inclusive, and toIndex, exclusive (exception last pos.)
		// **
		copiedArray0 = new ArrayList<JugglerDataContainer>(fInitializedListOfJugglers.subList(0,12));
		copiedArray1 = new ArrayList<JugglerDataContainer>(fInitializedListOfJugglers.subList(12,24));
		copiedArray2 = new ArrayList<JugglerDataContainer>(fInitializedListOfJugglers.subList(24,36));

		// ** check lengths of copied arrays **
		logMsg.log(java.util.logging.Level.INFO, "************* scores: " + fInitializedListOfJugglers.size());
		logMsg.log(java.util.logging.Level.INFO, "************* copiedArray0: " + copiedArray0.size());
		logMsg.log(java.util.logging.Level.INFO, "************* copiedArray1: " + copiedArray1.size());
		logMsg.log(java.util.logging.Level.INFO, "************* copiedArray2: " + copiedArray2.size());

		// ** sort by circuit **
		Collections.sort(fInitializedListOfJugglers, CIRCUIT_ORDER);

		// ** sort by dot product score **
		Collections.sort(copiedArray0, DOT_PRODUCT_ORDER);
		Collections.sort(copiedArray1, DOT_PRODUCT_ORDER);
		Collections.sort(copiedArray2, DOT_PRODUCT_ORDER);

		circuitL.add((ArrayList<JugglerDataContainer>) copiedArray0);
		circuitL.add((ArrayList<JugglerDataContainer>) copiedArray1);
		circuitL.add((ArrayList<JugglerDataContainer>) copiedArray2);

		circuitL = sortListOfLists(circuitL);

		return circuitL;
	}

	@SuppressWarnings("unchecked")
	private static ArrayList<ArrayList<JugglerDataContainer>> sortListOfLists(
			ArrayList<ArrayList<JugglerDataContainer>> lOl) {
		Collections.sort(lOl, new Comparator<Object>() {
			@Override
			public int compare(final Object o1, final Object o2) {
				final ArrayList<JugglerDataContainer> lst1 = (ArrayList<JugglerDataContainer>) o1;
				final ArrayList<JugglerDataContainer> lst2 = (ArrayList<JugglerDataContainer>) o2;
				return lst2.get(lst2.size() - 1).getDotProduct() - lst1.get(lst1.size() - 1).getDotProduct();
			}
		});

		JugglerDataContainer thing1 = lOl.get(0).get(lOl.get(0).size() - 1);
		JugglerDataContainer thing2 = lOl.get(1).get(lOl.get(0).size() - 1);
		JugglerDataContainer thing3 = lOl.get(2).get(lOl.get(0).size() - 1);

		logMsg.log(java.util.logging.Level.INFO,"Got val 1: " + thing1.getDotProduct());
		logMsg.log(java.util.logging.Level.INFO,"Got val 2: " + thing2.getDotProduct());
		logMsg.log(java.util.logging.Level.INFO,"Got val 3: " + thing3.getDotProduct());

		anaList(lOl);

		return lOl;
	}

	// assign a weight to each juggler based on score/preference/standard
	// deviation from mean...
	// may have to calculate mean & standard deviation: if a juggler comes in
	// below a certain threshold
	// then will get bumped from 1st preference in favor of juggler w/ higher
	// score and 2nd preference for circuit in question
	// ...
	private static void anaList(ArrayList<ArrayList<JugglerDataContainer>> targL) {
		for (ArrayList<JugglerDataContainer> lOl : targL) {
			int limit = 0;
			Double stdDev = 0.0;
			Double mean = 0.0;
			int dProd = 0;
			int[] scores = new int[4];
			for (int l = lOl.size() - 1; l >= 0; l--) {
				JugglerDataContainer jData = lOl.get(l);
				int currCircuit = jData.getCircuitNumber();

				// ** determine if weighted / given preferential treatment **
				stdDev = fCollectedStdDeviations.get(currCircuit);
				mean = fCollectedMeans.get(currCircuit);
				dProd = jData.getDotProduct();
				jData.weighted = Math.abs(stdDev / (mean - dProd)) > 2 ? true : false;
				logMsg.log(java.util.logging.Level.INFO, ">>> Ratio: " + Math.abs(stdDev / (mean - dProd)));

				// ** First pass assignment filter **
				if (currCircuit == jData.getJugglerCircuitPreferenceFirst()
						&& jData.getAssignedCircuit() == -1 && limit != 4){
					jData.setAssignedCircuit(currCircuit);
					limit++;
					updateJugglerStatus(targL, jData.getJugglerNumber(),currCircuit);
					scores[limit - 1] = jData.getDotProduct();
				}
				// ** Second pass assignment filter **
				if (limit < 4 && l == 0) {
					logMsg.log(java.util.logging.Level.INFO, "Not all eligible jugglers have been assigned!");
					logMsg.log(java.util.logging.Level.INFO, "score 0: " + scores[0]);
					logMsg.log(java.util.logging.Level.INFO, "score 1: " + scores[1]);
					logMsg.log(java.util.logging.Level.INFO, "score 2: " + scores[2]);
					logMsg.log(java.util.logging.Level.INFO, "score 3: " + scores[3]);
					for (int m = lOl.size() - 1; m >= 0; m--) {
						if (currCircuit == jData.getJugglerCircuitPreferenceSecond()
								&& jData.getAssignedCircuit() == -1
								&& jData.weighted) {
							jData.setAssignedCircuit(currCircuit);
							limit++;
							updateJugglerStatus(targL, jData.getJugglerNumber(),currCircuit);
							scores[limit - 1] = jData.getDotProduct();
						}
					}
				}
			}
		}
	}

	private static void updateJugglerStatus(ArrayList<ArrayList<JugglerDataContainer>> targL, int i, int currCircuit) {
		for (ArrayList<JugglerDataContainer> lOl : targL) {
			for (JugglerDataContainer jDC : lOl) {
				if (jDC.getJugglerNumber() == i && jDC.getAssignedCircuit() == -1) {
					jDC.setAssignedCircuit(currCircuit);
				}
			}
		}
	}
}
