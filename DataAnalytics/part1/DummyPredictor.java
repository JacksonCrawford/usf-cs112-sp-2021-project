import java.util.ArrayList;
/*
 * To be used with readData when implemented, unnecessary now
 */
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;


public class DummyPredictor extends Predictor {
	// Just returns datapoint ArrayList for now ;)
	public ArrayList<DataPoint> readData(String filename) {
		ArrayList<DataPoint> dataPoint = new ArrayList<DataPoint>();
		/*File data = new File(filename);
		Scanner inFile = new Scanner(data);
		
		int line = 1;
		while(inFile.hasNextLine()) {
			dataPoint.add(new DataPoint());
		}
		
		inFile.close();*/
		return dataPoint;
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

}
