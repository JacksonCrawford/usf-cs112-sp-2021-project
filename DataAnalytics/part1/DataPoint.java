public class DataPoint {
    public double f1;
    public double f2;
    public String label;
    public boolean isTest;
    
    // Constructor
    public DataPoint(double f1Param, double f2Param, String lParam, boolean tParam) {
    	this.f1 = f1Param;
    	this.f2 = f2Param;
    	this.isTest = tParam;
    	// Only set label if point is not a test point
    	if(!this.isTest) {
    		this.label = lParam;
    	}
    }

    // Default Constructor
    public DataPoint() {
        this.f1 = 0;
        this.f2 = 0;
        this.label = "";
        this.isTest = false;
    }


    /*
     * Accessors
     */
    
    // Returns f1 value (dbl)
    public double getf1() {
        return f1;
    }

    // Returns f1 value (dbl)
    public double getf2() {
        return f2;
    }

    // Returns label value (str)
    public String getLabel() {
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

    // Sets label (str)
    public void setLabel(String lParam) {
    	// If it is a test point, it does not gets set to null
    	if(!this.isTest) {
	        if(! (lParam.equals("green") || lParam.equals("blue"))) {
	        	return;
	        } else {
	        	label = lParam;
	        }
    	} else {
    		this.label = null;
    	}
    }

    // Sets isTest value (bool)
    public void setIsTest(boolean tParam) {
        isTest = tParam;
    }

}
