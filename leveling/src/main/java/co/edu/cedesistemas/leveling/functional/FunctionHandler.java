package co.edu.cedesistemas.leveling.functional;

import co.edu.cedesistemas.leveling.model.geometry.Scalable;
import co.edu.cedesistemas.leveling.model.geometry.Shape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionHandler {
    public static <T, R> R applyFunction(Function<T, R> f, T t) {
        return f.apply(t);
    }

    public static <T extends Scalable<T, U>, U extends Number> T getMultiplied(ShapeMultiplier<T, U> f, T shape, U value) {
        return f.multiply(shape, value);
    }

    public static <T> void consume(Consumer<List<T>> consumer, List<T> list) {
        consumer.accept(list);
    }

    public static Function<List<? extends Shape>, Map<? extends Shape, Double>> getAreaMapper() {

        return new Function<List<? extends Shape>, Map<? extends Shape, Double>>() {
            public Map<? extends Shape, Double> apply(List<? extends Shape> list) {
                return list.stream().collect(Collectors.toMap(shape -> shape, Shape::area));
            }
        };
    }
}