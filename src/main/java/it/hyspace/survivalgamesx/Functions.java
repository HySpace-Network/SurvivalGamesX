package it.hyspace.survivalgamesx;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Functions {

    private final List<Player> playersInGame = new ArrayList<>();
    private final List<Player> playersInSpectator = new ArrayList<>();



}
