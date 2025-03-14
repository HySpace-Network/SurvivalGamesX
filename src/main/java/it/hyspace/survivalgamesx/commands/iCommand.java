package it.hyspace.survivalgamesx.commands;

import it.hyspace.survivalgamesx.SGPlugin;
import it.hyspace.survivalgamesx.enums.MessageConfiguration;
import it.hyspace.survivalgamesx.utils.Console;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import net.enderx.mtlib.utils.ColorsAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public abstract class iCommand implements CommandExecutor, TabCompleter {


    private final String command;

    // Array delle stringhe che rappresentano i permessi richiesti per eseguire il comando
    private final String[] permissions_array;

    // Indica se il comando supporta il tab-complete
    private final Boolean tabComplete;

    // Descrizione del comando
    private final String description;

    // Lista dei permessi con wildcard inclusi
    private final List<String> permissions_list = new ArrayList<>();

    // Lista degli alias del comando
    private final List<String> aliases;

    /**
     * Costruttore principale per inizializzare il comando con alias.
     *
     * @param command Nome del comando principale.
     * @param tabComplete Indica se il comando supporta il tab-complete.
     * @param permissions_array Array dei permessi richiesti per il comando.
     * @param description Descrizione del comando.
     * @param aliases Alias del comando (parametro opzionale).
     */

    public iCommand(String command, Boolean tabComplete, String[] permissions_array, String description, String... aliases) {
        this.command = command;
        this.permissions_array = permissions_array;
        this.tabComplete = tabComplete;
        this.description = description;
        this.aliases = Arrays.asList(aliases);
    }

    /**
     * Costruttore alternativo senza alias.
     *
     * @param command Nome del comando.
     * @param tabComplete Indica se il comando supporta il tab-complete.
     * @param permissions_array Array dei permessi richiesti per il comando.
     * @param description Descrizione del comando.
     */

    public iCommand(String command, Boolean tabComplete, String[] permissions_array, String description) {
        this.command = command;
        this.permissions_array = permissions_array;
        this.tabComplete = tabComplete;
        this.description = description;
        this.aliases = Collections.emptyList(); // Se non ci sono alias, la lista sarà vuota
    }

    /**
     * Metodo eseguito quando il comando viene eseguito.
     * Usa il task scheduler di Bukkit per eseguire `executeCommand` nel thread principale.
     *
     * @param sender Il mittente del comando (può essere un player o la console).
     * @param command Il comando eseguito.
     * @param label L'etichetta del comando.
     * @param args Gli argomenti del comando.
     * @return Sempre `true` per indicare che il comando è stato gestito.
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getScheduler().runTask(SGPlugin.getInstance(), () -> executeCommand(sender, args));
        return true;
    }

    /**
     * Metodo privato che controlla i permessi e poi esegue il comando.
     *
     * @param sender Il mittente del comando.
     * @param args Gli argomenti passati al comando.
     * @return `true` se il comando è stato eseguito con successo, altrimenti `false`.
     */

    private boolean executeCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender) && (sender instanceof Player)) {
            sender.sendMessage(MessageConfiguration.NO_PERMS.getString()); // Invia un messaggio di "permessi insufficienti"
            return false;
        }
        execute(sender, args); // Chiama il metodo astratto che sarà implementato nelle classi figlie
        return true;
    }

    /**
     * Controlla se il mittente ha il permesso necessario per eseguire il comando.
     *
     * @param sender Il mittente del comando.
     * @return `true` se il mittente ha il permesso, `false` altrimenti.
     */


    public boolean hasPermission(CommandSender sender) {
        if (Arrays.stream(this.permissions_array)
                .collect(Collectors.toList())
                .isEmpty()) return true;// Se non ci sono permessi specificati, chiunque può eseguire il comando

        // Aggiunge i permessi wildcard
        for (String permission : this.permissions_array) {
            permissions_list.add("*");
            permissions_list.add("sumo.*");
            permissions_list.add("sumo.command.*");
            permissions_list.add(permission);
        }

        // Controlla se il sender ha uno dei permessi nella lista
        for (String permission : permissions_list)
            if (sender.hasPermission(permission)) return true;

        return false;
    }

    /**
     * Metodo astratto che deve essere implementato dalle sottoclassi per definire la logica del comando.
     *
     * @param sender Il mittente del comando.
     * @param args Gli argomenti del comando.
     */
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Invia un messaggio predefinito a un `CommandSender` utilizzando il sistema `MessageConfiguration`.
     *
     * @param sender Il destinatario del messaggio.
     * @param message Il messaggio da inviare.
     */
    protected void sendMessage(CommandSender sender, MessageConfiguration message) {
        sender.sendMessage(message.getString());
    }

    /**
     * Invia un messaggio predefinito a un `Player` utilizzando il sistema `MessageConfiguration`.
     *
     * @param player Il destinatario del messaggio.
     * @param message Il messaggio da inviare.
     */
    protected void sendMessage(Player player, MessageConfiguration message) {
        player.sendMessage(message.getString());
    }

    /**
     * Invia un messaggio con placeholder a un `CommandSender`.
     *
     * @param sender Il destinatario del messaggio.
     * @param lang Il messaggio da inviare.
     * @param placeholders Placeholder da sostituire nel messaggio.
     */
    protected void sendMessage(CommandSender sender, MessageConfiguration lang, String... placeholders) {
        String message = lang.getString();
        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace(placeholders[i], placeholders[i + 1]);
        }
        sender.sendMessage(ColorsAPI.color(message));
    }

    /**
     * Invia un messaggio con placeholder a un `Player`.
     *
     * @param player Il destinatario del messaggio.
     * @param lang Il messaggio da inviare.
     * @param placeholders Placeholder da sostituire nel messaggio.
     */
    protected void sendMessage(Player player, MessageConfiguration lang, String... placeholders) {
        String message = lang.getString();
        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace(placeholders[i], placeholders[i + 1]);
        }
        player.sendMessage(ColorsAPI.color(message));
    }

    /**
     * Invia un messaggio di errore a un `Player` e registra l'errore nella console.
     *
     * @param player Il destinatario del messaggio.
     * @param e L'eccezione da registrare.
     */
    protected void sendMessage(Player player, Exception e) {
        player.sendMessage(ColorsAPI.color(e.getMessage()));
        Console.severe(e.getMessage()); // Registra l'errore nella console
    }

    /**
     * Metodo che dovrebbe gestire il tab-complete per il comando, ma non è ancora implementato.
     * Al momento lancia un'eccezione.
     *
     * @param sender Il mittente del comando.
     * @param command Il comando eseguito.
     * @param alias L'alias usato.
     * @param args Gli argomenti digitati.
     * @return Una lista di suggerimenti per il completamento del comando.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
