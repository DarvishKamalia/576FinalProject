import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.wikijava.sound.playWave.KMeans;
import java.awt.Color;

public class Analyzer {
	private static float[] hsbValues = {0,0,0};
	private final static int NUMBER_OF_CLUSTERS = 64;
	
	public static double[] bucketTo4096(double r, double g, double b) {
		int rCount = 0;
		int gCount = 0;
		int bCount = 0;
		double[] rgbValues = {r, g, b};
		while (r < 256 && g < 256 && b < 256) {
			if (r >=0 && r < 16 && g >=0 && g < 16 && b >=0 && b < 16) {
				rgbValues[0] = 7.5 + rCount * 16;
				rgbValues[1] = 7.5 + gCount * 16;
				rgbValues[2] = 7.5 + bCount * 16;
				break;
			} else if (r >=16) {
				r=r-16;
				rCount++;
			} else if (g>=16) {
				g = g-16;
				gCount++;
			} else if (b>=16) {
				b = b - 16;
				bCount++;
			}
		}
		return rgbValues;
	}
	
	public static double[][] calculateRGB(String myFile) {
		double redValue= 0, greenValue = 0, blueValue = 0;
		int width = 352;
		int height = 288;
		int pix = 0;
		double[][] pixelValues = new double[height][width];
		BufferedImage myImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		try {
			File file = new File(myFile);
			InputStream is = new FileInputStream(file);

			long len = file.length();
			byte[] bytes = new byte[(int)len];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}


			int ind = 0;
			for(int y = 0; y < height; y++){

				for(int x = 0; x < width; x++){

					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 

					pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					pixelValues[height][width] = pix;
				
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					myImage.setRGB(x,y,pix);
					ind++;
					
					redValue = (pix >> 16) & 0xFF;
					greenValue = (pix >> 8) & 0xFF;
					blueValue = pix & 0xFF;
				}
			}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pixelValues;
	}
	
	public static float[] RGBtoHSB(double r, double g, double b) {
		
		return Color.RGBtoHSB((int)r, (int)g, (int)b, hsbValues);
		
	}
	

	
	public static void firstKMeansClusteringH(double h) {
		boolean flag = false;
		do {
			
		} while(flag);
		
		KMeans kmeans = new KMeans();
		
		int hCount = 0;
	
		while (h <= 1) {
			if (h >= 0 && h < 0.25) {
				h = 0.125 + 0.25 * hCount;

				for (int i = 0; i < 64; i++) {
					kmeans.setHValue(h);
				}
				break;
			} else if (h >= 0.25) {
				h = h - 0.25;
				hCount++;
			} 
	}
}

	public static void storeEuclidianDistances() {
		KMeans euclid = new KMeans();
		for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
	
		}
	}
	
	public static void calculateVideos() {
		
	}
	
	public static void insertionSort(double array[]) {
		 int n = array.length;  
		 for (int j = 1; j < n; j++) {  
			 double key = array[j];  
		     int i = j-1;  
		     while ( (i > -1) && ( array [i] > key ) ) {  
		    	 array [i+1] = array [i];  
		         i--;  
		     }  
		     	array[i+1] = key;  
		     }  
		    
	}
	
	//finish this
	public static void createHistogram() {
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
	}
	
	public static void main(String[] args) {
		double[][] rgbValues = calculateRGB("first001.rgb");
		double redValue = 0, greenValue = 0, blueValue = 0;
		int width = 352;
		int height = 288;
		
		ArrayList<Double> hValues = new ArrayList<Double>();
		
		KMeans kMeans  = new KMeans();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				redValue = ((int) rgbValues[width][height] >> 16) & 0xFF;
				greenValue = ((int) rgbValues[width][height] >> 8) & 0xFF;
				blueValue = (int) rgbValues[width][height] & 0xFF;
				
				double[] bucketedRGB = bucketTo4096(redValue, greenValue, blueValue);
				
				for (int k = 0; k < NUMBER_OF_CLUSTERS;k++) {
					float[] hsbValues = RGBtoHSB(bucketedRGB[0], bucketedRGB[1], bucketedRGB[2]);
					hValues.add((double) hsbValues[0]);
				}
			}
		}
		
		kMeans.sort(hValues);
		
		
		
			
	}

}
