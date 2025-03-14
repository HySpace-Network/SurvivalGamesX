package it.hyspace.survivalgamesx.task;

import it.hyspace.survivalgamesx.SGPlugin;
import it.hyspace.survivalgamesx.enums.game.GameState;
import net.enderx.mtlib.scoreboard.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardTask {

    private int taskId = -1;
    int countdown = 0;
    // Player champion = SumoPlugin.getInstance().getFunctions().getPlayersInGame().get(0);

    public void startUpdating() {
        taskId = Bukkit.getScheduler().runTaskTimer(SGPlugin.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                onScoreboardUpdate(player);
            }
        }, 20L, 20L).getTaskId();
    }

    public void stopUpdating() {
        if(taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

    public void onScoreboardUpdate(Player player) {
        for (int i = 1; i <= 5; i++) {
            if (player.getScoreboard().getTeam("line" + i) != null) {
                ScoreboardUtils.removeLine(player, i);
            }
        }

        GameState state = SGPlugin.getInstance().getGameManager().getGameState();
        switch (state) {
            case WAITING:
                break;
            case STARTING:
                break;
            case INGAME:
                break;

            case RESTARTING:
                break;
            default:
                break;
        }
    }

}
