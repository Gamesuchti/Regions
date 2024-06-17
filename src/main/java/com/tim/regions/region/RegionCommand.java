package com.tim.regions.region;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionCommand implements CommandExecutor {
    private final RegionManager regionManager;

    public RegionCommand(RegionManager regionManager) {
        this.regionManager = regionManager;
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
                            if (this.regionManager.getPoints(player)[0] != null && this.regionManager.getPoints(player)[1] != null) {
                                Region region = new Region(this.regionManager.getPoints(player)[0], this.regionManager.getPoints(player)[1], cuboidName);
                                region.setName(cuboidName);
                                this.regionManager.addCuboid(region);
                                player.sendMessage("Cuboid " + ChatColor.GREEN + cuboidName + ChatColor.WHITE + " saved!");
                                return true;
                            } else {
                                player.sendMessage("You must set two points before saving the cuboid.");
                                return false;
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reset")) {
                        this.regionManager.resetPoints(player);
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
