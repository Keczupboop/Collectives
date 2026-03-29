package net.kali.collectives.utils.visuals;

public class Sound {
    public boolean enabled;
    public org.bukkit.Sound content;

    public Sound(boolean enabled, org.bukkit.Sound content) {
        this.enabled = enabled;
        this.content = content;
    }
}
