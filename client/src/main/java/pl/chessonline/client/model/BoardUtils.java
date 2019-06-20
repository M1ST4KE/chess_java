package pl.chessonline.client.model;

public class BoardUtils {
    public static final boolean[] FIRST_COLUMN  = null;
    public static final boolean[] SECOND_COLUMN = null;
    public static final boolean[] SEVENTH_COLUMN = null;
    public static final boolean[] EIGHT_COLUMN = null;


    private BoardUtils() {
        throw new RuntimeException("You cann't instance me!");
    }

    public static boolean isValidTileCoordinate(int coordinate){
        return coordinate >= 0 && coordinate <64;
    }
}