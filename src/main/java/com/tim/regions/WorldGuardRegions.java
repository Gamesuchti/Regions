package com.tim.regions;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;

import java.util.Collections;

public class WorldGuardRegions {

    public void getWorldGuardRegions(String worldName) {
        World world = BukkitAdapter.adapt(Bukkit.getWorld(worldName));
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
        if (regionManager == null) {
            System.out.println(Collections.emptyList());
        }
        System.out.println(regionManager.getRegion("test").getPoints());
        System.out.println(regionManager.getRegion("test").getMinimumPoint());
        regionManager.getRegion("test").setFlag(Flags.BLOCK_BREAK, StateFlag.State.DENY);
        regionManager.getRegion("test").setFlag(Flags.GREET_TITLE, "§2§l " + regionManager.getRegion("test").getId());
        regionManager.getRegion("test").setFlag(Flags.FAREWELL_MESSAGE, "§3Goodbye!");
        regionManager.getRegion("test").setFlag(Flags.DENY_MESSAGE, "§cDu darfst das nicht tun!");
    }
}
