//package project;

import java.util.ArrayList;

public abstract class Predictor {
	// Method Signatures
	public abstract ArrayList<DataPoint> readData(String filename);
	public abstract String test(DataPoint data);
	public abstract double getAccuracy(ArrayList<DataPoint> data);
	public abstract double getPrecision(ArrayList<DataPoint> data);
}
