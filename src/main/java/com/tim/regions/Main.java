package com.tim.regions;

import com.tim.regions.region.Region;
import com.tim.regions.region.RegionCommand;
import com.tim.regions.region.RegionManager;
import com.tim.regions.handlers.PlayerInteractHandler;
import com.tim.regions.handlers.PlayerMoveHandler;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tim.regions.region.WorldGuardRegions;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Main extends JavaPlugin implements Listener {
    private final RegionManager regionManager = new RegionManager(this);
    private final WorldGuardRegions worldGuardRegions = new WorldGuardRegions();
    private BukkitTask particleTask;

    public Main() {
    }

    public BukkitTask getParticleTask() {
        return this.particleTask;
    }

    public void setParticleTask(BukkitTask particleTask) {
        this.particleTask = particleTask;
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractHandler(this.regionManager, this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerMoveHandler(this.regionManager), this);
        this.getCommand("cuboid").setExecutor(new RegionCommand(this.regionManager));

        File file = new File(this.getDataFolder(), "cuboids.yml");
        if (file.exists()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            List<Map<?, ?>> cuboidMaps = yaml.getMapList("cuboids");

            Region region;
            for(Iterator var4 = cuboidMaps.iterator(); var4.hasNext(); this.particleTask = (new ParticleTask(region, this)).runTaskTimer(this, 0L, 20L)) {
                Map<?, ?> cuboidMap = (Map)var4.next();
                Location point1 = Location.deserialize((Map)cuboidMap.get("point1"));
                Location point2 = Location.deserialize((Map)cuboidMap.get("point2"));
                String name = (String)cuboidMap.get("name");
                region = new Region(point1, point2, name);
                this.regionManager.addCuboid(region);
            }
        }

        worldGuardRegions.getWorldGuardRegions("world");

    }

    public void onDisable() {
        if (this.particleTask != null) {
            this.particleTask.cancel();
        }

        File dataFolder = this.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File file = new File(dataFolder, "cuboids.yml");
        YamlConfiguration yaml = new YamlConfiguration();
        List<Map<String, Object>> cuboidMaps = new ArrayList();

        for (Region region : this.regionManager.getCuboids()) {
            Map<String, Object> cuboidMap = new HashMap();
            cuboidMap.put("name", region.getName());
            cuboidMap.put("point1", region.getPoint1().serialize());
            cuboidMap.put("point2", region.getPoint2().serialize());
            cuboidMaps.add(cuboidMap);
        }

        yaml.set("cuboids", cuboidMaps);

        try {
            yaml.save(file);
            System.out.println("Cuboids saved successfully!");
        } catch (IOException var7) {
            System.out.println("An error occurred while saving the cuboids.");
            var7.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        this.getServer().getWorld("world").setTime(6000L);
    }
}
