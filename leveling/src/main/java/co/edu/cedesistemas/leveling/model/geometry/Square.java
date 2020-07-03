package co.edu.cedesistemas.leveling.model.geometry;

public class Square extends Rectangle {
    public Square(double w) {
        this(Point.of(0.0, 0.0), w);
    }

    public Square(Point<Double, Double> bl, double w) {
        super(bl, w, w);
    }
}