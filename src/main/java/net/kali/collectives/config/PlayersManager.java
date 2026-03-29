package net.kali.collectives.config;

import net.kali.collectives.Collectives;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayersManager {
    private final Collectives plugin;

    private File playersFile;
    private YamlConfiguration config;
    public PlayersManager(Collectives plugin) {
        this.plugin = plugin;

        setup();
    }

    public void setup() {
        playersFile = new File(plugin.getDataFolder(), "players.yml");

        if (!playersFile.exists()) {
            playersFile.getParentFile().mkdirs();
            plugin.saveResource("players.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(playersFile);
    }

    public Map<UUID, List<Integer>> getPlayers() {
        if (config == null) return new HashMap<>();
        Map<UUID, List<Integer>> players = new HashMap<>();

        for (String key : config.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            List<Integer> value = new ArrayList<>();

            value = config.getIntegerList(key);

            players.put(uuid, value);
        }

        return players;
    }

    public void savePlayers(Map<UUID, List<Integer>> players) {
        if (config == null) return;

        for (UUID uuid : players.keySet()) {
            config.set(uuid.toString(), players.get(uuid));
        }

        saveData();
    }

    private void saveData() {
        try {
            config.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
