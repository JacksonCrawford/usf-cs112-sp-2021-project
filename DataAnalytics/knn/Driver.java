package part2;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Driver {
	
	public static void main(String[] args) {
		KNNPredictor predictor = new KNNPredictor();
//		System.out.println(dpred.readData("titanic.csv"));
		predictor.readData("titanic.csv");
		System.out.println("survived: " + predictor.getAlive());
		System.out.println("not survived: " + predictor.getNotAlive());
		
	}

}
