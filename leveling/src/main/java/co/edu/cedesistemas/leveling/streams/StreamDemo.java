package co.edu.cedesistemas.leveling.streams;


import co.edu.cedesistemas.leveling.model.geometry.Scalable;
import co.edu.cedesistemas.leveling.model.geometry.Shape;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
    /**
     * Retorna una lista filtrada con las figuras que tienen un área menor o igual a límite de área especificado.
     *
     * @param areaLimit El valor máximo del área.
     * @param shapes Lista de figuras
     * @return Lista filtrada con las figuras que tienen un área menor o igual a la especificada.
     * */
    public static List<? extends Shape> filterShapes(double areaLimit, List<? extends Shape> shapes) {
        List<Shape> smallShapes = shapes.stream().filter(shape -> shape.area() <= areaLimit).collect(Collectors.toList());
        return smallShapes;
    }

    /**
     * Retorna una lista de objetos escalables escalados por un valor especificado.
     *
     * @param scalables Lista de objetos escalables.
     * @param value Valor numérico a escalar.
     * @return Lista con objetos escalados
     * */
    public static <T extends Scalable<T, U>, U extends Number> List<T> scale(List<T> scalables, U value) {
        List<T> objScalables = scalables.stream().map(scalable -> scalable.scale(value)).collect(Collectors.toList());
        return objScalables;
    }
}
