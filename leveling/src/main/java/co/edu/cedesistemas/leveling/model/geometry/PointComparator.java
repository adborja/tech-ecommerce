package co.edu.cedesistemas.leveling.model.geometry;

import java.util.Comparator;

public class PointComparator implements Comparator<Point<? extends Number, ? extends Number>> {
    @Override
    public int compare(Point p1, Point p2) {
        if (p1.getX().doubleValue() == p2.getX().doubleValue() && p1.getY().doubleValue() == p2.getY().doubleValue()) {
            return 0;
        }
        return p1.getX().doubleValue() > p2.getX().doubleValue()
                && p1.getY().doubleValue() > p2.getY().doubleValue() ? 1 : -1;
    }
}
