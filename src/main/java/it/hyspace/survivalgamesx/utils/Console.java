package it.hyspace.survivalgamesx.utils;

import org.bukkit.Bukkit;

public class Console {

    public static void info(String message) {
        Bukkit.getLogger().info(message);
    }
    public static void warning(String message) {
        Bukkit.getLogger().warning(message);
    }
    public static void error(String message) {
        Bukkit.getLogger().severe(message);
    }
    public static void severe(String message) {
        Bukkit.getLogger().severe(message);
    }
}
