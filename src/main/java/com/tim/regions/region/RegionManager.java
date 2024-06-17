package com.tim.regions.region;

import com.tim.regions.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegionManager {
    private final Main main;
    private final List<Region> regions = new ArrayList();
    private final Map<UUID, Location[]> playerCuboids = new HashMap();

    public List<Region> getCuboids() {
        return this.regions;
    }

    public void addCuboid(Region region) {
        this.regions.add(region);
    }

    public void removeCuboid(Region region) {
        this.regions.remove(region);
    }

    public RegionManager(Main main) {
        this.main = main;
    }

    public void setPoint(Player player, Location point, int index) {
        UUID playerId = player.getUniqueId();
        Location[] points = this.playerCuboids.get(playerId);
        if (points == null) {
            points = new Location[2];
        }

        points[index] = point;
        this.playerCuboids.put(playerId, points);
    }

    public Location[] getPoints(Player player) {
        return this.playerCuboids.get(player.getUniqueId());
    }

    public void resetPoints(Player player) {
        this.setPoint(player, null, 0);
        this.setPoint(player, null, 1);
        if (this.main.getParticleTask() != null) {
            this.main.getParticleTask().cancel();
            this.main.setParticleTask(null);
        }

    }
}
