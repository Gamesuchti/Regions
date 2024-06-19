package com.tim.regions;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;

import java.util.Collections;

public class WorldGuardRegions {

    public void getWorldGuardRegions(String worldName) {
        World world = BukkitAdapter.adapt(Bukkit.getWorld(worldName));
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
        if (regionManager == null) {
            System.out.println(Collections.emptyList());
        }

        for (ProtectedRegion region : regionManager.getRegions().values()) {
            region.setFlag(Flags.BLOCK_BREAK, StateFlag.State.DENY);
            region.setFlag(Flags.GREET_TITLE, "§2§l " + region.getId());
            region.setFlag(Flags.FAREWELL_TITLE, "");
            region.setFlag(Flags.DENY_MESSAGE, "§cDu darfst das nicht tun!");
        }
    }
}
