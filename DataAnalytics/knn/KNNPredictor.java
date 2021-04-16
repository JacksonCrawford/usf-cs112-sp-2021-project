package part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.lang.NumberFormatException;
import java.util.Random;
import java.lang.Math;

public class KNNPredictor extends Predictor {
	private int alive = 0;
	private int notAlive = 0;
	private int k;
	public ArrayList<DataPoint> dataList;
	public ArrayList<DataPoint> tests = new ArrayList<DataPoint>();
	
	// Constructor
	public KNNPredictor(int kParam) {
		this.k = kParam;
	}
	
	// Default Constructor
	public KNNPredictor() {
		this.k = 0;
	}
	
	
	// Reads a file and creates an ArrayList of DataPoints from data that is returned
	public ArrayList<DataPoint> readData(String filename) {
		// Initialize ArrayLists to be used later
		ArrayList<DataPoint> datapoints = new ArrayList<DataPoint>();
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<Double> ages = new ArrayList<Double>();
		ArrayList<Double> fares = new ArrayList<Double>();
		ArrayList<Integer> pClasses = new ArrayList<Integer>();
		ArrayList<Integer> sexes = new ArrayList<Integer>();
		ArrayList<Double> labels = new ArrayList<Double>();
		// Initialize new Random object
		Random rand = new Random();
		
		try {
			// Creates File object from argument "filename"
			File data = new File(filename);
			// Creates Scanner object to read from file
			Scanner inFile = new Scanner(data);
			// Sets delimiter to "," because we are reading a .csv
			inFile.useDelimiter(",");
			// These two are used for dealing with the "\n" instead of a comma in the fares column
			String fare;
			String[] slices = new String[2];
			
			// Add next value to ArrayList values
			while(inFile.hasNextLine()) { values.add(inFile.next()); }
			
			// For Loop that starts at the first value of survived column
			for(int i=7;i<values.size();i+=6) {
				// Checks if survived is 1 or 0, and adds a 1 or 0 to ArrayList labels when appropriate
				if(Integer.parseInt(values.get(i-1)) == 1) { labels.add(1.0);
					} else { labels.add(0.0); }
				
				try { if(values.get(i+2) == "male") { sexes.add(0); } else { sexes.add(1); }
					} catch (NumberFormatException nfe) { sexes.add(null); }
				
				// Adds age to ages, 0.0 if age is empty
				try { ages.add(Double.valueOf(values.get(i+3)));
					} catch(NumberFormatException nfe) { ages.add(0.0); }
				
				/* Here, the problem was that the data was not separated on every new line as well, so this is a workaround. */
				// Gets fare
				fare = values.get(i+4);
				// Creates array of fare that splits on "\n"
				slices = fare.split("\n");
				// Adds the real fare to fares, 0.0 if fare is empty
				try { fares.add(Double.valueOf(slices[0]));
					} catch(NumberFormatException nfe) { fares.add(0.0); }
				
				try {pClasses.add(Integer.parseInt(slices[1]));
					} catch(NumberFormatException nfe) { pClasses.add(0);
					} catch(ArrayIndexOutOfBoundsException bounds) {}
			}
			
			// Creates DataPoint objects for each read value and sets 90% of them to train, 10% to test.
			for(int j=0;j<fares.size()-1;j++) {
				// Adds if random number is less than or equal to .9
				if(rand.nextDouble() <= 0.9) {
					datapoints.add(new DataPoint(ages.get(j), fares.get(j), pClasses.get(j), sexes.get(j), labels.get(j), false));
				// Adds if random number is greater than .9, and adds point to ArrayList "tests"
				} else {
					datapoints.add(new DataPoint(ages.get(j), fares.get(j), pClasses.get(j), sexes.get(j), labels.get(j), true));
					this.tests.add(new DataPoint(ages.get(j), fares.get(j), pClasses.get(j), sexes.get(j), labels.get(j), true));
				}
			}
			
			// Initialize counter for enhanced for loop
			int counter = 0;
			// Enhanced for loop that iterates through points in datapoints
			for(DataPoint point : datapoints) {
				// Increment alive or notAlive if point is not a testpoint
				if(labels.get(counter) == 1.0 && !point.getIsTest()) {
					alive++;
				} else if(labels.get(counter) == 0.0 && !point.getIsTest()) {
					notAlive++;
				}
				// Incrememnt counter
				counter++;
			}
			
			// Closes Scanner
			inFile.close();
			
		// Catches FileNotFoundException
		} catch(FileNotFoundException fnfe) {
			System.out.println("File Not Found!");
		}
		
		// Returns datapoints ArrayList
		this.dataList = datapoints;
		return datapoints;
	}
	private double getDistance(DataPoint p1, DataPoint p2) {
		double x1 = p1.getf1();
		double x2 = p2.getf1();
		double y1 = p1.getf2();
		double y2 = p2.getf2();
		
		// Calculate square root of ((x_2 - x_1)^2 + (y_2 - y_1)^2) where x and y refer to f1 and f2
		double distance1 = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
		
		x1 = p1.getf3();
		x2 = p2.getf3();
		y1 = p1.getf4();
		y2 = p2.getf4();

		// Calculate square root of ((x_2 - x_1)^2 + (y_2 - y_1)^2) where x and y refer to f3 and f4
		double distance2 = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
		
		// Return mean of two distances
		return (distance1 + distance2) / 2;
	}

