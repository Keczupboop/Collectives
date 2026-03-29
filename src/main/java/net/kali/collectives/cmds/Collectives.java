package net.kali.collectives.cmds;

import net.kali.collectives.config.ConfigManager;
import net.kali.collectives.utils.Formatter;
import net.kali.collectives.utils.Won;
import net.kali.collectives.utils.visuals.Visual;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Collectives implements CommandExecutor, TabCompleter {
    private final net.kali.collectives.Collectives plugin;
    public Collectives(net.kali.collectives.Collectives plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length < 4) {
            if (args.length == 0) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: <yellow>/collectives {mark/reload/save} {player} {found} {findID}"));
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reload();
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Plugin reloaded!"));
                return true;
            }
            else if (args[0].equalsIgnoreCase("save")) {
                plugin.save();
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Successfully saved data!"));
                return true;
            }

            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: <yellow>/collectives {mark/reload/save} {player} {found/notFound} {findID}"));
            return true;
        }

        if (args[0].equalsIgnoreCase("mark")) {
            //Checking is player online
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Player <yellow>" + args[1] + " <red>is offline!"));
                return true;
            }

            //Parsing ID
            int ID;
            try {
                ID = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>'findID' must be an integer value!"));
                return true;
            }

            //Checking third argument
            if (args[2].equalsIgnoreCase("found")) {
                UUID targetUUID = target.getUniqueId();

                //Getting existing data
                Map<UUID, List<Integer>> players = plugin.getPlayers();
                //Target player data
                List<Integer> finds = players.get(targetUUID);

                ConfigManager configManager = plugin.getConfigManager();

                //Checking is ID bigger than max amount of collectibles
                if (ID > configManager.getMax()) {
                    target.sendMessage(MiniMessage.miniMessage().deserialize("<red>ID <yellow>" + ID + " <red>is larger than max amount!"));
                    return true;
                }

                if (finds == null) {
                    finds = new ArrayList<>();
                }
                else if (finds.contains(ID)) {
                    //==================//
                    //   AlreadyFound   //
                    //==================//
                    Visual alrFound = plugin.getAlrFound();
                    parseVisual(alrFound, target);

                    return true;
                }

                finds.add(ID);
                players.put(targetUUID, finds);

                int size = finds.size();

                //==================//
                //      Visuals     //
                //==================//
                Map<Integer, Visual> visuals = plugin.getVisuals();
                Visual visual = visuals.get(ID);

                parseVisual(visual, target);

                //==================//
                //        Won       //
                //==================//
                if (size == configManager.getMax()) {
                    Won won = configManager.getWon();
                    Formatter formatter = plugin.getFormatter();

                    for (String cmd : won.commands) {
                        String toSend = formatter.format(cmd, target);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), toSend);
                    }

                    //Visual
                    parseVisual(won.visual, target);
                }
            }
            else if (args[2].equalsIgnoreCase("notFound")) {
                UUID targetUUID = target.getUniqueId();

                //Getting existing data
                Map<UUID, List<Integer>> players = plugin.getPlayers();
                //Target player data
                List<Integer> finds = players.get(targetUUID);

                ConfigManager configManager = plugin.getConfigManager();

                //Checking is ID bigger than max amount of collectibles
                if (ID > configManager.getMax()) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>ID <yellow>" + ID + " <red>is larger than max amount!"));
                    return true;
                }

                if (finds != null) {
                    if (finds.contains(ID)) {
                        finds.remove(Integer.valueOf(ID));
                        players.put(targetUUID, finds);

                        sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Find with <yellow>ID " + ID + " <green>has been marked as <yellow>notFound <green>for player <yellow>" + target.getName()));
                        return true;
                    }
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Player<yellow> " + target.getName() + " <red>already has that find marked as <yellow>notFound"));
                }
            }
        }

        return true;
    }

    private void parseVisual(Visual visual, Player target) {
        //Message
        if (visual.message.enabled) {
            for (String message : visual.message.content) {
                target.sendMessage(MiniMessage.miniMessage().deserialize(message));
            }
        }

        //Sound
        if (visual.sound.enabled) {
            target.playSound(
                    target.getLocation(),
                    visual.sound.content,
                    1.0f,
                    1.0f
            );
        }

        //Particles
        if (visual.particle.enabled) {
            net.kali.collectives.utils.visuals.Particle particle = visual.particle;

            target.getWorld().spawnParticle(
                    particle.type,
                    particle.x,
                    particle.y,
                    particle.z,
                    particle.count,
                    particle.offsetX,
                    particle.offsetY,
                    particle.offsetZ,
                    particle.speed
            );
        }

        //Actionbar
        if (visual.actionBar.enabled) {
            target.sendActionBar(MiniMessage.miniMessage().deserialize(visual.actionBar.content));
        }

        //Title
        if (visual.title.enabled) {
            Title title = Title.title(MiniMessage.miniMessage().deserialize(visual.title.content), Component.empty());
            target.showTitle(title);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("mark");
            completions.add("reload");
            completions.add("save");
        }
        else if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        }
        else if (args.length == 3) {
            completions.add("found");
            completions.add("notFound");
        }
        else if (args.length == 4) {
            completions.add("{findID}");
        }

        return completions;
    }
}
