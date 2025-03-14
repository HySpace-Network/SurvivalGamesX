package it.hyspace.survivalgamesx;

import it.hyspace.survivalgamesx.managers.GameManager;
import it.hyspace.survivalgamesx.task.ScoreboardTask;
import lombok.Getter;
import net.enderx.mtlib.MTLib;
import net.enderx.mtlib.configuration.YMLConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SGPlugin extends JavaPlugin {

    @Getter private static SGPlugin instance;
    @Getter private MTLib mtLib;
    @Getter private YMLConfiguration messages;
    @Getter private Functions functions;
    @Getter private GameManager gameManager;
    @Getter private ScoreboardTask scoreboardTask;

    @Override
    public void onEnable() {
        instance = this;
        this.mtLib = new MTLib(this);

        this.functions = new Functions();
        this.gameManager = new GameManager();
        this.scoreboardTask = new ScoreboardTask();
        this.scoreboardTask.startUpdating();


        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}