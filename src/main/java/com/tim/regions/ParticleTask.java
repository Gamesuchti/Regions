package com.tim.regions;

import com.tim.regions.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleTask extends BukkitRunnable {
    private final Cuboid cuboid;
    private final JavaPlugin plugin;

    public ParticleTask(Cuboid cuboid, JavaPlugin plugin) {
        this.cuboid = cuboid;
        this.plugin = plugin;
    }

    public void run() {

        for (Block block : this.cuboid.edgeBlocks()) {
            Location loc = block.getLocation().add(0.5, 0.5, 0.5);
            this.plugin.getServer().getWorld(this.cuboid.getPoint1().getWorld().getName()).spawnParticle(Particle.HAPPY_VILLAGER, loc, 1);
        }

    }
}
