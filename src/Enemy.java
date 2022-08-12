public class Enemy {
    private double xValue;
    private double yValue;

    public Enemy(double x, double y) {
        this.xValue = x;
        this.yValue = y;
    }

    public double getyValue() {
        return yValue;
    }

    public double getxValue() {
        return xValue;
    }

    public void setyValue(double yValue) {
        this.yValue = yValue;
    }

    public void setxValue(double xValue) {
        this.xValue = xValue;
    }
}
