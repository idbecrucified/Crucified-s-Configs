package com.example.survivalfly;

public class CrucifiedsConfigs {
    // PvP & HUD Elements
    public static boolean toggleSprint = true;
    public static boolean totemCounter = true;
    public static boolean armorStatus = true;
    public static boolean cpsDisplay = true;
    public static boolean keystrokes = true;
    public static boolean fpsCounter = true;
    
    // HUD Positions
    public static int fpsCounterX = 5;
    public static int fpsCounterY = 5;
    public static int totemCounterX = 100;
    public static int totemCounterY = 100;
    public static int keystrokesX = 5;
    public static int keystrokesY = 65;
    public static int cpsDisplayX = 5;
    public static int cpsDisplayY = 45;
    public static int armorStatusX = -25; // Relative to right side by default
    public static int armorStatusY = -50; // Relative to center by default

    // Performance
    public static boolean entityCulling = true;
    public static boolean fpsBooster = false;
    public static boolean chunkAnimator = false;
    public static boolean particleMultiplier = false;

    // Graphics
    public static boolean fullbright = true;
    public static boolean dynamicLighting = false;
    public static boolean minimalHud = false;
    public static boolean weatherChanger = false;
}
