package com.tim.regions.handlers;

import com.tim.regions.cuboid.Cuboid;
import com.tim.regions.cuboid.CuboidManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveHandler implements Listener {
    private final CuboidManager cuboidManager;

    public PlayerMoveHandler(CuboidManager cuboidManager) {
        this.cuboidManager = cuboidManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        boolean isInCuboid = false;

        for (Cuboid cuboid : this.cuboidManager.getCuboids()) {
            if (cuboid.isIn(player)) {
                isInCuboid = true;
                player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("You are in the cuboid §6" + cuboid.getName()));
                break;
            }
        }

        if (!isInCuboid) {
            player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
        }

    }
}
