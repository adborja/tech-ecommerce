package co.edu.cedesistemas.leveling.functional;

import co.edu.cedesistemas.leveling.model.geometry.Scalable;

public class ShapeMultiplierImpl<T extends Scalable<T, U>, U extends Number> implements ShapeMultiplier<T, U> {
    @Override
    public T multiply(T scalable, U value) {
        return scalable.scale(value);
    }
}
