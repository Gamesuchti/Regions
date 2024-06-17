package com.tim.regions.handlers;

import com.tim.regions.Main;
import com.tim.regions.ParticleTask;
import com.tim.regions.region.Region;
import com.tim.regions.region.RegionManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitTask;

public class PlayerInteractHandler implements Listener {
    private final RegionManager regionManager;
    private final Main main;
    private BukkitTask particleTask;

    public PlayerInteractHandler(RegionManager regionManager, Main main) {
        this.regionManager = regionManager;
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType() == Material.WOODEN_SWORD) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock != null) {
                    Location blockLocation = clickedBlock.getLocation();
                    Location[] points = this.regionManager.getPoints(player);
                    if (points == null) {
                        points = new Location[2];
                    }

                    if (points[0] != null && points[1] != null) {
                        player.sendMessage("Two points already exist. Please execute the command to remove the existing cuboid before setting a new one.");
                        return;
                    }

                    boolean settingNewPoint = points[0] == null || !points[0].equals(blockLocation);
                    if (!settingNewPoint && points[0] != null && points[1] != null) {
                        this.regionManager.setPoint(player, null, 0);
                        this.regionManager.setPoint(player, null, 1);
                        player.sendMessage("Previous cuboid points reset!");
                        if (this.particleTask != null) {
                            this.particleTask.cancel();
                            this.particleTask = null;
                        }
                    }

                    if (points[0] == null) {
                        this.regionManager.setPoint(player, blockLocation, 0);
                        player.sendMessage("Point 1 set!");
                    } else if (!points[0].equals(blockLocation)) {
                        this.regionManager.setPoint(player, blockLocation, 1);
                        player.sendMessage("Point 2 set!");
                        Region region = new Region(points[0], blockLocation, null);
                        this.particleTask = (new ParticleTask(region, this.main)).runTaskTimer(this.main, 0L, 20L);
                    }
                }
            }

        }
    }
}
