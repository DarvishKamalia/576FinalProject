//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class Analyzer {
//	private static float[] hsbValues = {0,0,0};
//	private final static int NUMBER_OF_CLUSTERS = 64;
//
//	public static double[] bucketTo4096(double h, double s, double v) {
//		int rCount = 0;
//		int gCount = 0;
//		int bCount = 0;
//		double[] rgbValues = {h, s, v};
//		while (h < 256 && s < 256 && v < 256) {
//			if (h >=0 && h < 16 && s >=0 && s < 16 && v >=0 && v < 16) {
//				rgbValues[0] = 7.5 + rCount * 16;
//				rgbValues[1] = 7.5 + gCount * 16;
//				rgbValues[2] = 7.5 + bCount * 16;
//				break;
//			} else if (h >=16) {
//				h=h-16;
//				rCount++;
//			} else if (s>=16) {
//				s = s-16;
//				gCount++;
//			} else if (v>=16) {
//				v = v - 16;
//				bCount++;
//			}
//		}
//		return rgbValues;
//	}
//
//	public static double[][] calculateRGB(String myFile) {
//		double redValue= 0, greenValue = 0, blueValue = 0;
//		int width = 352;
//		int height = 288;
//		int pix = 0;
//		double[][] pixelValues = new double[height][width];
//		BufferedImage myImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//		try {
//			File file = new File(myFile);
//			InputStream is = new FileInputStream(file);
//
//			long len = file.length();
//			byte[] bytes = new byte[(int)len];
//
//			int offset = 0;
//			int numRead = 0;
//			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
//				offset += numRead;
//			}
//
//			int ind = 0;
//			for(int y = 0; y < height; y++){
//
//				for(int x = 0; x < width; x++){
//
//					byte a = 0;
//					byte h = bytes[ind];
//					byte s = bytes[ind+height*width];
//					byte v = bytes[ind+height*width*2];
//
//					pix = 0xff000000 | ((h & 0xff) << 16) | ((s & 0xff) << 8) | (v & 0xff);
//					pixelValues[y][x] = pix;
//
//					//int pix = ((a << 24) + (h << 16) + (s << 8) + v);
//					myImage.setRGB(x,y,pix);
//					ind++;
//
//					redValue = (pix >> 16) & 0xFF;
//					greenValue = (pix >> 8) & 0xFF;
//					blueValue = pix & 0xFF;
//				}
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return pixelValues;
//	}
//
//	public static float[] RGBtoHSB(double h, double s, double v) {
//		return Color.RGBtoHSB((int)h, (int)s, (int)v, hsbValues);
//	}
//
//	//finish this
//	public static void storeEuclidianDistances() {
//		KMeans euclid = new KMeans();
//		for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
//
//		}
//	}
//
//
//	public static void insertionSort(double array[]) {
//		 int n = array.length;
//		 for (int j = 1; j < n; j++) {
//			 double key = array[j];
//		     int i = j-1;
//		     while ( (i > -1) && ( array [i] > key ) ) {
//		    	 array [i+1] = array [i];
//		         i--;
//		     }
//		     	array[i+1] = key;
//		     }
//
//	}
//
//	public static void main(String[] args) {
//		double[][] rgbValues = calculateRGB("flower.rgb");
//		double redValue = 0, greenValue = 0, blueValue = 0;
//		int width = 352;
//		int height = 288;
//
//		KMeans kMeans  = new KMeans();
//
//		ArrayList<Double> hValues = new ArrayList<Double>();
//
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++) {
//
//				redValue = ((int) rgbValues[j][i] >> 16) & 0xFF;
//				greenValue = ((int) rgbValues[j][i] >> 8) & 0xFF;
//				blueValue = (int) rgbValues[j][i] & 0xFF;
//
//				double[] bucketedRGB = bucketTo4096(redValue, greenValue, blueValue);
//
//
//				float[] hsbValues = RGBtoHSB(bucketedRGB[0], bucketedRGB[1], bucketedRGB[2]);
//				hValues.add((double) hsbValues[0]);
//
//			}
//		}
//
//		kMeans.sort(hValues);
//
//		for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
//			for (int j=0; j < NUMBER_OF_CLUSTERS; j++) {
//				normalizedMeans1[i] = kMeans.currentMeans[i] * kMeans.calculateEuclidianDistance(kMeans.currentMeans[i], kMeans.currentMeans[j]);
//			}
//		}
//
//		System.out.println(Arrays.toString(normalizedMeans1));
//
//
//	}
//
//}
