package co.edu.cedesistemas.leveling.streams;

import co.edu.cedesistemas.leveling.functional.ShapeMultiplier;
import co.edu.cedesistemas.leveling.functional.ShapeMultiplierImpl;
import co.edu.cedesistemas.leveling.model.geometry.Rectangle;
import co.edu.cedesistemas.leveling.model.geometry.Scalable;
import co.edu.cedesistemas.leveling.model.geometry.Shape;

import java.util.ArrayList;
import java.util.Arrays;
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
        //List<Rectangle> rectangles = new ArrayList<>();
        return shapes.stream()
                .filter(rectagle -> ((Shape) rectagle).area()<=areaLimit)
                .collect(Collectors.toList());
       // return newList;
       // shapes.stream().filter(t -> ((Rectangle) t).area()<areaLimit).forEach( r ->rectangles.add(r));

       // return rectangles;
    }

    /**
     * Retorna una lista de objetos escalables escalados por un valor especificado.
     *
     * @param scalables Lista de objetos escalables.
     * @param value Valor numérico a escalar.
     * @return Lista con objetos escalados
     * */
    public static <T extends Scalable<T, U>, U extends Number> List<T> scale(List<T> scalables, U value) {

       // List<ScalableImpl> scalables = Arrays.asList(s1, s2, s3);
        //Double totalScalable= scalables.stream().reduce(0, (a, b) -> a+b);
        return scalables.stream().map(shape -> shape.scale(value)).collect(Collectors.toList());




    }
}
