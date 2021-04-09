package part2;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.NumberFormatException;
import java.util.Random;

public class KNNPredictor extends Predictor {
	private int alive = 0;
	private int notAlive = 0;
	// Reads a file and creates an ArrayList of DataPoints from data that is returned
	public ArrayList<DataPoint> readData(String filename) {
		ArrayList<DataPoint> datapoints = new ArrayList<DataPoint>();
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<Double> ages = new ArrayList<Double>();
		ArrayList<Double> fares = new ArrayList<Double>();
		ArrayList<Boolean> labels = new ArrayList<Boolean>();
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
			String survived;
			String[] slices = new String[2];
			
			// Add next value to ArrayList values
			while(inFile.hasNextLine()) { values.add(inFile.next()); }
			
			// For Loop that starts at the first value of survived column
			for(int i=7;i<values.size();i+=6) {
				// Checks if survived is 1 or 0, and adds a bool true for 1, false for 0 to labels
				if(Integer.parseInt(values.get(i-1)) == 1) { labels.add(true);
					} else { labels.add(false); }
				// Adds age to ages, 0.0 if age is empty
				try { ages.add(Double.valueOf(values.get(i+3)));
					} catch(NumberFormatException nfe) { ages.add(0.0); }
				// Gets fare
				fare = values.get(i+4);
				// Creates array of fare that splits on "\n"
				slices = fare.split("\n");
				// Adds fare to fares, 0.0 if fare is empty
				try { fares.add(Double.valueOf(slices[0]));
					} catch(NumberFormatException nfe) { fares.add(0.0); }
			}
			
			// Creates DataPoint objects for each read value
			for(int j=0;j<ages.size();j++) {
				if(rand.nextDouble() < 0.9) {
					datapoints.add(new DataPoint(ages.get(j), fares.get(j), false, false));
				} else {
					datapoints.add(new DataPoint(ages.get(j), fares.get(j), false, true));
				}
			}
			
			int counter = 0;
			for(DataPoint point : datapoints) {
				if(labels.get(counter) == true) {
					point.setLabel(true);
					alive++;
				} else {
					point.setLabel(false);
					notAlive++;
				}
				counter++;
			}
			
			// Closes Scanner
			inFile.close();
			
		// Catches FileNotFoundException
		} catch(FileNotFoundException fnfe) {
			System.out.println("File Not Found!");
		}
		
		// Returns datapoints ArrayList
		return datapoints;
	}
	
	// Returns text stating which point is the largest
	public String test(DataPoint data) {
		if(data.getf1() > data.getf2()) {
			return "f1 is greater than f2!";
		} else if(data.getf1() < data.getf2()) {
			return "f2 is greater than f1!";
		} else {
			return "f1 and f2 are the same!";
		}
	}
	
	// Returns sum of all points
	public double getAccuracy(ArrayList<DataPoint> data) {
		double sum = 0;
		for(int i=0;i<data.size();i++) {
			sum += (data.get(i)).getf1() + (data.get(i)).getf2();
		}
		return sum;
	}
	
	// Returns total difference of points compared
	public double getPrecision(ArrayList<DataPoint> data) {
		double diff = 0;
		for(int i=0;i<data.size();i++) {
			diff += (data.get(i)).getf1() - (data.get(i)).getf2();
		}
		return diff;
	}
	
	// Returns number of survived passengers
	public int getAlive() {
		return alive;
	}
	
	// Returns number of passengers who did not survive
	public int getNotAlive() {
		return notAlive;
	}

}
