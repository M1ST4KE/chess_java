package pl.chessonline.client.model;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN  = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);
    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);

    private static boolean[] initRow(int rowNum) {
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNum] = true;
            rowNum++;
        } while (rowNum % NUM_TILES_PER_ROW != 0);
        return row;
    }

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("You cann't instance me!");
    }

    private static boolean[] initColumn(int columnN) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnN] = true;
            columnN += NUM_TILES_PER_ROW;
        } while (columnN < NUM_TILES);
        return column;
    }

    public static boolean isValidTileCoordinate(int coordinate){
        return coordinate >= 0 && coordinate <64;
    }
}
