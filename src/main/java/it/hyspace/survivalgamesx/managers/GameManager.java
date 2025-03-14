package it.hyspace.survivalgamesx.managers;

import it.hyspace.survivalgamesx.SGPlugin;
import it.hyspace.survivalgamesx.enums.game.GameState;
import lombok.Getter;
import it.hyspace.survivalgamesx.enums.MessageConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class GameManager {

    private final SGPlugin plugin = SGPlugin.getInstance();
    private final PlayerManager playerManager = new PlayerManager();
    private GameState gameState = GameState.WAITING;

    public void setGameState(GameState gameState) {
        if (this.gameState == GameState.INGAME && gameState == GameState.STARTING) return;
        if (this.gameState == gameState) return;

        Bukkit.getScheduler().runTask(plugin, () -> {
            this.gameState = gameState;


            switch (gameState) {
                case WAITING:
                    Bukkit.broadcastMessage(plugin.getMessages().getString("waiting-player"));
                    SGPlugin.getInstance().getGameManager().playerManager.waitingPlayer();
                    break;
                case STARTING:
                    if (SGPlugin.getInstance().getFunctions().canStartGame()) {
                        SGPlugin.getInstance().getFunctions().startGameCountdown();
                        SGPlugin.getInstance().getFunctions().preparePlayersForGame();
                    } else {
                        Bukkit.broadcastMessage(MessageConfiguration.NOT_ENOUGH_PLAYER.getString());
                        setGameState(GameState.WAITING);
                    }

                    break;
                case INGAME:
                    if (SGPlugin.getInstance().getFunctions().getGameStartCountdownTask() != null) {
                        SGPlugin.getInstance().getFunctions().getGameStartCountdownTask().cancel();
                    }
                    Bukkit.broadcastMessage(plugin.getMessages().getString("game-start"));
                    SGPlugin.getInstance().getFunctions().startGame();
                    break;
                case END:
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.getInventory().clear();
                    }

                    setGameState(GameState.RESTARTING);
                    break;
                case RESTARTING:
                    SGPlugin.getInstance().getFunctions().getPlayersInGame().clear();
                    SGPlugin.getInstance().getFunctions().getPlayerInSpectator().clear();
                    SGPlugin.getInstance().getFunctions().restartGame();
                    break;
            }
        });
    }
}
