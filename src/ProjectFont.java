import java.awt.Font;
import java.io.File;

public class ProjectFont {
    public static final int PRIMARYFONTSIZE = 90;
    public static final int SECONDARYFONTSIZE = 65;
    public static final int THIRDFONTSIZE = 55;

    private static Font font = setUp(PRIMARYFONTSIZE);


    private static Font setUp(int fontSize) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(
                "ClearSans-Bold.ttf")).deriveFont(Font.BOLD, fontSize);
        }
        catch (Exception e) {
            // use a default Serif font
            font = new Font("serif", Font.PLAIN, fontSize);
        }
        return font;
    }


    public static Font font() {
        return font;
    }

}
