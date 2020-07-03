package co.edu.cedesistemas.leveling.model.geometry;

public interface Scalable<T, U extends Number> {
    T scale(U percentage);
}