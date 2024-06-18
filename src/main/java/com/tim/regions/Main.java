package com.tim.regions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    private final WorldGuardRegions worldGuardRegions = new WorldGuardRegions();

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        worldGuardRegions.getWorldGuardRegions("world");

    }

    public void onDisable() {

    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        this.getServer().getWorld("world").setTime(6000L);
    }
}
