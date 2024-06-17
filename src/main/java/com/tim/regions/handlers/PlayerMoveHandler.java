package com.tim.regions.handlers;

import com.tim.regions.region.Region;
import com.tim.regions.region.RegionManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveHandler implements Listener {
    private final RegionManager regionManager;

    public PlayerMoveHandler(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        boolean isInCuboid = false;

        for (Region region : this.regionManager.getCuboids()) {
            if (region.isIn(player)) {
                isInCuboid = true;
                player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("You are in the cuboid §6" + region.getName()));
                break;
            }
        }

        if (!isInCuboid) {
            player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
        }

    }
}
