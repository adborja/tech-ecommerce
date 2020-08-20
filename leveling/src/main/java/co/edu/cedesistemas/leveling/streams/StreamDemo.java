package co.edu.cedesistemas.leveling.streams;

import co.edu.cedesistemas.leveling.model.geometry.Scalable;
import co.edu.cedesistemas.leveling.model.geometry.Shape;

import java.util.*;
import java.util.stream.Collectors;

public class StreamDemo {
    /**
     * Retorna una lista filtrada con las figuras que tienen un área menor o igual a límite de área especificado.
     *
     * @param areaLimit El valor máximo del área.
     * @param shapes Lista de figuras
     * @return Lista filtrada con las figuras que tienen un área menor o igual a la especificada.
     * */
    public static List<? extends Shape> filterShapes(double areaLimit, List<? extends Shape> shapes) {
        return shapes.stream().filter(item -> item.area()<=areaLimit).collect(Collectors.toList());
    }

    /**
     * Retorna una lista de objetos escalables escalados por un valor especificado.
     *
     * @param scalables Lista de objetos escalables.
     * @param value Valor numérico a escalar.
     * @return Lista con objetos escalados
     * */
    public static <T extends Scalable<T, U>, U extends Number> List<T> scale(List<T> scalables, U value) {
        return scalables.stream().map(item -> item.scale(value)).collect(Collectors.toList());
    }
}
