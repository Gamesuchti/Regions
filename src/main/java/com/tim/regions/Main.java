package com.tim.regions;

import com.tim.regions.cuboid.Cuboid;
import com.tim.regions.cuboid.CuboidCommand;
import com.tim.regions.cuboid.CuboidManager;
import com.tim.regions.handlers.PlayerInteractHandler;
import com.tim.regions.handlers.PlayerMoveHandler;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Main extends JavaPlugin implements Listener {
    private final CuboidManager cuboidManager = new CuboidManager(this);
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
        this.getServer().getPluginManager().registerEvents(new PlayerInteractHandler(this.cuboidManager, this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerMoveHandler(this.cuboidManager), this);
        this.getCommand("cuboid").setExecutor(new CuboidCommand(this.cuboidManager));

        File file = new File(this.getDataFolder(), "cuboids.yml");
        if (file.exists()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            List<Map<?, ?>> cuboidMaps = yaml.getMapList("cuboids");

            Cuboid cuboid;
            for(Iterator var4 = cuboidMaps.iterator(); var4.hasNext(); this.particleTask = (new ParticleTask(cuboid, this)).runTaskTimer(this, 0L, 20L)) {
                Map<?, ?> cuboidMap = (Map)var4.next();
                Location point1 = Location.deserialize((Map)cuboidMap.get("point1"));
                Location point2 = Location.deserialize((Map)cuboidMap.get("point2"));
                String name = (String)cuboidMap.get("name");
                cuboid = new Cuboid(point1, point2, name);
                this.cuboidManager.addCuboid(cuboid);
            }
        }

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

        for (Cuboid cuboid : this.cuboidManager.getCuboids()) {
            Map<String, Object> cuboidMap = new HashMap();
            cuboidMap.put("name", cuboid.getName());
            cuboidMap.put("point1", cuboid.getPoint1().serialize());
            cuboidMap.put("point2", cuboid.getPoint2().serialize());
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
