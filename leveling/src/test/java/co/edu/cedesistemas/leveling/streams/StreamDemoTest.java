package co.edu.cedesistemas.leveling.streams;

import co.edu.cedesistemas.leveling.model.geometry.Circle;
import co.edu.cedesistemas.leveling.model.geometry.Point;
import co.edu.cedesistemas.leveling.model.geometry.Rectangle;
import co.edu.cedesistemas.leveling.model.geometry.Shape;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;


public class StreamDemoTest {

    @Test
    public void testFilterShapes() {
        Circle c1 = new Circle(20);
        Circle c2 = new Circle(10);
        Circle c3 = new Circle(30);
        Circle c4 = new Circle(15);
        Circle c5 = new Circle(5);

        List<Circle> circles = Arrays.asList(c1, c2, c3, c4, c5);
        List<? extends Shape> newCircles = StreamDemo.filterShapes(1000, circles);
        assertThat(newCircles, contains(c2, c4, c5));

        Rectangle r1 = new Rectangle(Point.of(0.0, 0.0), 10, 20);
        Rectangle r2 = new Rectangle(Point.of(0.0, 0.0), 15, 5);
        Rectangle r3 = new Rectangle(Point.of(0.0, 0.0), 5, 8);
        Rectangle r4 = new Rectangle(Point.of(0.0, 0.0), 4, 15);
        Rectangle r5 = new Rectangle(Point.of(0.0, 0.0), 3, 7);

        List<Rectangle> rectangles = Arrays.asList(r1, r2, r3, r4, r5);
        List<? extends Shape> newRectangles = StreamDemo.filterShapes(60, rectangles);
        assertThat(newRectangles, contains(r3, r4, r5));
    }

    @Test
    public void testScale() {
        Circle c1 = new Circle(5);
        Circle c2 = new Circle(10);
        Circle c3 = new Circle(15);
        List<Circle> circles = Arrays.asList(c1, c2, c3);
        List<Circle> newCircles = StreamDemo.scale(circles, 200D);

        Integer sum = newCircles.stream()
                .mapToInt(c -> (int) c.getRadius())
                .sum();
        assertThat(sum, equalTo(60));

        ScalableImpl s1 = new ScalableImpl(2);
        ScalableImpl s2 = new ScalableImpl(4);
        ScalableImpl s3 = new ScalableImpl(6);

        List<ScalableImpl> scalables = Arrays.asList(s1, s2, s3);
        List<ScalableImpl> newScalables = StreamDemo.scale(scalables, 100D);

        Integer sSum = newScalables.stream()
                .mapToInt(s -> (int) s.getV())
                .sum();
        assertThat(sSum, equalTo(1200));
    }

    @Test
    public void testRectangleStreams() {
        /*
        Dada una lista de rectangulos, obtener una lista con rectangulos transformados de la siguiente forma:
        1. Los rectangulos deben quedar escalados al 50% -> Alto (W) al 50%, Ancho (H) al 50%,
        2. Los rectangulos deben quedar en el tercer cuadrante del plano cartesiano de manera simétrica (mirar figura)
        3. Los rectangulos deben quedar ordenados de mayor a menor area

        condiciones:
            1. Los rectangulos de entrada deben estar situados en su totalidad el primer cuadrante del plano cartesiano
            2. El area de rectangulos de entrada no deben superar el valor de 150
        */
        Rectangle r1 = new Rectangle(Point.of(10.0, 20.0), 15, 8);
        Rectangle r2 = new Rectangle(Point.of(-10.0, -20.0), 40, 20);
        Rectangle r3 = new Rectangle(Point.of(15.0, 10.0), 10, 15);
        Rectangle r4 = new Rectangle(Point.of(12.0, -25.0), 9, 10);
        Rectangle r5 = new Rectangle(Point.of(25.0, 5.0), 11, 12);
        Rectangle r6 = new Rectangle(Point.of(3.0, 4.0), 30, 10);
        Rectangle r7 = new Rectangle(Point.of(6.0, 10.0), 25, 5);

        List<Rectangle> rectangles = Arrays.asList(r1, r2, r3, r4, r5, r6, r7);
        // ********************** TODO: Add list manipulation here (using streams)

        //List<Rectangle> newRectangle = StreamDemo.scale(rectangles, 50D);


        List<Rectangle> result = rectangles.stream()
                .filter(i -> i.getBottomLeft().getX() >=0 && i.getBottomRight().getY()>=0)
                .filter(i -> i.area()<=150)
                .map(i -> Rectangle.mirror(i))
                .map(i -> i.scale(50D))
                .sorted((a,b) -> Double.compare(b.area(),a.area()))
                .collect(Collectors.toList());



        //List<Rectangle> result = Collections.emptyList();
        // **********************

        assertThat(result.size(), equalTo(4));
        assertThat(result.get(0).area(), equalTo(37.5));
        assertThat(result.get(2).getBottomLeft(), equalTo(Point.of(-18.5, -12.5)));
    }
}
