import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI {
    private static JFrame mainWindow;
    private static GameGrid grid;
    private static JLabel menuLabel;

    // key listener
    private static KeyboardListener keyListener = new KeyboardListener();
    private static Random rnd = new Random();

    // menu size and background color for game
    private static final Dimension MENUSIZE = new Dimension(100, 100);
    private static final int MENUTEXTSIZE = 2; // 60
    private static final Color MENUTEXTCOLOR = new Color(119, 110, 101);
    private static final Color BACKGROUNDCOLOR = new Color(250, 248, 239);
    private static int score = 0;

    // Direction vars
    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;


    public GUI() {
        // setup grid
        // init mainwindow
        // resetStats for end game
        // generate two random tiles
        grid = new GameGrid();
        initMainWindow();
        generateRandomTile();
        generateRandomTile();
    }


    public void display() {
        mainWindow.setVisible(true);
    }


    private void initMainWindow() {
        mainWindow = new JFrame();
        mainWindow.setTitle("2048!");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000, 1000);
        mainWindow.setResizable(false);

        JPanel content = initContentPanel();
        mainWindow.setContentPane(content);
        mainWindow.setFocusable(true);
        mainWindow.addKeyListener(keyListener);
    }


    private JPanel initContentPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUNDCOLOR);
        content.add(initMenu(), BorderLayout.PAGE_START);
        content.add(grid, BorderLayout.CENTER);
        return content;
    }


    private static String getMenuString() {
        return "2048!    Score: " + score;
    }


    private static JPanel initMenu() {
        JPanel ret = new JPanel(new BorderLayout());
        ret.setBackground(BACKGROUNDCOLOR);
        ret.setPreferredSize(MENUSIZE);
        Font font = ProjectFont.font().deriveFont(MENUTEXTSIZE);

        // 2048 text
        String s = getMenuString();
        menuLabel = new JLabel(s, SwingConstants.CENTER);
        menuLabel.setForeground(MENUTEXTCOLOR);
        menuLabel.setFont(font);

        // add the items
        ret.add(menuLabel, BorderLayout.CENTER);
        return ret;
    }


    private static void addToScore(int n) {
        score += n;
        menuLabel.setText(getMenuString());
    }


    private static void restartGame() {
        score = 0;
        menuLabel.setText(getMenuString());
        grid.resetGrid();
        generateRandomTile();
        generateRandomTile();
        mainWindow.repaint();
    }


    private static void loseGame() {
        System.out.println("YOU LOSE!");

        int result = JOptionPane.showConfirmDialog(mainWindow,
            "Would you like to play again?", "You Lose!",
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        else if (result == JOptionPane.YES_OPTION) {
            restartGame();
        }
    }


    private static void checkLoseGame() {
        boolean oneTileCanMove = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // check if every tile is stuck
                Tile t = grid.getTile(i, j);
                if (canTileMove(LEFT, t, i, j) || canTileMove(UP, t, i, j)
                    || canTileMove(RIGHT, t, i, j) || canTileMove(DOWN, t, i,
                        j)) {
                    oneTileCanMove = true;
                }
            }
        }
        if (oneTileCanMove == false) {
            loseGame();
        }
    }


    private static void generateRandomTile() {
        // 90% chance to gen a 2, 10% chance to gen a 4
        int value = Math.random() < 0.9 ? 2 : 4;
        Tile one = new Tile(value);
        // random location in empty spots
        int numEmpty = grid.getEmptyPositions();
        if (numEmpty != 0) {
            // grab the index and get the loc, then add the tile
            int randomIndex = rnd.nextInt(numEmpty);
            Location loc = grid.getEmptyLocation(randomIndex);
            grid.addTile(one, loc.getRow(), loc.getCol());
        }
    }


    // --
    // next methods deal with moving tiles

    private static boolean canTileMove(int key, Tile t, int row, int col) {
        int number = t.getNumber();
        Tile other; // represents tile next to current
        if (key == LEFT) {
            if (col == 0) {
                return false;
            }
            other = grid.getTile(row, col - 1);
            if (other.getNumber() == Tile.EMPTY || other
                .getNumber() == number) {
                return true;
            }
        }
        else if (key == UP) {
            if (row == 0) {
                return false;
            }
            other = grid.getTile(row - 1, col);
            if (other.getNumber() == Tile.EMPTY || other
                .getNumber() == number) {
                return true;
            }
        }
        else if (key == RIGHT) {
            if (col == 3) {
                return false;
            }
            other = grid.getTile(row, col + 1);
            if (other.getNumber() == Tile.EMPTY || other
                .getNumber() == number) {
                return true;
            }
        }
        else if (key == DOWN) {
            if (row == 3) {
                return false;
            }
            other = grid.getTile(row + 1, col);
            if (other.getNumber() == Tile.EMPTY || other
                .getNumber() == number) {
                return true;
            }
        }
        else {
            // nothing
        }
        return false;
    }


    private static Tile doubleTile(int number) {
        Tile t = new Tile(number * 2);
        addToScore(number * 2);
        return t;
    }


    private static boolean moveTileUpwards(Tile t, int row, int col) {
        int thisRow = row;
        int thisCol = col;
        boolean flag = true;
        boolean changed = false;
        while (flag) {
            if (thisRow == 0) {
                flag = false;
            }
            else {
                Tile other = grid.getTile(thisRow - 1, thisCol);
                if (other.getNumber() == -1) {
                    // empty tile above
                    // move up
                    grid.removeTile(thisRow, thisCol);
                    grid.addTile(t, thisRow - 1, thisCol);
                    thisRow--;
                    changed = true;
                }
                else if (other.getNumber() == t.getNumber()) {
                    // same tile above
                    // combine
                    grid.removeTile(thisRow, thisCol);
                    grid.removeTile(thisRow - 1, thisCol);
                    Tile combinedTile = doubleTile(t.getNumber());
                    grid.addTile(combinedTile, thisRow - 1, thisCol);
                    thisRow--;
                    changed = true;
                }
                else {
                    // different tile above
                    // cannot move
                    flag = false;
                }
            }
        }
        return changed;
    }


    private static boolean moveTileLeftwards(Tile t, int row, int col) {
        int thisRow = row;
        int thisCol = col;
        boolean flag = true;
        boolean changed = false;
        while (flag) {
            if (thisCol == 0) {
                flag = false;
            }
            else {
                Tile other = grid.getTile(thisRow, thisCol - 1);
                if (other.getNumber() == -1) {
                    // empty tile above
                    // move up
                    grid.removeTile(thisRow, thisCol);
                    grid.addTile(t, thisRow, thisCol - 1);
                    thisCol--;
                    changed = true;
                }
                else if (other.getNumber() == t.getNumber()) {
                    // same tile above
                    // combine
                    grid.removeTile(thisRow, thisCol);
                    grid.removeTile(thisRow, thisCol - 1);
                    Tile combinedTile = doubleTile(t.getNumber());
                    grid.addTile(combinedTile, thisRow, thisCol - 1);
                    thisCol--;
                    changed = true;
                }
                else {
                    // different tile above
                    // cannot move
                    flag = false;
                }
            }
        }
        return changed;
    }


    private static boolean moveTileRightwards(Tile t, int row, int col) {
        int thisRow = row;
        int thisCol = col;
        boolean flag = true;
        boolean changed = false;
        while (flag) {
            if (thisCol == 3) {
                flag = false;
            }
            else {
                Tile other = grid.getTile(thisRow, thisCol + 1);
                if (other.getNumber() == -1) {
                    // empty tile above
                    // move up
                    grid.removeTile(thisRow, thisCol);
                    grid.addTile(t, thisRow, thisCol + 1);
                    thisCol++;
                    changed = true;
                }
                else if (other.getNumber() == t.getNumber()) {
                    // same tile above
                    // combine
                    grid.removeTile(thisRow, thisCol);
                    grid.removeTile(thisRow, thisCol + 1);
                    Tile combinedTile = doubleTile(t.getNumber());
                    grid.addTile(combinedTile, thisRow, thisCol + 1);
                    thisCol++;
                    changed = true;
                }
                else {
                    // different tile above
                    // cannot move
                    flag = false;
                }
            }
        }
        return changed;
    }


    private static boolean moveTileDownwards(Tile t, int row, int col) {
        int thisRow = row;
        int thisCol = col;
        boolean flag = true;
        boolean changed = false;
        while (flag) {
            if (thisRow == 3) {
                flag = false;
            }
            else {
                Tile other = grid.getTile(thisRow + 1, thisCol);
                if (other.getNumber() == -1) {
                    // empty tile above
                    // move up
                    grid.removeTile(thisRow, thisCol);
                    grid.addTile(t, thisRow + 1, thisCol);
                    thisRow++;
                    changed = true;
                }
                else if (other.getNumber() == t.getNumber()) {
                    // same tile above
                    // combine
                    grid.removeTile(thisRow, thisCol);
                    grid.removeTile(thisRow + 1, thisCol);
                    Tile combinedTile = doubleTile(t.getNumber());
                    grid.addTile(combinedTile, thisRow + 1, thisCol);
                    thisRow++;
                    changed = true;
                }
                else {
                    // different tile above
                    // cannot move
                    flag = false;
                }
            }
        }
        return changed;
    }


    private static boolean moveAllTilesUp() {
        // rows is i
        // cols is j
        // left to right, up to down
        //// remember you pass in to grid by (rows, cols)
        boolean changed = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // go through every index
                Tile tile = grid.getTile(i, j);
                if (tile.getNumber() != -1) {
                    // not empty
                    boolean tempChanged = moveTileUpwards(tile, i, j);
                    if (tempChanged) {
                        // we only need one change to update
                        changed = tempChanged;
                    }
                }
            }
        }
        return changed;
    }


    private static boolean moveAllTilesLeft() {
        // left to right up to down
        // i is row
        // j is col
        // remember you pass in to grid by (rows, cols)
        boolean changed = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // go through every index
                Tile tile = grid.getTile(i, j);
                if (tile.getNumber() != -1) {
                    // not empty
                    boolean tempChanged = moveTileLeftwards(tile, i, j);
                    if (tempChanged) {
                        // we only need one change to update
                        changed = tempChanged;
                    }
                }
            }
        }
        return changed;
    }


    private static boolean moveAllTilesRight() {
        // right to left, up to down
        // i is row
        // j is col
        // remember you pass in to grid by (rows, cols)
        boolean changed = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                // go through every index
                Tile tile = grid.getTile(i, j);
                if (tile.getNumber() != -1) {
                    // not empty
                    boolean tempChanged = moveTileRightwards(tile, i, j);
                    if (tempChanged) {
                        // we only need one change to update
                        changed = tempChanged;
                    }
                }
            }
        }
        return changed;
    }


    private static boolean moveAllTilesDown() {
        // left to right, down to up
        // i is row
        // j is col
        // remember you pass in to grid by (rows, cols)
        boolean changed = false;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                // go through every index
                Tile tile = grid.getTile(i, j);
                if (tile.getNumber() != -1) {
                    // not empty
                    boolean tempChanged = moveTileDownwards(tile, i, j);
                    if (tempChanged) {
                        // we only need one change to update
                        changed = tempChanged;
                    }
                }
            }
        }
        return changed;
    }


    public static void generateAndUpdate(int key) {
        // 0 for left
        // 1 for up
        // 2 for right
        // 3 for down
        boolean changed = false;
        if (key == LEFT) {
            changed = moveAllTilesLeft();
        }
        else if (key == UP) {
            changed = moveAllTilesUp();
        }
        else if (key == RIGHT) {
            changed = moveAllTilesRight();
        }
        else if (key == DOWN) {
            changed = moveAllTilesDown();
        }
        else {
            // How did we get here?
            // do nothing
        }
        if (changed) {
            generateRandomTile();
            mainWindow.repaint();
        }
        checkLoseGame();
    }
}
