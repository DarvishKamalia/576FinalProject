package org.wikijava.sound.playWave;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class KMeans {
	private final static int NUMBER_OF_CLUSTERS = 64;
	private static double chiSquare = 0;
	static double[] sums = new double[NUMBER_OF_CLUSTERS];
	public static double[] previousMeans = new double[NUMBER_OF_CLUSTERS];
	public double[] currentMeans = new double[NUMBER_OF_CLUSTERS];

	
	double minimum;
	boolean flag = true;
	
	static ArrayList<ArrayList<Double>> clusterNumbersOuter = new ArrayList<ArrayList<Double>>();
	static ArrayList<ArrayList<Double>> clusterNumbersOuterCopy = new ArrayList<ArrayList<Double>>();
	static ArrayList<ArrayList<Double>> normalizedMeans = new ArrayList<ArrayList<Double>>();

	public ArrayList<ArrayList<Double>> sort(ArrayList<Double> hValues) {
		
		//set all of first ArrayLists's values to hValues
	
		for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
			//add ArrayList to outer to set size of 64
			clusterNumbersOuter.add(new ArrayList<Double>());
		}

		int totalIterations = 1584; 

		for (int f = 0; f < NUMBER_OF_CLUSTERS; f++) {
			
			for (int i = 0; i < totalIterations; i++) {
				
				//divide given hValues ArrayList by number of clusters and distribute evenly to clusterValuesInner to start
				double h = hValues.get(0);
				clusterNumbersOuter.get(f).add(h);
				hValues.remove(0);			
			
			}
		
			//arbitrarily set all means to the first value in each cluster to start
			currentMeans[f] = clusterNumbersOuter.get(f).get(0);		
		}

		do {
			
			//each cluster
			for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
				//each value
				sums[i] =0;
				for (int k = 0; k < clusterNumbersOuter.get(i).size(); k++) {
					
					double currentValue = clusterNumbersOuter.get(i).get(k); 
										
					ArrayList<Double> minimums = new ArrayList<Double>();
					
					//each mean
					for (int m = 0; m < currentMeans.length; m++) {
						/*
						 * calculate difference for each value (singular) in each cluster (64) with each mean (64) 
						 * would result in 64 differences for each value 
						 * 4096  total differences * total number of values = total number of differences 
						 */
						
						minimums.add(Math.abs((clusterNumbersOuter.get(i).get(k) - currentMeans[m])));	
					}
					
					minimum = Collections.min(minimums);
					
					int targetIndex = minimums.indexOf(minimum);
					
					// Move the value to the cluster at targetIndex 
					
					clusterNumbersOuter.get(i).remove(k); 
					clusterNumbersOuter.get(targetIndex).add(currentValue); 
				}
			}
		
			for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
				
				for (int k = 0; k < clusterNumbersOuter.get(i).size(); k++) {
					//recalculate sums
					sums[i] += clusterNumbersOuter.get(i).get(k); 
				
				}
				
				//recalculate means
				double denominator = clusterNumbersOuter.get(i).size(); 
				currentMeans[i] = denominator == 0 ? 0 : sums[i]/denominator;
//				System.out.println(currentMeans[i]);
				
			}
		
			
			
			for (int i = 0; i < currentMeans.length; i++) {
				if (Arrays.equals(currentMeans, previousMeans)) {
					flag = false;
					break;
				} else {
					
					previousMeans[i] = currentMeans[i];	
					
				}			
			}
		
		} while (flag);

		return clusterNumbersOuter;
	}
	
	public double calculateEuclidianDistance(double h1, double h2) {
		return Math.sqrt(Math.pow(h1-h2, 2));
	}
	
	public static double calculateChiDistance(ArrayList<ArrayList<Double>> query, ArrayList<ArrayList<Double>> database) {
		
		for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
			for (int j = 0; j < NUMBER_OF_CLUSTERS;j++) {
				chiSquare = chiSquare + (Math.pow((query.get(i).size()-database.get(i).size()),
						2))/(query).get(i).size()+database.get(i).size();
			}
			
		}
	
		return chiSquare;
	}
	
}
