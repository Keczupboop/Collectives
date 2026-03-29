package net.kali.collectives.utils;

import org.bukkit.entity.Player;

public class Formatter {
    public String format(String toFormat, Player player) {
        while (toFormat.contains("{player}")) {
            toFormat = toFormat.replace("{player}", player.getName());
        }

        return toFormat;
    }
}
