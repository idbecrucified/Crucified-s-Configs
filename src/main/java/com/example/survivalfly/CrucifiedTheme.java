package com.example.survivalfly;

public enum CrucifiedTheme {
    GAMER("Gamer", 0xFF8B5CF6, 0xFFA855F7), // Purple / Indigo default
    SEA("Sea", 0xFF0D9488, 0xFF14B8A6),
    SUN("Sun", 0xFFD97706, 0xFFF59E0B),
    OLED("OLED", 0xFF18181B, 0xFF27272A),
    FLASH("Flash", 0xFFDC2626, 0xFFEF4444),
    NATURAL("Natural", 0xFF15803D, 0xFF22C55E),
    ROCK("Rock", 0xFF374151, 0xFF9CA3AF); // Fixed Rock Theme colors to gray/slate

    private final String name;
    private final int primaryColor;
    private final int secondaryColor;

    private static CrucifiedTheme currentTheme = GAMER; // Gamer set as default

    CrucifiedTheme(String name, int primaryColor, int secondaryColor) {
        this.name = name;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public String getName() { return name; }
    public int getPrimaryColor() { return primaryColor; }
    public int getSecondaryColor() { return secondaryColor; }

    public static CrucifiedTheme getCurrentTheme() { return currentTheme; }
    public static void setCurrentTheme(CrucifiedTheme theme) { currentTheme = theme; }
}
