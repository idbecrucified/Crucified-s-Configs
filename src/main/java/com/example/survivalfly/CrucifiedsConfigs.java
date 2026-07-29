package com.example.survivalfly;

public class CrucifiedsConfigs {
    public static String currentTheme = "Gamer";

    // PvP / HUD Options
    public static boolean toggleSprint = true;
    public static boolean cpsDisplay = true;
    public static boolean keystrokes = true;
    public static boolean armorStatus = true;
    public static boolean totemCounter = true;
    public static boolean fpsCounter = true;

    // HUD Customization Settings
    public static boolean hudBackground = true;
    public static int hudBackgroundColor = 0x80000000; // Semi-transparent dark
    public static int hudTextColor = 0xFFFFFFFF;       // White text

    // Performance Options
    public static boolean fastRender = true;
    public static boolean fpsBoost = false;

    // Graphics Options
    public static boolean fullbright = false;
    public static boolean smoothLighting = true;

    // HUD Coordinates for Dragging & Positioning
    public static int fpsX = 5;
    public static int fpsY = 5;
    public static int keystrokesX = 5;
    public static int keystrokesY = 25;
    public static int armorX = 5;
    public static int armorY = 70;
    public static int cpsX = 5;
    public static int cpsY = 120;
    public static int totemX = 5;
    public static int totemY = 150;

    public static void resetHudPositions() {
        fpsX = 5; fpsY = 5;
        keystrokesX = 5; keystrokesY = 25;
        armorX = 5; armorY = 70;
        cpsX = 5; cpsY = 120;
        totemX = 5; totemY = 150;
    }
}
