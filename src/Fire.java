public class Fire {
    private double xValue;
    private double yValue;
    private int speed;

    Fire(double x, double y, int s) {
        this.xValue = x;
        this.yValue = y;
        this.speed = s;
    }

    public double getxValue() {
        return xValue;
    }

    public double getyValue() {
        return yValue;
    }

    public int getSpeed() {
        return speed;
    }

    public void setxValue(double xValue) {
        this.xValue = xValue;
    }

    public void setyValue(double yValue) {
        this.yValue = yValue;
    }
}
