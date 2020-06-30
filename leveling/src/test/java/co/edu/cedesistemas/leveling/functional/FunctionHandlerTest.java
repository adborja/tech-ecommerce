package co.edu.cedesistemas.leveling.functional;

import co.edu.cedesistemas.leveling.model.geometry.Circle;
import co.edu.cedesistemas.leveling.model.geometry.Point;
import co.edu.cedesistemas.leveling.model.geometry.Rectangle;
import co.edu.cedesistemas.leveling.model.geometry.Shape;
import co.edu.cedesistemas.leveling.model.geometry.Square;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class FunctionHandlerTest {
    @Test
    public void testGetArea() {
        double radius = 10;
        Circle circle = new Circle(radius);
        ShapeMultiplier<Circle, Double> shapeMultiplier = (c, v) -> c.scale(v);
        Circle multiplied = FunctionHandler.getMultiplied(shapeMultiplier, circle, 200D);
        Double area = multiplied.area();

        double circleArea = Math.PI * Math.pow(radius, 2);
        double newCircleArea = Math.PI * Math.pow(multiplied.getRadius(), 2);

        System.out.println(circleArea);
        assertThat(circle.area(), closeTo(circleArea, 0.001));
        assertThat(area, closeTo(newCircleArea, 0.001));
    }

    @Test
    public void testGetArea2() {
        Rectangle rectangle = new Rectangle(Point.of(0.0, 0.0), 10, 15);
        Function<Rectangle, Double> f = r -> r.area();
        Double area = FunctionHandler.applyFunction(f, rectangle);
        assertThat(area, equalTo(150D));
    }

    @Test
    public void testGetRectangleWidth() {
        Function<Rectangle, Double> f = s -> s.getWidth();
        Double width  = FunctionHandler.applyFunction(f, new Square(Point.of(0.0, 0.0), 15));
        assertThat(width, equalTo(15.0));
    }

    @Test
    public void testSort() {
        Circle c1 = new Circle(10);
        Circle c2 = new Circle(15);
        Circle c3 = new Circle(5);
        Circle c4 = new Circle(12);
        List<Circle> circles = Arrays.asList(c1, c2, c3, c4);

        Consumer<List<Circle>> sorter = l -> Collections.sort(l);
        FunctionHandler.consume(sorter, circles);

        assertThat(circles, contains(c3, c1, c4, c2));
    }

    @Test
    public void testShapeAreaMap() {
        Function<List<? extends Shape>, Map<? extends Shape, Double>> function = FunctionHandler.getAreaMapper();

        Square s1 = new Square(10);
        Square s2 = new Square(20);
        Square s3 = new Square(30);
        Square s4 = new Square(40);
        Square s5 = new Square(50);

        List<Square> squares = Arrays.asList(s1, s2, s3, s4, s5);

        Map<? extends Shape, Double> result = function.apply(squares);
        assertThat(result.size(), equalTo(5));
        assertThat(result.get(s1), equalTo(100.0));
        assertThat(result.get(s2), equalTo(400.0));
        assertThat(result.get(s3), equalTo(900.0));
    }
}
