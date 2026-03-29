package net.kali.collectives.utils.visuals;

import java.util.List;

public class Message {
    public boolean enabled;
    public List<String> content;

    public Message(boolean enabled, List<String> content) {
        this.enabled = enabled;
        this.content = content;
    }
}
