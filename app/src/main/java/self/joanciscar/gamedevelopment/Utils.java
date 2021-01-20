package self.joanciscar.gamedevelopment;

public class Utils {

    public static boolean isBetween(double value,double min, double max) {
        return min <= value && value <= max;
    }
    public static boolean isBetween(float value,float min, float max) {
        return min <= value && value <= max;
    }
    public static boolean isBetween(int value,int min, int max) {
        return min <= value && value <= max;
    }

    public static float clamp(int value,int min, int max) {
        return clamp((float)value,min,max);
    }

    public static float clamp(float value,float min, float max) {
        if(Float.compare(value,max) > 0) {
            return max;
        }
        else if(Float.compare(value,min) < 0) {
            return min;
        }
        else {
            return value;
        }
    }

    /**
     *
     * @param value value to compare
     * @param min value permitted
     * @param max value permitted
     * @return 0 if it is in between, 1 or more if exceeds the maximum, -1 or less if exceeds minimum
     */
    public static int isBetweenInt(float value,float min, float max) {
        if(isBetween(value,min,max)) {
            return 0;
        }
        return max - value > 0 ? -1 : 1;
    }
}
