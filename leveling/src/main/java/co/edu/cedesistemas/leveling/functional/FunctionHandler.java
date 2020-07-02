package co.edu.cedesistemas.leveling.functional;

import co.edu.cedesistemas.leveling.model.geometry.Scalable;
import co.edu.cedesistemas.leveling.model.geometry.Shape;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionHandler {
    public static <T, R> R applyFunction(Function<T, R> f, T t) {
        return null;
    }

    public static <T extends Scalable<T, U>, U extends Number> T getMultiplied(ShapeMultiplier<T, U> f, T shape, U value) {
        return f.multiply(shape, value);
    }

    public static <T> void consume(Consumer<List<T>> consumer, List<T> list) {
        consumer.accept(list);
    }

    public static Function<List<? extends Shape>, Map<? extends Shape, Double>> getAreaMapper() {

        return (list) -> list.stream()
                .collect(Collectors.toMap(s -> s, Shape::area));
    }
}
