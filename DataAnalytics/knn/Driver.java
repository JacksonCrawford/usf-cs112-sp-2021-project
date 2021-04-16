package part2;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Driver {
		//KNNPredictor predictor = new KNNPredictor(22);
		
	public static void gui(KNNPredictor predictor, ArrayList<DataPoint> datapoints) {
		JFrame dpFrame = new JFrame("DataPoints!");
		// Create JPanel
		JPanel panel = new JPanel();
		// Set JPanel layout to FlowLayout
		panel.setLayout(new FlowLayout());
		
		// Create JLabel's that display precision and accuracy (formatted for percentages)
		JLabel accuLabel = new JLabel("DataPoint Accuracy: " + String.format("%.2f", predictor.getAccuracy(datapoints)) + "%");
		JLabel precLabel = new JLabel(" | DataPoint Precision: " + String.format("%.2f", predictor.getPrecision(datapoints)) + "%");
		
		// Add JLabels to JPanel
		panel.add(accuLabel);
		panel.add(precLabel);
		// Add JPanel to JFrame
		dpFrame.add(panel);
		// Set size of JFrame to 650px650p
		dpFrame.setSize(650, 650);
		// Make JFrame visible
		dpFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a number of points to test against: ");
		
		int k = input.nextInt();
		
		input.close();
		
		KNNPredictor predictor = new KNNPredictor(k);
		ArrayList<DataPoint> data = predictor.readData("titanic.csv");
		System.out.println("survived: " + predictor.getAlive());
		System.out.println("drowned: " + predictor.getNotAlive());
		System.out.println("tests: " + predictor.getTests().size());
		System.out.println("Accuracy: " + predictor.getAccuracy(data));
		System.out.println("Precision: " + predictor.getPrecision(data));
		gui(predictor, predictor.readData("titanic.csv"));
		
		
	}

}
