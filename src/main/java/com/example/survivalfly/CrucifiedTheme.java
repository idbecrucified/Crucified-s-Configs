package com.example.survivalfly;

public class CrucifiedTheme {
    public static String currentTheme = "Default";

    public static int getPrimaryColor() {
        if ("Purple".equals(currentTheme)) return 0xFF9933FF;
        if ("Blue".equals(currentTheme)) return 0xFF3399FF;
        if ("Green".equals(currentTheme)) return 0xFF33FF99;
        return 0xFFFF007F;
    }

    public static int getSecondaryColor() {
        if ("Purple".equals(currentTheme)) return 0xFFCC66FF;
        if ("Blue".equals(currentTheme)) return 0xFF66CCFF;
        if ("Green".equals(currentTheme)) return 0xFF66FFCC;
        return 0xFFFF69B4;
    }

    public static int getBackgroundColor() {
        return 0xCC1a1c23;
    }

    public static int getHeaderColor() {
        return 0xCC252836;
    }

    public static int getAccentColor() {
        return getPrimaryColor();
    }
}
