package pl.chessonline.client.model;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

//klasa abstrakcyjna reprezentująca pole, klasy nadpisujące dają na puste i zajęte pole
public abstract class Tile {
    //Board consists of 64 Tiles
    //represents empty tile
    protected final int tileCoordinate;

    private final static Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    //hash map przechowująca pola
    public static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<Integer, EmptyTile>();
        for(int i =0; i<64; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    //metoda tworząca pola
    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

    //klasa reprezetująca puste pole
    public static final class EmptyTile extends Tile{
        private EmptyTile(final int coordinate){
            super(coordinate);

        }
        @Override
        public boolean isTileOccupied(){
            return false;
        }

        @Override
        public Piece getPiece(){
            return null;
        }
    }

    //klasa reprezentująca zajęte pole
    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate, Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied(){
            return true;
        }

        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
        }
    }
}
