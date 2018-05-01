package org.wikijava.sound.playWave;
import java.util.ArrayList;


public class KMeans {
	private final static int NUMBER_OF_CLUSTERS = 64;
	private static double chiSquare = 0;
	static double[] sums = new double[NUMBER_OF_CLUSTERS];
	static double[] previousMeans = new double[NUMBER_OF_CLUSTERS];
	static double[] currentMeans = new double[NUMBER_OF_CLUSTERS];
	static double[] normalizedMeans1 = new double[NUMBER_OF_CLUSTERS];
	static double[] normalizedMeans2 = new double[NUMBER_OF_CLUSTERS];
	static double[] normalizedMeans3 = new double[NUMBER_OF_CLUSTERS];
	static double[] normalizedMeans4 = new double[NUMBER_OF_CLUSTERS];
	
	static ArrayList<ArrayList<Double>> clusterNumbersOuter = new ArrayList<ArrayList<Double>>();

	public void sort(ArrayList<Double> hValues) {
		
		//set all of first ArrayLists's values to hValues
	
		boolean flag = true;
		for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
			//add ArrayList to outer to set size of 64
			clusterNumbersOuter.add(new ArrayList<Double>());
		}

		int totalIterations = hValues.size() / NUMBER_OF_CLUSTERS; 

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

		int countOfChangedValues = 0;
		double movedValue;
		do {
			
			double minimum = Double.POSITIVE_INFINITY;
			for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
				sums[i] = 0.0;
				for (int m = 0; m < clusterNumbersOuter.get(i).size(); m++) {
					if (Math.abs((clusterNumbersOuter.get(i).get(m) - currentMeans[i])) < minimum) {
						//change minimum difference
						minimum = Math.abs(clusterNumbersOuter.get(i).get(m) - currentMeans[i]);
						//add value to new cluster	
						movedValue = clusterNumbersOuter.get(i).get(m);
						//remove value from original cluster
						clusterNumbersOuter.get(i).remove(m);
						
						clusterNumbersOuter.get(i).add(movedValue);
						countOfChangedValues++;
					
					}
	
				}
				
				for (int k = 0; k < countOfChangedValues; k++) {
					//recalculate sums
					sums[i] += clusterNumbersOuter.get(i).get(k); 
				}
				
				//recalculate means
				currentMeans[i] = sums[i]/(clusterNumbersOuter.get(i).size());
				
				
			}
			
			if (currentMeans.equals(previousMeans)) {
				flag = false;
				break;
			} else {
				previousMeans = currentMeans.clone();
			}	
			
		} while (flag);
			
	}
	
	public static void divideToFourHistograms() {
		int width = 352;
		int height = 288;
		//top left
		for (int i = 0; i < width/2; i++) {
			for (int j =0; j < height/2; j++) {
				normalizedMeans1[i] = currentMeans[i] * calculateEuclidianDistance(currentMeans[i], currentMeans[j]);
			}
		}
		
		//top right
		for (int i =width/2; i < width; i++) {
			for (int j =0; j < height/2; j++) {
				normalizedMeans2[i] = currentMeans[i] * calculateEuclidianDistance(currentMeans[i], currentMeans[j]);
			}
		}
		
		//bottom left
		for (int i = 0; i < width/2; i++) {
			for (int j =height/2; j < height; j++) {
				normalizedMeans3[i] = currentMeans[i] * calculateEuclidianDistance(currentMeans[i], currentMeans[j]);
			}
		}
		
		//bottom right
		for (int i = width/2; i < width; i++) {
			for (int j =height/2; j < height; j++) {
				normalizedMeans4[i] = currentMeans[i] * calculateEuclidianDistance(currentMeans[i], currentMeans[j]);
			}
		}
		
	}
	
	public static double calculateEuclidianDistance(double h1, double h2) {
		return Math.sqrt(Math.pow(h1-h2, 2));
	}
	
	public static double calculateChiDistance(int queryClusterNumber, int databaseClusterNumber) {
		
		for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
			chiSquare = chiSquare + (Math.pow((clusterNumbersOuter.get(queryClusterNumber).get(i)-clusterNumbersOuter.get(databaseClusterNumber).get(i)),
					2))/(clusterNumbersOuter.get(queryClusterNumber).get(i)+clusterNumbersOuter.get(databaseClusterNumber).get(i));
		}
	
		return chiSquare;
	}
	
	
}
