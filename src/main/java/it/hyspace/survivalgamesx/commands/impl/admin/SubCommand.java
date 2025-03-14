package it.hyspace.survivalgamesx.commands.impl.admin;

import it.hyspace.survivalgamesx.enums.MessageConfiguration;
import it.hyspace.survivalgamesx.utils.Console;
import net.enderx.mtlib.utils.ColorsAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class SubCommand {

    private final String name;
    private final String description;
    private final List<String> aliases;

    public SubCommand(String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = Arrays.asList(aliases);
    }

    public abstract void execute(CommandSender sender, String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public boolean matches(String input) {
        return name.equalsIgnoreCase(input) || aliases.contains(input.toLowerCase());
    }

    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    protected void sendMessage(CommandSender sender, MessageConfiguration message) {
        sender.sendMessage(ColorsAPI.color(message.getString()));
    }

    protected void sendMessage(Player player, MessageConfiguration message) {
        player.sendMessage(ColorsAPI.color(message.getString()));
    }

    protected void sendMessage(CommandSender sender, MessageConfiguration lang, String... placeholders) {
        String message = lang.getString();
        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace(placeholders[i], placeholders[i + 1]);
        }
        sender.sendMessage(ColorsAPI.color(message));
    }

    protected void sendMessage(Player player, MessageConfiguration lang, String... placeholders) {
        String message = lang.getString();
        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace(placeholders[i], placeholders[i + 1]);
        }
        player.sendMessage(ColorsAPI.color(message));
    }

    protected void sendMessage(Player player, Exception e) {
        player.sendMessage(ColorsAPI.color(e.getMessage()));
        Console.severe(e.getMessage());
    }
}
