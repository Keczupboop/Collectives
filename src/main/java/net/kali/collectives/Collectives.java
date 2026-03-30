package net.kali.collectives;

import net.kali.collectives.config.ConfigManager;
import net.kali.collectives.config.PlayersManager;
import net.kali.collectives.utils.Formatter;
import net.kali.collectives.utils.visuals.Visual;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Collectives extends JavaPlugin {
    private ConfigManager configManager;
    private PlayersManager playersManager;
    private Map<UUID, List<Integer>> players;

    private Map<Integer, Visual> visuals;
    private Visual alrFound;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        playersManager = new PlayersManager(this);

        players = playersManager.getPlayers();
        visuals = configManager.getVisuals();
        alrFound = configManager.getAlreadyFound();

        net.kali.collectives.cmds.Collectives cmd = new net.kali.collectives.cmds.Collectives(this);
        getCommand("collectives").setExecutor(cmd);
        getCommand("collectives").setTabCompleter(cmd);
    }

    @Override
    public void onDisable() {
        save();
    }

    public void save() {
        playersManager.savePlayers(players);
    }

    public void reload() {
        configManager.setup();
        reloadConfig();
        playersManager.setup();

        players = playersManager.getPlayers();
        visuals = configManager.getVisuals();
        alrFound = configManager.getAlreadyFound();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Map<UUID, List<Integer>> getPlayers() {
        return players;
    }

    public Map<Integer, Visual> getVisuals() {
        return visuals;
    }

    public Visual getAlrFound() {
        return alrFound;
    }
}
