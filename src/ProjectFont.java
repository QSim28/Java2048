import java.awt.Font;
import java.io.InputStream;

public class ProjectFont {
    public static final int PRIMARYFONTSIZE = 90;
    public static final int SECONDARYFONTSIZE = 65;
    public static final int THIRDFONTSIZE = 55;

    private static Font font = setUp(PRIMARYFONTSIZE);


    private static Font setUp(int fontSize) {
        Font font = null;
        try {
            InputStream is = ProjectFont.class.getClassLoader()
                .getResourceAsStream("resources/ClearSans-Bold.ttf");

            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD,
                fontSize);
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
