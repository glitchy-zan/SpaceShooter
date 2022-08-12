public class Fire {
    private double x;
    private double y;
    private int speed;

    Fire(double x, double y, int s) {
        this.x = x;
        this.y = y;
        this.speed = s;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
