package pl.chessonline.gui;


import com.google.common.collect.Lists;
import org.json.JSONException;
import pl.chessonline.client.connection.Connection;
import pl.chessonline.client.connection.EndGame;
import pl.chessonline.client.connection.Handshake;
import pl.chessonline.client.connection.Movement;
import pl.chessonline.client.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    EndGame endGame;
    Connection connection;
    private ServerListener serverListener;

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board chessBoard;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private boolean highlightLegalMoves;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String defaultPieceImagesPath = "art/";

    private Color lightTileColor = Color.decode("#FFFFFF");
    private Color darkTileColor = Color.decode("#696969");

    public Table()  {
        this.gameFrame = new JFrame("Chess game");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.boardPanel = new BoardPanel();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = true;
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);

        Handshake handshake = null;
        try {
            handshake = new Handshake();
            connection = new Connection(handshake.getGamePort());
            endGame = new EndGame();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.serverListener = new ServerListener(connection.getInputStream());
        this.serverListener.subscribe(()->{
            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
                    destinationTile.getTileCoordinate());
            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
            if(transition.getMoveStatus().isDone()) {
                chessBoard = transition.getTransitionBoard();
            }});
    }

    private JMenuBar createTableMenuBar()   {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createGameMenu());
        tableMenuBar.add(createPreferencesMenu());

        return tableMenuBar;
    }

    private JMenu createGameMenu()  {
        final JMenu gameMenu = new JMenu("Game");

        final JMenuItem openPGN = new JMenuItem("Load PGN file");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open up that pgn file!");
            }
        });
        gameMenu.add(openPGN);

        final JMenuItem exitMenuItem = new  JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gameMenu.add(exitMenuItem);

        return gameMenu;
    }

    private JMenu createPreferencesMenu()   {

        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);

        preferencesMenu.addSeparator();

        final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves", true);

        legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
            }
        });

        preferencesMenu.add(legalMoveHighlighterCheckbox);
        return preferencesMenu;
    }

    public enum BoardDirection  {

        NORMAL  {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles)  {
                return boardTiles;
            }

            @Override
            BoardDirection opposite()  {
                return FLIPPED;
            }
        },

        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles)  {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite()   {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
    }

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        BoardPanel()    {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();

            for(int i = 0; i < BoardUtils.NUM_TILES; i++)   {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board)    {
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel  {

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId)    {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            //todo napisać addServerListener


            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {



                    if(isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(e))    {
                        if(sourceTile == null)  {
                            sourceTile = chessBoard.getTile((tileId));
                            humanMovedPiece = sourceTile.getPiece();
                            if(humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        }   else    {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getTransitionBoard();
                                //TODO add the move that was made to the move log
                            }

                            try {
                                connection.sendMessage(new Movement(sourceTile.getTileCoordinate(),
                                        destinationTile.getTileCoordinate()));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;

                            Movement movement;
                            try {
                                movement = connection.recieveMessage();

                                final Move opponentMove = Move.MoveFactory.createMove(chessBoard, movement.getFrom(), movement.getTo());
                                final MoveTransition opponentTransition = chessBoard.currentPlayer().makeMove(opponentMove);
                                if (opponentTransition.getMoveStatus().isDone()) {
                                    chessBoard = opponentTransition.getTransitionBoard();
                                }
                            } catch (IOException | JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });


                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        public void drawTile(final Board board) {
            assignTileColor();;
            assignTilePieceIcon(board);
            highlightLegals(chessBoard);
            validate();
            repaint();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()) {
                try {
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1) +
                            board.getTile(this.tileId).getPiece().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                }   catch (IOException e)   {
                    e.printStackTrace();
                }
            }
        }

        private void highlightLegals(final Board board) {
            if(highlightLegalMoves)    {
                for(final Move move : pieceLegalMoves(board))   {
                    if(move.getDestinationCoordinate() == this.tileId)  {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("art/green_dot.png")))));
                        }   catch(Exception e)  {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance())    {
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTileColor()  {
            if(BoardUtils.FIRST_ROW[this.tileId]    ||  BoardUtils.THIRD_ROW[this.tileId]   ||
                    BoardUtils.FIFTH_ROW[this.tileId]   ||  BoardUtils.SEVENTH_ROW[this.tileId])    {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtils.SECOND_ROW[this.tileId]    ||  BoardUtils.FOURTH_ROW[this.tileId]  ||
                    BoardUtils.SIXTH_ROW[this.tileId]   ||  BoardUtils.EIGHTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}
