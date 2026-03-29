package net.kali.collectives.utils.visuals;

public class Visual {
    public Message message;
    public Sound sound;
    public Particle particle;
    public SingleMessage actionBar;
    public SingleMessage title;

    public Visual(Message message, Sound sound, Particle particle, SingleMessage actionBar, SingleMessage title) {
        this.message = message;
        this.sound = sound;
        this.particle = particle;
        this.actionBar = actionBar;
        this.title = title;
    }
}
