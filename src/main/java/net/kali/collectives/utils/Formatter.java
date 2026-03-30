package net.kali.collectives.utils;

import net.kali.collectives.Collectives;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Formatter {
    private final Collectives plugin;
    public Formatter(Collectives plugin) {
        this.plugin = plugin;
    }

    public String format(String toFormat, Player player) {
        List<Integer> playerCollectives = plugin.getPlayers().get(player.getUniqueId());

        String amount;
        String maxAmount = String.valueOf(plugin.getConfigManager().getMax());

        if (playerCollectives == null)
            amount = "0";
        else
            amount = String.valueOf(playerCollectives.size());

        //Formatting
        while (toFormat.contains("{player}")) {
            toFormat = toFormat.replace("{player}", player.getName());
        }

        while (toFormat.contains("{amount}")) {
            toFormat = toFormat.replace("{amount}", amount);
        }

        while (toFormat.contains("{maxAmount}")) {
            toFormat = toFormat.replace("{maxAmount}", maxAmount);
        }

        return toFormat;
    }
}
