package it.hyspace.survivalgamesx.hooks;

import it.hyspace.survivalgamesx.SGPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PapiHook extends PlaceholderExpansion {

    private final SGPlugin plugin;

    public PapiHook(SGPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return "HySpace Team";
    }

    @Override
    public String getIdentifier(){
        return "survivalgames";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){
        return null;
    }


}
