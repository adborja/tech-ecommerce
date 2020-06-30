package co.edu.cedesistemas.leveling.model.geometry;

// Lesson 1 -- Classes
public class Rectangle implements Shape {
    private final Point<Double, Double> bl;
    private final Point<Double, Double> tr;
    private final Point<Double, Double> br;
    private final Point<Double, Double> tl;
    private final double height;
    private final double width;

    // Lesson 1 -- Constructores
    public Rectangle(Point<Double, Double> bl, double w, double h) {
        this.bl = bl;
        this.tl = Point.of(bl.getX(), bl.getY() + h);
        this.br = Point.of(bl.getX() + w, bl.getY());
        this.tr = Point.of(bl.getX() + w, bl.getY() + h);
        this.height = h;
        this.width = w;
    }

    public Point<Double, Double> getTopRight() {
        return tr;
    }

    public Point<Double, Double> getBottomLeft() {
        return bl;
    }

    public Point<Double, Double> getBottomRight() {
        return br;
    }

    public Point<Double, Double> getTopLeft() {
        return tl;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public double area() {
        return getHeight() * getWidth();
    }

    @Override
    public double perimeter() {
        return 2 * (getHeight() + getWidth());
    }
}
