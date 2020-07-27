package co.edu.cedesistemas.leveling.model.geometry;

public class Rectangle implements Shape, Scalable<Rectangle, Double> {
    private Point<Double, Double> bl;
    private Point<Double, Double> tr;
    private Point<Double, Double> br;
    private Point<Double, Double> tl;
    private final double height;
    private final double width;

    public Rectangle(Point<Double, Double> bl, double w, double h) {
        this.bl = bl;
        this.tl = Point.of(bl.getX(), bl.getY() + h);
        this.br = Point.of(bl.getX() + w, bl.getY());
        this.tr = Point.of(bl.getX() + w, bl.getY() + h);
        this.height = h;
        this.width = w;
    }

    public Rectangle(double w, double h) {
        this(Point.of(0.0, 0.0), w, h);
    }

    public Point<Double, Double> getTopRight() {
        return tr;
    }

    public void setTopRight(Point<Double, Double> tr) {
        this.tr = tr;
    }

    public Point<Double, Double> getBottomLeft() {
        return bl;
    }

    public void setBottomLeft(Point<Double, Double> bl) {
        this.bl = bl;
    }

    public Point<Double, Double> getBottomRight() {
        return br;
    }

    public void setBottomRight(Point<Double, Double> br) {
        this.br = br;
    }

    public Point<Double, Double> getTopLeft() {
        return tl;
    }

    public void setTopLeft(Point<Double, Double> tl) {
        this.tl = tl;
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

    @Override
    public Rectangle scale(Double percentage) {

        Rectangle rectangle = new Rectangle(this.getBottomLeft(),this.getWidth()*(percentage/100),this.getHeight()*(percentage/100));
        return rectangle;
    }

    public static Rectangle mirror(Rectangle r) {
        Point<Double, Double> nbl = Point.of(r.getTopRight().getX() * -1, r.getTopRight().getY() * -1);
        Point<Double, Double> ntr = Point.of(r.getBottomLeft().getX() * -1, r.getBottomLeft().getY() * -1);
        Point<Double, Double> ntl = Point.of(r.getBottomRight().getX() * -1, r.getBottomRight().getY() * -1);
        Point<Double, Double> nbr = Point.of(r.getTopLeft().getX() * -1, r.getTopLeft().getY() * -1);

        Rectangle result = new Rectangle(r.getWidth(), r.getHeight());
        result.setTopLeft(ntl);
        result.setTopRight(ntr);
        result.setBottomLeft(nbl);
        result.setBottomRight(nbr);

        return result;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "bl=" + bl +
                ", tr=" + tr +
                ", br=" + br +
                ", tl=" + tl +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
