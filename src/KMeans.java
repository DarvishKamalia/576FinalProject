import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class KMeans {

	public ColorTuple[] createBuckets(ArrayList<ColorTuple> hValues) {

		boolean flag = true;

		ArrayList<ArrayList<ColorTuple>> clusterNumbersOuter = new ArrayList<ArrayList<ColorTuple>>();

		//set all of first ArrayLists's values to hValues
		ColorTuple[] currentMeans = new ColorTuple[Constants.NUMBER_OF_CLUSTERS];
		ColorTuple[] previousMeans = new ColorTuple[Constants.NUMBER_OF_CLUSTERS];

		for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
			//add ArrayList to outer to set size of 64
			clusterNumbersOuter.add(new ArrayList<ColorTuple>());
		}

		int numColorsInEachBucket = hValues.size() / Constants.NUMBER_OF_CLUSTERS;

		for (int f = 0; f < Constants.NUMBER_OF_CLUSTERS; f++) {
			
			for (int i = 0; i < numColorsInEachBucket; i++) {
				
				//divide given hValues ArrayList by number of clusters and distribute evenly to clusterValuesInner to start
				ColorTuple h = hValues.get(0);
				clusterNumbersOuter.get(f).add(h);
				hValues.remove(0);			
			
			}
		
			//arbitrarily set all means to the first value in each cluster to start
			currentMeans[f] = clusterNumbersOuter.get(f).get(0);
			previousMeans[f] = clusterNumbersOuter.get(f).get(0);
		}

		do {
			//each cluster
			for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
				//each value

				for (int k = 0; k < clusterNumbersOuter.get(i).size(); k++) {
					
					ColorTuple currentValue = clusterNumbersOuter.get(i).get(k);

                    int targetIndex = getTargetIndex(clusterNumbersOuter.get(i).get(k), currentMeans);
					
					// Move the value to the cluster at targetIndex 
					
					clusterNumbersOuter.get(i).remove(k); 
					clusterNumbersOuter.get(targetIndex).add(currentValue); 
				}
			}

            double[] sumsR = new double[Constants.NUMBER_OF_CLUSTERS];
            double[] sumsG = new double[Constants.NUMBER_OF_CLUSTERS];
            double[] sumsB = new double[Constants.NUMBER_OF_CLUSTERS];

            for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
				
				for (int k = 0; k < clusterNumbersOuter.get(i).size(); k++) {
					//recalculate sums
					sumsR[i] += clusterNumbersOuter.get(i).get(k).h;
                    sumsG[i] += clusterNumbersOuter.get(i).get(k).s;
                    sumsB[i] += clusterNumbersOuter.get(i).get(k).v;
                }
				
				//recalculate means
				double denominator = clusterNumbersOuter.get(i).size(); 
				currentMeans[i].h = denominator == 0 ? 0 : sumsR[i]/denominator;
                currentMeans[i].s = denominator == 0 ? 0 : sumsG[i]/denominator;
                currentMeans[i].v = denominator == 0 ? 0 : sumsB[i]/denominator;

//				System.out.println(currentMeans[i]);
				
			}

            if (Arrays.equals(currentMeans, previousMeans)) {
                flag = false;
            }
			else {
                for (int i = 0; i < currentMeans.length; i++) {
                        previousMeans[i] = currentMeans[i];
                }
            }
		
		} while (flag);

		ColorTuple[] smoothedMeans = new ColorTuple[Constants.NUMBER_OF_CLUSTERS];

		for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
		    smoothedMeans[i] = new ColorTuple();
        }

        for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
            for (int j=0; j < Constants.NUMBER_OF_CLUSTERS; j++) {
                smoothedMeans[i].h = currentMeans[i].h * calculateEuclidianDistance(currentMeans[i].h, currentMeans[j].h);
                smoothedMeans[i].s = currentMeans[i].s * calculateEuclidianDistance(currentMeans[i].s, currentMeans[j].s);
                smoothedMeans[i].v = currentMeans[i].v * calculateEuclidianDistance(currentMeans[i].v, currentMeans[j].v);
            }
        }

		return smoothedMeans;
	}

    public int getTargetIndex(ColorTuple color, ColorTuple[] currentMeans) {
        ArrayList<Double> minimums = new ArrayList<Double>();

        //each mean
        for (int m = 0; m < currentMeans.length; m++) {
            /*
             * calculate difference for each value (singular) in each cluster (64) with each mean (64)
             * would result in 64 differences for each value
             * 4096  total differences * total number of values = total number of differences
             */

            //minimums.add(Math.abs((clusterNumbersOuter.get(i).get(k) - currentMeans[m])));
            minimums.add(getAbsoluteDistance(currentMeans[m], color));
        }

        double minimum = Collections.min(minimums);

        return minimums.indexOf(minimum);
    }

    public double getAbsoluteDistance (ColorTuple c1, ColorTuple c2) {
	    return Math.sqrt(Math.pow(c1.h - c2.h, 2) + Math.pow(c1.s - c2.s, 2) + Math.pow(c1.v - c2.v, 2));
    }
	
	public double calculateEuclidianDistance(double h1, double h2) {
		return Math.sqrt(Math.pow(h1-h2, 2));
	}

	public static double calculateChiDistance(int[] query, int[] database) {
		double chiSquare = 0;

		for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
			for (int j = 0; j < Constants.NUMBER_OF_CLUSTERS;j++) {
				//chiSquare = chiSquare + (Math.pow((query[i]-database.get(i).size()), 2))/(query).get(i).size()+database.get(i).size();
                double denominator = (double) (query[i] + database[i]);
				chiSquare += denominator == 0 ? 0 : Math.pow(query[i]-database[i], 2) / denominator;
			}
		}
	
		return chiSquare;
	}
	
}
