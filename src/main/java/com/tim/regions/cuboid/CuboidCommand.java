package com.tim.regions.cuboid;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CuboidCommand implements CommandExecutor {
    private final CuboidManager cuboidManager;

    public CuboidCommand(CuboidManager cuboidManager) {
        this.cuboidManager = cuboidManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cuboid")) {
            Player player;
            if (sender instanceof Player) {
                player = (Player)sender;
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("save")) {
                        if (args.length < 2) {
                            player.sendMessage("Please specify a name for the cuboid.");
                            return false;
                        } else {
                            String cuboidName = args[1];
                            if (this.cuboidManager.getPoints(player)[0] != null && this.cuboidManager.getPoints(player)[1] != null) {
                                Cuboid cuboid = new Cuboid(this.cuboidManager.getPoints(player)[0], this.cuboidManager.getPoints(player)[1], cuboidName);
                                cuboid.setName(cuboidName);
                                this.cuboidManager.addCuboid(cuboid);
                                player.sendMessage("Cuboid " + ChatColor.GREEN + cuboidName + ChatColor.WHITE + " saved!");
                                return true;
                            } else {
                                player.sendMessage("You must set two points before saving the cuboid.");
                                return false;
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reset")) {
                        this.cuboidManager.resetPoints(player);
                        player.sendMessage("Cuboid removed!");
                        return true;
                    } else {
                        player.sendMessage("Please specify an argument: reset or save.");
                        return false;
                    }
                } else {
                    sender.sendMessage("This command can only be run by a player.");
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
