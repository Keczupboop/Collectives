package net.kali.collectives.utils.visuals;

public class Particle {
    public boolean enabled;
    public org.bukkit.Particle type;
    public double x;
    public double y;
    public double z;
    public int count;
    public double offsetX;
    public double offsetY;
    public double offsetZ;
    public double speed;

    public Particle(boolean enabled, org.bukkit.Particle type, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed) {
        this.enabled = enabled;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
    }
}
