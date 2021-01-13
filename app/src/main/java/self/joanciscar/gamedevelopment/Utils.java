package self.joanciscar.gamedevelopment;

public class Utils {

    public static boolean isBetween(float value,float min, float max) {
        return min <= value && value <= max;
    }

    public static boolean isBetween(int value,int min, int max) {
        return isBetween((float)value,(float)min,(float)max);
    }
}
