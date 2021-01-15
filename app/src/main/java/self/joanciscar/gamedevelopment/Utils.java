package self.joanciscar.gamedevelopment;

public class Utils {

    public static boolean isBetween(double value,double min, double max) {
        return min <= value && value <= max;
    }
    public static boolean isBetween(float value,float min, float max) {
        return isBetween((double) value,min,max);
    }
    public static boolean isBetween(int value,int min, int max) {
        return isBetween((float)value,min,max);
    }

    public static double clamp(int value,int min, int max) {
        return clamp((float)value,min,max);
    }

    public static double clamp(float value,float min, float max) {
        return clamp((double) value,min,max);
    }
    public static double clamp(double value,double min, double max) {
        if(Double.compare(value,max) > 0) {
            return max;
        }
        else if(Double.compare(value,min) < 0) {
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
    public static int isBetweenInt(double value,double min, double max) {
        if(isBetween(value,min,max)) {
            return 0;
        }
        return max - value > 0 ? -1 : 1;
    }
}