	// Returns text stating which point is the largest
	public int test(DataPoint datapoint) {
		// Check if datapoint is a test point
		try {
			// Check that point is a test point
			check(datapoint);
			// Create 2D array named distlabel with 2 columns and however many test points rows
			double[][] distLabel = new double[tests.size()][2];
			// For loop to fill 2D array
			for(int i=0;i<tests.size();i++) {
				distLabel[i][0] = getDistance(datapoint, tests.get(i));
				distLabel[i][1] = tests.get(i).getLabel();
			}
			
			// Lambda to sort array (requires Java 8+)
			Arrays.sort(distLabel, Comparator.comparingDouble(o -> o[0]));
			
			// Create 2d array to fill with k elements
			double[] slice = new double[k];
			// For loop to fill slice with labels respective index values from distLabel
			for(int j=0;j<k;j++) { slice[j] = distLabel[j][1]; }
			// Initialize variables for counting labels
			int lived = 0; int notLived = 0;
			// Increments lived and notLived according to label values in slice
			for( double label : slice ) {
				if(label == 1.0) { lived++;
					} else if(label == 2.0) { notLived++; }
			}
			
			// Returns 1 or 0 if lived or notLived is greatest
			if(lived > notLived) { return 1;
				} else if(lived < notLived) { return 0; }
			
			// Returns 2 if lived and notLived are the same 
			return 2;
		} catch(CustomException err) { 
			System.out.println("argued datapoint was not a test point");
			return 2; }
	}
	
	public double getAccuracy(ArrayList<DataPoint> datapoints) {
		int[] testLabels = new int[datapoints.size()];
		// Loop to get test points
		for(int i=0;i<datapoints.size();i++) {
			if(datapoints.get(i).getIsTest()) {
				testLabels[i] = (int)datapoints.get(i).getLabel();
			}
		}
		
		// Initialize variables for looping and calculating
		int tPos = 0; int tNeg = 0; int fPos = 0; int fNeg = 0;
		int testLabel; int pointLabel; int counter = 0; int tc = 0;
		
		for(DataPoint point : datapoints) {
			pointLabel = (int)point.getLabel();
			testLabel = testLabels[counter];
			if(testLabels[counter] == 2) { System.out.println(tc); tc++; }
			
			if(testLabel == 1 && pointLabel == 1) { tPos++;
				} else if(testLabel == 0 && pointLabel == 0) { tNeg++; 
				} else if(testLabel == 1 && pointLabel == 0) { fPos++;
				} else if(testLabel == 0 && pointLabel == 1) { fNeg++; }
			counter++;
		}
		
		double sum = tPos + tNeg + fPos + fNeg;
		double accuracy = (tPos + tNeg) / sum;
		return accuracy * 100;
	}
	
	// Returns total difference of points compared
	public double getPrecision(ArrayList<DataPoint> datapoints) {
		ArrayList<Integer> testLabels = new ArrayList<Integer>();
		// Loop to get test points
		for(int i=0;i<datapoints.size();i++) {
			if(datapoints.get(i).getIsTest()) {
				testLabels.add((int)datapoints.get(i).getLabel());
			}
		}
		
		// Initialize variables for looping and calculating
		int tPos = 0; int fNeg = 0;
		int pointLabel; int counter = 0;
		
		for(int testLabel : testLabels) {
			pointLabel = (int)datapoints.get(counter).getLabel();
			
			// fPos and tNeg are not needed for calculation, and are therefore excluded to save memory
			if(testLabel == 1 && pointLabel == 1) { tPos++;
				} else if(testLabel == 0 && pointLabel == 1) { fNeg++; }
			counter++;
		}
		
		double sum = (tPos + fNeg);
		double precision = tPos / sum;
		
		return precision * 100;
	}
	
	// Custom Exception to check if datapoint is test or not
	static void check(DataPoint point) throws CustomException {
		if(!point.getIsTest()) {
			throw new CustomException("argued datapoint was not a test point");
		}
		
	}
	
	// Returns number of survived passengers
	public int getAlive() {
		return alive;
	}
	
	// Returns number of passengers who did not survive
	public int getNotAlive() {
		return notAlive;
	}
	
	public ArrayList<DataPoint> getTests() {
		return this.tests;
	}

}
