package co.edu.cedesistemas.leveling.functional;

import co.edu.cedesistemas.leveling.generics.Sorter;
import co.edu.cedesistemas.leveling.model.geometry.Circle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        // Ordenar numeros ...
        List<Integer> numbers = Arrays.asList(5, 8, 2, 10, 4, 6, 1);
        // Solucion 1: Usando clase anonima
       SortFunction<Integer> sorter1 = new SortFunction<>() {
            @Override
            public void sort(List<Integer> list) {
                Collections.sort(list);
            }
        };
        sorter1.sort(numbers);
        System.out.println(numbers);

         */

        // Solucion 2: Usando expresión lambda:
        SortFunction<Integer> sorter2 = l -> Sorter.bubbleSort(numbers);
        sorter2.sort(numbers);
        System.out.println(numbers);

        // EJERCICIO:
        Circle circle = new Circle(30);
        // ************ CAMBIAR ESTAS LINEAS POR EXPRESION LAMBDA **********************
        Consumer<Circle> areaConsumer = circleLambda -> {
            ShapeMultiplier<Circle, Double> shapeMultiplier= (x,y) -> (x.scale(y));
            System.out.println("new area: " + shapeMultiplier.multiply(circle, 200D).area());
        };
        // *****************************************************************************

        // INSERTE EXPRESION LAMBDA ACA PARA OBTENER EL MISMO RESULTADO
        areaConsumer.accept(circle);
    }
}
