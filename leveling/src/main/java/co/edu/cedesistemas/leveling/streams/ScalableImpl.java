package co.edu.cedesistemas.leveling.streams;

import co.edu.cedesistemas.leveling.model.geometry.Scalable;

public class ScalableImpl implements Scalable<ScalableImpl, Double> {
    private final double v;

    public ScalableImpl(final double v) {
        this.v = v;
    }

    public double getV() {
        return v;
    }

    @Override
    public ScalableImpl scale(Double percentage) {
        return new ScalableImpl(this.v * percentage);
    }
}
