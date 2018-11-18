import java.awt.Color;

public class Tile {
    private Color color;
    private int number;

    public static final int WIDTH = 168;
    public static final int HEIGHT = 168;

    public static final int EMPTY = -1;

    // Tile Colors
    public static final Color EMPTYCOLOR = new Color(205, 192, 180);
    public static final Color TWOCOLOR = new Color(238, 228, 218);
    public static final Color FOURCOLOR = new Color(237, 224, 200);
    public static final Color EIGHTCOLOR = new Color(242, 177, 121);
    public static final Color SIXTEENCOLOR = new Color(245, 149, 99);
    public static final Color THIRTYTWOCOLOR = new Color(246, 124, 95);
    public static final Color SIXTYFOURCOLOR = new Color(246, 94, 59);
    public static final Color ONETWENTYEIGHTCOLOR = new Color(237, 207, 114);
    public static final Color TWOFIFTYSIXCOLOR = new Color(237, 204, 97);
    public static final Color FIVETWELVECOLOR = new Color(237, 200, 80);
    public static final Color ONEZEROTWOFOURCOLOR = new Color(237, 197, 63);
    public static final Color TWOZEROFOUREIGHTCOLOR = new Color(237, 194, 46);

    public static final Color PRIMARYTEXTCOLOR = new Color(119, 110, 101); // 2,4
    public static final Color SECONDARYTEXTCOLOR = new Color(249, 246, 242); // 8,16,32,64,128,256,
                                                                             // 512,
                                                                             // 1024


    // default tile is empty
    public Tile() {
        number = -1;
        color = EMPTYCOLOR;
    }


    public Tile(int number) {
        this.number = number;
        determineColor();
    }


    private void determineColor() {
        switch (number) {
            case -1:
                color = EMPTYCOLOR;
                break;
            case 2:
                color = TWOCOLOR;
                break;
            case 4:
                color = FOURCOLOR;
                break;
            case 8:
                color = EIGHTCOLOR;
                break;
            case 16:
                color = SIXTEENCOLOR;
                break;
            case 32:
                color = THIRTYTWOCOLOR;
                break;
            case 64:
                color = SIXTYFOURCOLOR;
                break;
            case 128:
                color = ONETWENTYEIGHTCOLOR;
                break;
            case 256:
                color = TWOFIFTYSIXCOLOR;
                break;
            case 512:
                color = FIVETWELVECOLOR;
                break;
            case 1024:
                color = ONEZEROTWOFOURCOLOR;
                break;
            case 2048:
                color = TWOZEROFOUREIGHTCOLOR;
                break;
            default:
                color = EMPTYCOLOR;
                break;
        }
    }


    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }


    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }
}
