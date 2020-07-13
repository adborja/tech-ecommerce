package co.edu.cedesistemas.leveling.functional;

import co.edu.cedesistemas.leveling.model.geometry.Scalable;

@FunctionalInterface
public interface ShapeMultiplier<T extends Scalable<T, U>, U extends Number> {
    T multiply(T scalable, U value);
}
