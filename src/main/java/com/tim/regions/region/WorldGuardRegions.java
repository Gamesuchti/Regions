package com.tim.regions.region;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;

public class WorldGuardRegions {

    public void getWorldGuardRegions(String worldName) {
        World world = BukkitAdapter.adapt(Bukkit.getWorld(worldName));
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
        if (regionManager == null) {
            System.out.println(Collections.emptyList());
        }
        System.out.println(new ArrayList<>(regionManager.getRegion("test").getPoints()));
        System.out.println(new ArrayList<>(Collections.singleton(regionManager.getRegion("test").getMinimumPoint())));
        System.out.println(new ArrayList<>(Collections.singleton(regionManager.getRegion("test").getMaximumPoint())));
    }
}
