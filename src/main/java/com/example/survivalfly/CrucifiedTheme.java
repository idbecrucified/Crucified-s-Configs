public class CrucifiedTheme {
    private static String currentTheme = "default";

    // Make color methods static
    public static int getPrimaryColor() {
        return 0xFF00FF00; // Replace with your color logic/variable
    }

    public static int getSecondaryColor() {
        return 0xFF005500; // Replace with your color logic/variable
    }

    // Add missing theme getters/setters
    public static String getCurrentTheme() {
        return currentTheme;
    }

    public static void setTheme(String theme) {
        currentTheme = theme;
    }

    // Add missing rendering decoration helper
    public static void renderThemeDecorations(DrawContext context, int x, int y, int width, int height) {
        // Add decorative rendering logic here
    }
}
