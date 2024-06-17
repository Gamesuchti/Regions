package com.tim.regions;

import com.tim.regions.region.Region;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleTask extends BukkitRunnable {
    private final Region region;
    private final JavaPlugin plugin;

    public ParticleTask(Region region, JavaPlugin plugin) {
        this.region = region;
        this.plugin = plugin;
    }

    public void run() {

        for (Block block : this.region.edgeBlocks()) {
            Location loc = block.getLocation().add(0.5, 0.5, 0.5);
            this.plugin.getServer().getWorld(this.region.getPoint1().getWorld().getName()).spawnParticle(Particle.HAPPY_VILLAGER, loc, 1);
        }

    }
}
