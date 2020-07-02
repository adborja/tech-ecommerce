package co.edu.cedesistemas.leveling.streams;


import co.edu.cedesistemas.leveling.functional.ShapeMultiplier;
import co.edu.cedesistemas.leveling.model.geometry.Circle;
import co.edu.cedesistemas.leveling.model.geometry.Scalable;
import co.edu.cedesistemas.leveling.model.geometry.Shape;

import java.util.List;
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

        List<Shape> shapes1 = shapes.stream()
                .filter(element -> element.area() <= areaLimit)
                .collect(Collectors.toList());

        return shapes1;
    }

    /**
     * Retorna una lista de objetos escalables escalados por un valor especificado.
     *
     * @param scalables Lista de objetos escalables.
     * @param value Valor numérico a escalar.
     * @return Lista con objetos escalados
     * */
    public static <T extends Scalable<T, U>, U extends Number> List<T> scale(List<T> scalables, U value) {

        List<T> scalables2 = scalables.stream()
                .map((c -> c.scale(value)))
                .collect(Collectors.toList());

        return scalables2;
    }
}
