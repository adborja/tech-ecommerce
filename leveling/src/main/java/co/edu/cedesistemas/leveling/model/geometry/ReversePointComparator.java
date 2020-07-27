package co.edu.cedesistemas.leveling.model.geometry;

import java.util.Comparator;

public class ReversePointComparator implements Comparator<Point<? extends Number, ? extends Number>> {
    @Override
    public int compare(Point p1, Point p2) {
        if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
            return 0;
        }
        return p1.getX().doubleValue() < p2.getX().doubleValue()
                && p1.getY().doubleValue() < p2.getY().doubleValue() ? 1 : -1;
    }
}
