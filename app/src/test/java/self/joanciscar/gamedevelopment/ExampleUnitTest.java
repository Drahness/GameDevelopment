package self.joanciscar.gamedevelopment;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void pointsAreCorrect() {
        Point beg = new Point(100,150);

        Point end = new Point(750,350);
        double distance = 500;
        Point result = Point.newPosition(distance,beg,end);
        System.out.println(distance);
        assertEquals(end,result);
    }

    @Test
    public void betweenIsCorrect() {
        double one = Double.MIN_NORMAL;
        double two = Double.MAX_VALUE-1;
        double a = 0.00001;
        double b = 1;
        System.out.println(1.0000000000000002==1.0);
        System.out.println(1.0000000000000002);
        System.out.println(1.0);
        assertEquals(Utils.isBetweenInt(1.0,1.0000000000000001,1),0);
    }
}