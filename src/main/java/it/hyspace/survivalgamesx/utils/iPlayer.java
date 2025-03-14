package it.hyspace.survivalgamesx.utils;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public interface iPlayer {

    void registerPlayer(UUID uuid);
    boolean isRegistered(OfflinePlayer player);
    void addKill(UUID uuid);
    int getKills(UUID uuid);
}
