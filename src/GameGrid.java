import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GameGrid extends JPanel {
    private static final long serialVersionUID = 1L;

    // overall grid size
    private static final Dimension GRIDSIZE = new Dimension(800, 800);
    // spaces in between tiles
    private static final int BOARDBACKGROUNDSEPSIZE = 25;
    // board background
    private static final Color BOARDBACKGROUND = new Color(187, 173, 160);

    private static Font font;

    // tiles contains the tile in each position in grid
    private Tile[][] tiles = new Tile[4][4];
    // emptyTiles contains locations of all empty tiles
    private ArrayList<Location> emptyTiles = new ArrayList<Location>();


    public GameGrid() {
        super();
        fillTilesWithEmpty();
        setUpFont();
    }


    private void setUpFont() {
        font = ProjectFont.font();
    }


    private void setFontSize(int neededNum) {
        if (neededNum < 128) {
            if (neededNum < 16) {
                font = font.deriveFont(ProjectFont.PRIMARYFONTSIZE);
            }
            else {
                font = font.deriveFont(ProjectFont.SECONDARYFONTSIZE);
            }
        }
        else {
            font = font.deriveFont(ProjectFont.THIRDFONTSIZE);
        }
    }


    private void fillTilesWithEmpty() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(-1);
                Location loc = new Location(i, j);
                emptyTiles.add(loc);
            }
        }
    }


    public void paintComponent(Graphics g) {
        // w and h are coordinates of exact middle of grid
        int w = (getWidth() / 2) - (int)(GRIDSIZE.getWidth() / 2);
        int h = (getHeight() / 2) - (int)(GRIDSIZE.getHeight() / 2);
        drawBoard(g, w, h);

    }


    private void drawBoard(Graphics g, int w, int h) {
        g.setColor(BOARDBACKGROUND);
        g.fillRect(w, h, (int)GRIDSIZE.getWidth(), (int)GRIDSIZE.getHeight());
        drawTiles(g, w, h);
    }


    // returns top left X coor of grid
    private int getStartX(int col, int w) {

        return w + ((col * Tile.WIDTH) + ((col + 1) * BOARDBACKGROUNDSEPSIZE));
    }


    // returns top left Y coor of grid
    private int getStartY(int row, int h) {

        return h + ((row * Tile.HEIGHT) + ((row + 1) * BOARDBACKGROUNDSEPSIZE));
    }


    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g
     *            The Graphics instance.
     * @param text
     *            The String to draw.
     * @param rect
     *            The Rectangle to center the text in.
     */
    private void drawCenteredString(
        Graphics g,
        String text,
        Rectangle rect,
        Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as
        // in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics
            .getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }


    private void drawTiles(Graphics g, int w, int h) {
        // loop through every tile
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != null) {
                    // get the x and y of this tile
                    int x = getStartX(j, w);
                    int y = getStartY(i, h);
                    // set color then draw tile
                    g.setColor(tiles[i][j].getColor());
                    g.fillRect(x, y, Tile.WIDTH, Tile.HEIGHT);

                    // Draw Number
                    int number = tiles[i][j].getNumber();
                    if (number != -1) {
                        // ensure font size is right
                        setFontSize(number);
                        // build Rectangle for tile
                        Rectangle tile = new Rectangle();
                        tile.setBounds(x, y, Tile.WIDTH, Tile.HEIGHT);

                        // set the font, then color
                        g.setFont(font);
                        if (number == 2 || number == 4) {
                            g.setColor(Tile.PRIMARYTEXTCOLOR);
                        }
                        else {
                            g.setColor(Tile.SECONDARYTEXTCOLOR);
                        }
                        drawCenteredString(g, number + "", tile, font);
                    }
                }
            }
        }
    }


    // remove a tile from empty list
    private void removeTileFromEmpty(int row, int col) {
        for (int i = 0; i < emptyTiles.size(); i++) {
            Location loc = emptyTiles.get(i);
            if (loc.getRow() == row && loc.getCol() == col) {
                emptyTiles.remove(i);
            }
        }
    }


    public void addTile(Tile tile, int row, int col) {
        tiles[row][col] = tile;
        removeTileFromEmpty(row, col);
    }


    public void removeTile(int row, int col) {
        Tile t = new Tile();
        tiles[row][col] = t;
        emptyTiles.add(new Location(row, col));
    }


    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }


    public int getEmptyPositions() {
        return emptyTiles.size();
    }


    public Location getEmptyLocation(int index) {
        return emptyTiles.get(index);
    }


    public void resetGrid() {
        emptyTiles.clear();
        fillTilesWithEmpty();
    }
}
