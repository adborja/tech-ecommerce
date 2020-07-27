package co.edu.cedesistemas.leveling.model.geometry;

import java.util.Objects;
import java.util.Random;

public class Point<X extends Number, Y extends Number> {
    private final X x;
    private final Y y;

    public Point(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public static <T extends Number> double distance(Point<T, T> p1, Point<T, T> p2) {
        return Math.sqrt(Math.pow(p1.getX().doubleValue() - p2.getX().doubleValue(), 2)
                + Math.pow(p1.getY().doubleValue() - p2.getY().doubleValue(), 2));
    }

    public static <X extends Number, Y extends Number> Point<X, Y> of(X x, Y y) {
        return new Point<>(x, y);
    }

    public static Point<Double, Double> random(int bound) {
        Double x1 = new Random(bound).nextDouble();
        Double y1 = new Random(bound).nextDouble();
        return of(x1, y1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point<?, ?> that = (Point<?, ?>) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
