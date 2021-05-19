package part4;

public class DataPoint {
    public double f1;
    public double f2;
    public int f3;
    public int f4;
    public double label;
    public boolean isTest;
    
    // Constructor
    public DataPoint(double f1Param, double f2Param, int f3Param, int f4Param, double lParam, boolean tParam) {
    	// Age
    	this.f1 = f1Param;
    	// Fare
    	this.f2 = f2Param;
    	// Passenger Class
    	this.f3 = f3Param;
    	// Sex
    	this.f4 = f4Param;
    	this.label = lParam;
    	this.isTest = tParam;
    }

    // Default Constructor
    public DataPoint() {
        this.f1 = 0.0;
        this.f2 = 0.0;
        this.f3 = 0;
        this.f4 = 0;
        this.label = 0.0;
        this.isTest = false;
    }


    /*
     * Accessors
     */
    
    // Returns f1 value (dbl)
    public double getf1() {
        return f1;
    }

    // Returns f2 value (dbl)
    public double getf2() {
        return f2;
    }
    
    // Returns f3 value (int)
    public int getf3() {
    	return f3;
    }
    
    // Returns f4 value (str)
    public int getf4() {
    	return f4;
    }

    // Returns label value (dbl)
    public double getLabel() {
        return label;
    }

    // Returns isTest value (bool)
    public boolean getIsTest() {
        return isTest;
    }


    /*
     * Mutators
     */
    
    // Sets f1 value (dbl)
    public void setf1(double fParam) {
        f1 = fParam;
    }

    // Sets f2 value (dbl)
    public void setf2(double fParam) {
        f2 = fParam;
    }
    
    // Sets f3 value (int)
    public void setf1(int fParam) {
        f3 = fParam;
    }

    // Sets f4 value (str)
    public void setf4(int fParam) {
        f4 = fParam;
    }

    // Sets label (str)
    public void setLabel(double lParam) {
    	label = lParam;
    }

    // Sets isTest value (bool)
    public void setIsTest(boolean tParam) {
        isTest = tParam;
    }

}
