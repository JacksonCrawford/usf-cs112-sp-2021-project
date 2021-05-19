package part4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.SwingUtilities;


import part2.DataPoint;
import part2.KNNPredictor;

public class Graph extends JPanel {

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    // TODO: Add point colors for each type of data point
    private Color pointColor = new Color(255, 0, 255);
    private Color red = new Color(255, 0, 0);

    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    
    private Color truePos = new Color(0, 0, 255);
    private Color falsePos = new Color(0, 255, 255);
    private Color falseNeg = new Color(255, 255, 0);
    private Color trueNeg = new Color(255, 0, 0);

    // TODO: Change point width as needed
    private static int pointWidth = 10;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private static ArrayList<DataPoint> data;
    private ArrayList<DataPoint> tests;

    // TODO: Add a private KNNPredictor variable
    private static KNNPredictor predictor;
    private double accuracy;
    private double precision;
    
    static final int MIN_K = 2;
    static final int MAX_K = 25;
    static final int INIT_K = 5;
    
    public static int k = 5;
    	
	/**
	 * Constructor method
	 */
    public Graph(int K, String fileName) {
    	this.predictor = new KNNPredictor(K);

    	data = predictor.readData(fileName);

    	this.data = data;
    	this.tests = predictor.getTests();
    	this.accuracy = predictor.getAccuracy(data);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
//        this.addMouseListener(this);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw the main axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        // Draw the points
        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

//    public void mouseClicked(MouseEvent me) {
//    	int xCoord = (me.getX() - 81) / 10;
//    	int yCoord = (me.getY() - 41);
//    	System.out.println(xCoord + ", " + yCoord);
//	}
    
    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);
        int predStatus;
        double realStatus;
        for (int i = 0; i < tests.size(); i++) {
            int x1 = (int) ((tests.get(i).getf1() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - tests.get(i).getf2()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            
            predStatus = predictor.test(tests.get(i));
            realStatus = tests.get(i).getLabel();
            if(predStatus == 1 && realStatus == 1.0) {
            	g2.setColor(truePos);
            } else if(predStatus == 1 && realStatus == 0.0) {
            	g2.setColor(falsePos);
            } else if(predStatus == 0 && realStatus == 1.0) {
            	g2.setColor(falseNeg);
            } else if(predStatus == 0 && realStatus == 0.0) {
            	g2.setColor(trueNeg);
            }
            

//            if (i % 2 == 0) {
//                g2.setColor(pointColor);
//            } else {
//                g2.setColor(red);
//            }
            g2.fillOval(x, y, ovalW, ovalH);
        }

    }

    
    
    /*
     * @Return the min values
     */
    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getf1());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getf2());
        }
        return minData;
    }


    /*
     * @Return the max values;
     */
    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getf1());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getf2());
        }
        return maxData;
    }

    /* Mutator */
    public void setData(ArrayList<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }
    
    public double fetchAccuracy() {
    	return predictor.getAccuracy(data);
    }
    
    public double fetchPrecision() {
    	return predictor.getPrecision(data);
    }
    
    private static void sliderSetup(JSlider slider) {
    	slider.setMajorTickSpacing(5);
    	slider.setMinorTickSpacing(1);
    	slider.setSnapToTicks(true);
    	slider.setPaintTicks(true);
    	slider.setPaintLabels(true);
    	slider.setPreferredSize(new Dimension(700, 90));
    }

    private static void paramSelect() {
    	JFrame frame = new JFrame("Select Parameters");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLayout(new FlowLayout());
    	
    	Container contentPane = frame.getContentPane();
    	
    	/*** JSlider ***/
        JSlider kSelector = new JSlider(JSlider.HORIZONTAL, MIN_K, MAX_K, INIT_K);
        sliderSetup(kSelector);
        
        /*** JLabel ***/
        JLabel label = new JLabel("Choose the majority value");
        
    	/*** JButton ***/
        JButton start = new JButton("Run Test");
        start.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		k = (kSelector.getValue() * 2) + 1;
        		createAndShowGuiGraph(k, "titanic.csv");
        		createAndShowGuiDP(predictor, data);
        		frame.dispose();
        	}
        });
        
        contentPane.add(label);
        contentPane.add(kSelector);
        contentPane.add(start);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /*  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    private static void createAndShowGuiGraph(int K, String fileName) {
        Graph mainPanel = new Graph(K, fileName);

        // Feel free to change the size of the panel
        mainPanel.setPreferredSize(new Dimension(700, 600));

        /* creating the frame */
        JFrame frame = new JFrame("CS 112 Lab Part 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        contentPane.add(mainPanel);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    
    
    public static void createAndShowGuiDP(KNNPredictor predictor, ArrayList<DataPoint> datapoints) {
		JFrame dpFrame = new JFrame("DataPoints!");
		// Create JPanel
		JPanel panel = new JPanel();
		// Set JPanel layout to FlowLayout
		panel.setLayout(new FlowLayout());
		
		// Create JLabel's that display precision and accuracy
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
      
    /* The main method runs createAndShowGui*/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	paramSelect();
            }
        });
    }
}
