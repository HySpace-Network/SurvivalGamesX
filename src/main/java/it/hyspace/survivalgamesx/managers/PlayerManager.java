package it.hyspace.survivalgamesx.managers;

import it.hyspace.survivalgamesx.SGPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerManager {

    public PlayerManager (){}

    public void waitingPlayer(){
        Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SURVIVAL));
    }

    public void giveKits() {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getGameMode() == GameMode.SURVIVAL)
                .forEach(this::giveKit);
    }

    public void resetPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            resetPlayer(player);
        }

        SGPlugin.getInstance().getFunctions().getPlayersInGame().clear();
    }

    private void giveKit(Player player) {
        player.getInventory().clear();
        //player.getInventory().addItem();
    }

    private void resetPlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        //player.teleport(SPAWN_LOCATION);
        player.getInventory().clear();

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }
}
