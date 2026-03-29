package net.kali.collectives.config;

import net.kali.collectives.Collectives;
import net.kali.collectives.utils.Won;
import net.kali.collectives.utils.visuals.*;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.*;

public class ConfigManager {
    private final Collectives plugin;
    public ConfigManager(Collectives plugin) {
        this.plugin = plugin;
        setup();
    }

    public void setup() {
        File mainDir = plugin.getDataFolder();
        if (!mainDir.exists()) mainDir.mkdir();

        plugin.saveDefaultConfig();
    }

    public int getMax() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("max")) return 0;

        return config.getInt("max");
    }

    public Map<Integer, Visual> getVisuals() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("visuals")) return new HashMap<>();

        ConfigurationSection visuals = config.getConfigurationSection("visuals");
        Map<Integer, Visual> visualsMap = new HashMap<>();

        for (String key : visuals.getKeys(false)) {
            int intKey;
            try {
                intKey = Integer.parseInt(key);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return new HashMap<>();
            }

            ConfigurationSection currentVisual = visuals.getConfigurationSection(key);

            //Message
            Message message = new Message(false, Arrays.asList(""));
            if (currentVisual.contains("messages")) {
                ConfigurationSection messages = currentVisual.getConfigurationSection("messages");
                if (messages.contains("enabled") && messages.getBoolean("enabled")) {
                    message.enabled = true;
                    if (messages.contains("content")) {
                        message.content = messages.getStringList("content");
                    }
                }
            }

            //Sound
            Sound sound = new Sound(false, org.bukkit.Sound.ENTITY_FOX_EAT);
            if (currentVisual.contains("sound")) {
                ConfigurationSection soundSection = currentVisual.getConfigurationSection("sound");
                if (soundSection.contains("enabled") && soundSection.getBoolean("enabled")) {
                    sound.enabled = true;
                    if (soundSection.contains("content")) {
                        sound.content = Registry.SOUNDS.get(NamespacedKey.minecraft(soundSection.getString("content")));
                    }
                }
            }

            //Particles
            Particle particle = new Particle(false, org.bukkit.Particle.EXPLOSION, 0, 0, 0, 20, 0, 0, 0, 0.1);
            if (currentVisual.contains("particles")) {
                ConfigurationSection particlesSection = currentVisual.getConfigurationSection("particles");
                if (particlesSection.contains("enabled") && particlesSection.getBoolean("enabled")) {
                    particle.enabled = true;
                    if (particlesSection.contains("type") && particlesSection.contains("pos") && particlesSection.contains("count") && particlesSection.contains("offsets") && particlesSection.contains("speed")) {
                        particle.type = Registry.PARTICLE_TYPE.get(NamespacedKey.minecraft(particlesSection.getString("type")));

                        List<Double> pos = particlesSection.getDoubleList("pos");
                        particle.x = pos.getFirst();
                        particle.y = pos.get(1);
                        particle.z = pos.get(2);

                        particle.count = particlesSection.getInt("count");

                        List<Double> offsets = particlesSection.getDoubleList("offsets");
                        particle.offsetX = offsets.getFirst();
                        particle.offsetY = offsets.get(1);
                        particle.offsetZ = offsets.get(2);

                        particle.speed = particlesSection.getInt("speed");
                    }
                }
            }

            //ActionBar
            SingleMessage actionbar = new SingleMessage(false, "");
            if (currentVisual.contains("actionbar")) {
                ConfigurationSection actionbarSection = currentVisual.getConfigurationSection("actionbar");
                if (actionbarSection.contains("enabled") && actionbarSection.getBoolean("enabled")) {
                    actionbar.enabled = true;
                    if (actionbarSection.contains("content")) {
                        actionbar.content = actionbarSection.getString("content");
                    }
                }
            }

            //Title
            SingleMessage title = new SingleMessage(false, "");
            if (currentVisual.contains("title")) {
                ConfigurationSection titleSection = currentVisual.getConfigurationSection("title");
                if (titleSection.contains("enabled") && titleSection.getBoolean("enabled")) {
                    title.enabled = true;
                    if (titleSection.contains("content")) {
                        title.content = titleSection.getString("content");
                    }
                }
            }

            //Creating VISUAL
            Visual visual = new Visual(message, sound, particle, actionbar, title);
            visualsMap.put(intKey, visual);
        }
        return visualsMap;
    }

    public Won getWon() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("won")) return null;

        ConfigurationSection wonSection = config.getConfigurationSection("won");
        List<String> cmds = wonSection.getStringList("commands");

        //Message
        Message message = new Message(false, Arrays.asList(""));
        if (wonSection.contains("messages")) {
            ConfigurationSection messages = wonSection.getConfigurationSection("messages");
            if (messages.contains("enabled") && messages.getBoolean("enabled")) {
                message.enabled = true;
                if (messages.contains("content")) {
                    message.content = messages.getStringList("content");
                }
            }
        }

        //Sound
        Sound sound = new Sound(false, org.bukkit.Sound.ENTITY_FOX_EAT);
        if (wonSection.contains("sound")) {
            ConfigurationSection soundSection = wonSection.getConfigurationSection("sound");
            if (soundSection.contains("enabled") && soundSection.getBoolean("enabled")) {
                sound.enabled = true;
                if (soundSection.contains("content")) {
                    sound.content = Registry.SOUNDS.get(NamespacedKey.minecraft(soundSection.getString("content")));
                }
            }
        }

        //Particles
        Particle particle = new Particle(false, org.bukkit.Particle.EXPLOSION, 0, 0, 0, 20, 0, 0, 0, 0.1);
        if (wonSection.contains("particles")) {
            ConfigurationSection particlesSection = wonSection.getConfigurationSection("particles");
            if (particlesSection.contains("enabled") && particlesSection.getBoolean("enabled")) {
                particle.enabled = true;
                if (particlesSection.contains("type") && particlesSection.contains("pos") && particlesSection.contains("count") && particlesSection.contains("offsets") && particlesSection.contains("speed")) {
                    particle.type = Registry.PARTICLE_TYPE.get(NamespacedKey.minecraft(particlesSection.getString("type")));

                    List<Double> pos = particlesSection.getDoubleList("pos");
                    particle.x = pos.getFirst();
                    particle.y = pos.get(1);
                    particle.z = pos.get(2);

                    particle.count = particlesSection.getInt("count");

                    List<Double> offsets = particlesSection.getDoubleList("offsets");
                    particle.offsetX = offsets.getFirst();
                    particle.offsetY = offsets.get(1);
                    particle.offsetZ = offsets.get(2);

                    particle.speed = particlesSection.getInt("speed");
                }
            }
        }

        //ActionBar
        SingleMessage actionbar = new SingleMessage(false, "");
        if (wonSection.contains("actionbar")) {
            ConfigurationSection actionbarSection = wonSection.getConfigurationSection("actionbar");
            if (actionbarSection.contains("enabled") && actionbarSection.getBoolean("enabled")) {
                actionbar.enabled = true;
                if (actionbarSection.contains("content")) {
                    actionbar.content = actionbarSection.getString("content");
                }
            }
        }

        //Title
        SingleMessage title = new SingleMessage(false, "");
        if (wonSection.contains("title")) {
            ConfigurationSection titleSection = wonSection.getConfigurationSection("title");
            if (titleSection.contains("enabled") && titleSection.getBoolean("enabled")) {
                title.enabled = true;
                if (titleSection.contains("content")) {
                    title.content = titleSection.getString("content");
                }
            }
        }

        return new Won(cmds, new Visual(message, sound, particle, actionbar, title));
    }

    public Visual getAlreadyFound() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("already_found")) return null;

        ConfigurationSection alrFoundSection = config.getConfigurationSection("already_found");

        //Message
        Message message = new Message(false, Arrays.asList(""));
        if (alrFoundSection.contains("messages")) {
            ConfigurationSection messages = alrFoundSection.getConfigurationSection("messages");
            if (messages.contains("enabled") && messages.getBoolean("enabled")) {
                message.enabled = true;
                if (messages.contains("content")) {
                    message.content = messages.getStringList("content");
                }
            }
        }

        //Sound
        Sound sound = new Sound(false, org.bukkit.Sound.ENTITY_FOX_EAT);
        if (alrFoundSection.contains("sound")) {
            ConfigurationSection soundSection = alrFoundSection.getConfigurationSection("sound");
            if (soundSection.contains("enabled") && soundSection.getBoolean("enabled")) {
                sound.enabled = true;
                if (soundSection.contains("content")) {
                    sound.content = Registry.SOUNDS.get(NamespacedKey.minecraft(soundSection.getString("content")));
                }
            }
        }

        //Particles
        Particle particle = new Particle(false, org.bukkit.Particle.EXPLOSION, 0, 0, 0, 20, 0, 0, 0, 0.1);
        if (alrFoundSection.contains("particles")) {
            ConfigurationSection particlesSection = alrFoundSection.getConfigurationSection("particles");
            if (particlesSection.contains("enabled") && particlesSection.getBoolean("enabled")) {
                particle.enabled = true;
                if (particlesSection.contains("type") && particlesSection.contains("pos") && particlesSection.contains("count") && particlesSection.contains("offsets") && particlesSection.contains("speed")) {
                    particle.type = Registry.PARTICLE_TYPE.get(NamespacedKey.minecraft(particlesSection.getString("type")));

                    List<Double> pos = particlesSection.getDoubleList("pos");
                    particle.x = pos.getFirst();
                    particle.y = pos.get(1);
                    particle.z = pos.get(2);

                    particle.count = particlesSection.getInt("count");

                    List<Double> offsets = particlesSection.getDoubleList("offsets");
                    particle.offsetX = offsets.getFirst();
                    particle.offsetY = offsets.get(1);
                    particle.offsetZ = offsets.get(2);

                    particle.speed = particlesSection.getInt("speed");
                }
            }
        }

        //ActionBar
        SingleMessage actionbar = new SingleMessage(false, "");
        if (alrFoundSection.contains("actionbar")) {
            ConfigurationSection actionbarSection = alrFoundSection.getConfigurationSection("actionbar");
            if (actionbarSection.contains("enabled") && actionbarSection.getBoolean("enabled")) {
                actionbar.enabled = true;
                if (actionbarSection.contains("content")) {
                    actionbar.content = actionbarSection.getString("content");
                }
            }
        }

        //Title
        SingleMessage title = new SingleMessage(false, "");
        if (alrFoundSection.contains("title")) {
            ConfigurationSection titleSection = alrFoundSection.getConfigurationSection("title");
            if (titleSection.contains("enabled") && titleSection.getBoolean("enabled")) {
                title.enabled = true;
                if (titleSection.contains("content")) {
                    title.content = titleSection.getString("content");
                }
            }
        }

        return new Visual(message, sound, particle, actionbar, title);
    }
}
