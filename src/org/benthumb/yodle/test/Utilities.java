package org.benthumb.yodle.test;

import java.util.logging.Logger;

//import java.math.*;

public class Utilities {

	// ** Logging **
	static Logger logMsg = Logger.getLogger("Utilities");
	
	public static Double calculateMean(int[] scores){
		
		int N = scores.length;
		Double sum = (double) 0;

		for (int i = 0; i < N; i++) {
			sum += scores[i];
		}
		// find mean
		return (Double)sum / N;
	}

	public static Double calculateStdDev(int[] scores, Double mean) {
		int N = scores.length;

		// find numerator
		float num = 0;
		for (int j = 0; j < N; j++) {
			int score = scores[j];
			num += Math.pow((score - mean), 2);
		}

		// find standard deviation
		logMsg.log(java.util.logging.Level.INFO, "sanity test: The square root of four is " + Math.pow(4, 0.5));
		Double stdDev = (Double) Math.pow((num/N), 0.5); 
		logMsg.log(java.util.logging.Level.INFO,
				">> calculated standard deviation: " + stdDev);
		return stdDev;

	}

	public static String arrayToString(int[] a) {
		String separator = " ";
		StringBuffer result = new StringBuffer();
		if (a.length > 0) {
			result.append(a[0]);
			for (int i = 1; i < a.length; i++) {
				result.append(separator);
				result.append(a[i]);
			}
		}
		return result.toString();
	}
	
	public static double caculateRatio(Double stdDev, Double mean, int score){
		
		return 2.3;
		
	}
}
