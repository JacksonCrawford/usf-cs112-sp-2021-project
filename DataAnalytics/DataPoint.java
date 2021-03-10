public class DataPoint {
    public double f1;
    public double f2;
    public String label;
    public boolean isTest;

    // Default Constructor
    public DataPoint() {
        this.f1 = 0;
        this.f2 = 0;
        this.label = "";
        this.isTest = false;
    }


    // Accessors
    public double getf1() {
        return f1;
    }

    public double getf2() {
        return f2;
    }

    public String getLabel() {
        return label;
    }

    public boolean getIsTest() {
        return isTest;
    }


    // Mutators
    public void setf1(double fParam) {
        f1 = fParam;
    }

    public void setf2(double fParam) {
        f2 = fParam;
    }

    public void setLabel(String lParam) {
        label = lParam;
    }

    public void setIsTest(tParam) {
        isTest = tParam;
    }

}
