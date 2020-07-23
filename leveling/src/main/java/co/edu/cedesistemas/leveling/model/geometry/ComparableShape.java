package co.edu.cedesistemas.leveling.model.geometry;

public interface ComparableShape extends Shape {
    default boolean isBiggerThan(Shape shape) {
        return this.perimeter() > shape.perimeter();
    }
}
