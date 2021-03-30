import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Driver {
	public static void gui(ArrayList<DataPoint> datapoints) {
		// Create DummyPredictor instance
		DummyPredictor dpred = new DummyPredictor();
		
		// Create JFrame
		JFrame dpFrame = new JFrame("DataPoints!");
		// Create JPanel
		JPanel panel = new JPanel();
		// Set JPanel layout to FlowLayout
		panel.setLayout(new FlowLayout());
		
		// Create JLabel's that display precision and accuracy
		JLabel precLabel = new JLabel("DataPoint Precision: " + dpred.getPrecision(datapoints));
		JLabel accuLabel = new JLabel(" | DataPoint Accuracy: " + dpred.getAccuracy(datapoints));
		
		// Add JLabels to JPanel
		panel.add(precLabel);
		panel.add(accuLabel);
		// Add JPanel to JFrame
		dpFrame.add(panel);
		// Set size of JFrame to 650px650p
		dpFrame.setSize(650, 650);
		// Make JFrame visible
		dpFrame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// Create Random instance
		Random rand = new Random();
		// Create training datapoint
		DataPoint trainPoint = new DataPoint(rand.nextDouble(), rand.nextDouble(), "blue", false);
		// Create regular datapoint
		DataPoint testPoint = new DataPoint(rand.nextDouble(), rand.nextDouble(), "green", true);
		
		// Write results to file "datapoints.txt"
		try {
			FileWriter writer = new FileWriter("datapoints.txt");
			writer.write("Train point: " + Double.toString(trainPoint.getf1()) + " : " + Double.toString(trainPoint.getf2()) + 
					" : " + trainPoint.getLabel() + " : " + Boolean.toString(trainPoint.getIsTest()));
			writer.write("\nTest point: " + Double.toString(testPoint.getf1()) + " : " + Double.toString(testPoint.getf2()) + 
					" : " + testPoint.getLabel() + " : " + Boolean.toString(testPoint.getIsTest()));
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		// Create ArrayList of type DataPoint and size 2
		ArrayList<DataPoint> datapoints = new ArrayList<DataPoint>(2);
		
		// Add training and regular datapoints to ArrayList
		datapoints.add(trainPoint);
		datapoints.add(testPoint);
		
		// Call gui with ArrayList as argument
		gui(datapoints);
		
	}

}
