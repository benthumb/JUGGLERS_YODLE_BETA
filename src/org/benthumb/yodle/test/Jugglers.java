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
		File f = new File("C:\\Users\\Paul\\workspace\\JUGGLERS_YODLE\\src\\org\\benthumb\\yodle\\test\\jugglefest_sample.txt");
		try {
			// **************** For each line: split on " " **************** //
			BufferedReader bufr = new BufferedReader(new FileReader(f));
			for (int i = 0; i < f.length(); i++) {
				String line = bufr.readLine();
				if(line != null){
					if(line.startsWith("J")){
						listOfJugglers.add(Jugglers.processJuggStr(line));
					}
					else if(line.startsWith("C")){
						listOfCircuits.add(Jugglers.processCircStr(line));
					}
					else{
						// iterate don't pay attention
					}
				}
			}
		}catch(Exception e){
			//System.out.println("Routine failed: " + e.getStackTrace());
			e.printStackTrace();
		}
		int count = 0;
		int plc = 0;
		int[][] result = new int[listOfCircuits.size() * listOfJugglers.size()][6];
		for(int[] circuit : listOfCircuits){
			for(int[] juggler : listOfJugglers){
				int dProd = Jugglers.dotProduct(circuit, juggler);
				// assign
				result[plc][0] = circuit[0];
				result[plc][1] = dProd;
				result[plc][2] = juggler[0];
				result[plc][3] = juggler[4];
				result[plc][4] = juggler[5];
				result[plc][5] = juggler[6];

				//increment
				plc++;

				//System.out.println("Count: " + count);
				count++;
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
		System.out.println("Second list of circuits: " + insideTestRslt1.length);
		for(int i = 0; i < insideTestRslt.length; i++){
			//logMsg.log(java.util.logging.Level.INFO, "Happy!");
			System.out.print("Result: " + insideTestRslt[i][0]);
			System.out.print(" " + insideTestRslt[i][1]);
			System.out.print(" " + insideTestRslt[i][2]);
//			System.out.print(" " + insideTestRslt[i][3]);
//			System.out.print(" " + insideTestRslt[i][4]);
//			System.out.print(" " + insideTestRslt[i][5]);
			System.out.println("");
		}
		
		for(int i = 0; i < insideTestRslt1.length; i++){
			System.out.print("Result 1: " + insideTestRslt1[i][0]);
			System.out.print(" " + insideTestRslt1[i][1]);
			System.out.print(" " + insideTestRslt1[i][2]);
//			System.out.print(" " + insideTestRslt[i][3]);
//			System.out.print(" " + insideTestRslt[i][4]);
//			System.out.print(" " + insideTestRslt[i][5]);
			System.out.println("");
		}
		
		for(int i = 0; i < insideTestRslt2.length; i++){
			System.out.print("Result 2: " + insideTestRslt2[i][0]);
			System.out.print(" " + insideTestRslt2[i][1]);
			System.out.print(" " + insideTestRslt2[i][2]);
//			System.out.print(" " + insideTestRslt[i][3]);
//			System.out.print(" " + insideTestRslt[i][4]);
//			System.out.print(" " + insideTestRslt[i][5]);
			System.out.println("");
		}
		
		//Jugglers.dotProduct();
		//		String jugg = "J J8 H:8 E:2 P:3 C1,C0,C2";
		//		String circ = "C C0 H:7 E:7 P:10";
		//		int[] juggArr = Jugglers.processJuggStr(jugg);
		//		int[] cirArr = Jugglers.processCircStr(circ);
		//		Jugglers.dotProduct(cirArr, juggArr);

	}
	static int dotProduct(int[] cirArr, int[] juggArr){
		// (2i + 4j + 5k) * (5i + 22j + 90k) = 548

		//		Vector<Integer> vO = new Vector<Integer>();
		//		Vector<Integer> vT = new Vector<Integer>();

		int[] vO = cirArr;
		int[] vT = juggArr;

		//My_Vector.addAll(Arrays.asList(My_Array));

		//		vO.add(2);
		//		vO.add(4);
		//		vO.add(90);
		//
		//		vT.add(5);
		//		vT.add(22);
		//		vT.add(5);

		int I = 0;
		int J = 0;
		int K = 0;
		int dP;

		for(int i = 1; i < 4; i++){
			// first I
			if(i == 1){
				I = vO[i]*vT[i];
				//System.out.println("I value: " + I);
			}
			else if(i == 2){
				J = vO[i]*vT[i];
				//System.out.println("J value: " + J);
			}
			else{
				K = vO[i]*vT[i];
				//System.out.println("K value: " + K);
			}
		}

		//		for(int i = 0; i < vO.size(); i++){
		//			// first I
		//			if(i == 0){
		//				I = vO.get(i)*vT.get(i);
		//			}
		//			else if(i == 1){
		//				J = vO.get(i)*vT.get(i);
		//			}
		//			else{
		//				K = vO.get(i)*vT.get(i);
		//			}
		//		}
		dP = I+J+K;
		//System.out.println("The dot product is: " + dP + " !!");
		return dP;
	}

	static int[] processJuggStr(String juggStr){
		int[] recept = new int[7];

		//String sampStr = "J J8 H:8 E:2 P:3 C1,C0,C2";
		String sampStr = juggStr;
		String[] tempStr = sampStr.split(" ");
		int i = -1;
		for(String val : tempStr){
			if(i != -1){
				if(i == 0){
					// id = id.replace(".xml", "");
					val = val.replace("J","");
					// keep 1-end : assign to first slot in recept
					//System.out.println("Removed 'J' from 1st position: " + val);
					recept[i] = Integer.parseInt(val);
				}
				else if(i >= 1 && i < 4){
					// split on ':'
					String[] scores = val.split(":");
					recept[i] = Integer.parseInt(scores[1]);
				}
				else{
					// split on ','
					String[] circuits = val.split(",");
					for(String tmp : circuits){
						tmp = tmp.replace("C","");
						recept[i] = Integer.parseInt(tmp);
						i++;
					}
				}
			}
			// iterate loop counter
			i++;
		}
		System.out.println("Here's our reconstituted Juggler string: ");
		for(int j : recept){
			System.out.print(j + " ");
		}
		System.out.println("");
		return recept;
	}

	static int[] processCircStr(String circStr){
		int[] recept = new int[4];

		//String sampStr = "C C2 H:7 E:6 P:4";
		String sampStr = circStr;
		String[] tempStr = sampStr.split(" ");
		int i = -1;
		for(String val : tempStr){
			if(i != -1){
				if(i == 0){
					// id = id.replace(".xml", "");
					val = val.replace("C","");
					// keep 1-end : assign to first slot in recept
					//System.out.println("Removed 'C' from 1st position: " + val);
					recept[i] = Integer.parseInt(val);
				}else{
					// split on ':'
					String[] scores = val.split(":");
					recept[i] = Integer.parseInt(scores[1]);
				}
			}
			// iterate loop counter
			i++;
		}
		System.out.println("Here's our reconstituted Circuit string: ");
		for(int j : recept){
			System.out.print(j + " ");

		}
		System.out.println("");
		return recept;
	}

	//static void detCircuit(){
	//static void detCircuit(int[][] scores){
	static ArrayList<int[][]> detCircuit(int[][] scores){
		ArrayList<int[][]> circuitL = new ArrayList<int[][]>();
		//		int[][] scores = new int[14][3];
		//		// circuits
		//		scores[0][0] = 125;
		//		scores[1][0] = 77;
		//		scores[2][0] = 236;
		//		scores[3][0] = 236;
		//		scores[4][0] = 236;
		//		scores[5][0] = 334;
		//		scores[6][0] = 125;
		//		scores[7][0] = 125;
		//		scores[8][0] = 334;
		//		scores[9][0] = 236;
		//		scores[10][0] = 159;
		//		scores[11][0] = 236;
		//		scores[12][0] = 129;
		//		scores[13][0] = 77;
		//
		//		//scores
		//		scores[0][1] = 89;
		//		scores[1][1] = 526;
		//		scores[2][1] = 2;
		//		scores[3][1] = 2596;
		//		scores[4][1] = 33;
		//		scores[5][1] = 48;
		//		scores[6][1] = 44511254;
		//		scores[7][1] = 22;
		//		scores[8][1] = 5206;
		//		scores[9][1] = 166;
		//		scores[10][1] = 167;
		//		scores[11][1] = 55;
		//		scores[12][1] = 889;
		//		scores[13][1] = 145;
		//
		//		// jugglers
		//		scores[0][2] = 3;
		//		scores[1][2] = 2;
		//		scores[2][2] = 1;
		//		scores[3][2] = 4;
		//		scores[4][2] = 7;
		//		scores[5][2] = 9;
		//		scores[6][2] = 11;
		//		scores[7][2] = 12;
		//		scores[8][2] = 34;
		//		scores[9][2] = 26;
		//		scores[10][2] = 5;
		//		scores[11][2] = 14;
		//		scores[12][2] = 15;
		//		scores[13][2] = 37;

		scores = Jugglers.getSorted(scores,0);

		int colZ = 0;
		int colI = 1;
		int nxt = 0;
		int partStrt = 0;
		int partEnd = 0;
		int partSze = 0;
		//int[][] tmpArr = null;
		for(int i = 0; i < scores.length; i++){
			if(nxt == scores.length){
				nxt--;
			}
			if(nxt < scores.length){
				if(scores[i][colZ] == scores[nxt][colZ]){
					// harvests
//					System.out.println("Got value: " + scores[i][colZ]);
//					System.out.println("+++++++++++++++++++++++++++++++++++");
					if(i == 0){
						partStrt = i;
					}
					if(i == nxt && partStrt > 0){
						System.out.println("At the end ... ?");
						partStrt = partEnd + 1;
						partEnd = i;
						//						System.out.println("Got value: " + scores[i][colZ]);
						//						System.out.println("Partition started at: " + partStrt);
						//						System.out.println("Partition ended at: " + partEnd);
						//						System.out.println("===================================");
						partSze = (partEnd - partStrt) + 1;
						//						System.out.println(">> Size of partition: " + partSze);
						//						System.out.println("-----------------------------------");
						//int[][] tmpArr = new int[partSze][3];
						int[][] tmpArr = new int[partSze][6];
						//int tmpStrtL = partStrt;
						tmpArr = Jugglers.copyPartition(tmpArr, scores, partStrt, partSze);
						tmpArr = Jugglers.getSorted(tmpArr, 1);
						circuitL.add(tmpArr);
						partSze = 0;
					}

				} else {
					if(partStrt == 0 && i == 1){
						// do nada
					}else{
						partStrt = partEnd + 1;
					}
					partEnd = i;
					//					System.out.println("Got value: " + scores[i][colZ]);
					//					System.out.println("Partition started at: " + partStrt);
					//					System.out.println("Partition ended at: " + partEnd);
					//					System.out.println("===================================");
					partSze = (partEnd - partStrt) + 1;
					//					System.out.println(">> Size of partition: " + partSze);
					//
					//					System.out.println("-----------------------------------");
					//int[][] tmpArr = new int[partSze][3];
					int[][] tmpArr = new int[partSze][6];
					//int tmpStrtL = partStrt;
					tmpArr = Jugglers.copyPartition(tmpArr, scores, partStrt, partSze);
					tmpArr = Jugglers.getSorted(tmpArr, 1);
					circuitL.add(tmpArr);
					partSze = 0;
				}
			}
			nxt = i + 2;
		}
		return circuitL;
	}
	private static int[][] getSorted(int[][] arrToSort, final int col){
		Arrays.sort(arrToSort, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o2[col] - o1[col];
			}
		});

		//		for(int l = 0; l < arrToSort.length; l++){
		//			System.out.print("Got elements: " + arrToSort[l][0]);
		//			System.out.print(" " + arrToSort[l][1]);
		//			System.out.println(" " + arrToSort[l][2]);
		//		}
		return arrToSort;
	}

	private static int[][] copyPartition(int[][] newArr, int[][] origArr, int partM, int sze){
		if(sze == 1){
			newArr = null;
			int[][] sglPart = new int[1][3];
			newArr = sglPart;
			System.out.println("Partition value: " + partM);
			System.out.println("Partition value: " + origArr[partM][0]);
			System.out.println("Partition value: " + origArr[partM][1]);

			newArr[0][0] = origArr[partM][0];
			newArr[0][1] = origArr[partM][1];
			newArr[0][2] = origArr[partM][2];
			newArr[0][3] = origArr[partM][2];
			newArr[0][4] = origArr[partM][2];
			newArr[0][5] = origArr[partM][2];
		}else{
			for(int j = 0; j < newArr.length; j++){
				newArr[j][0] = origArr[partM][0];
				newArr[j][1] = origArr[partM][1];
				newArr[j][2] = origArr[partM][2];
				newArr[j][3] = origArr[partM][2];
				newArr[j][4] = origArr[partM][2];
				newArr[j][5] = origArr[partM][2];
				partM++;
			}
		}
		return newArr;
	}
}
