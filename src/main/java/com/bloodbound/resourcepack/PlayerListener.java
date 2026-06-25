package com.bloodbound.resourcepack;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class PlayerListener implements Listener {

    private final BloodboundResourcePack plugin;

    public PlayerListener(BloodboundResourcePack plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Send all configured resource packs
        for (BloodboundResourcePack.ResourcePackEntry pack : plugin.getPacks()) {
            player.setResourcePack(pack.url(), pack.hash(), pack.prompt(), pack.required());
        }
    }

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        PlayerResourcePackStatusEvent.Status status = event.getStatus();

        switch (status) {
            case DECLINED -> {
                if (plugin.shouldKickOnDecline()) {
                    player.kick(plugin.getKickMessage());
                    plugin.getLogger().info(player.getName() + " declined the resource pack and was kicked.");
                }
            }
            case FAILED_DOWNLOAD -> {
                if (plugin.shouldKickOnFailedDownload()) {
                    player.kick(plugin.getFailedDownloadMessage());
                    plugin.getLogger().info(player.getName() + " failed to download the resource pack and was kicked.");
                }
            }
            case ACCEPTED -> {
                plugin.getLogger().info(player.getName() + " accepted the resource pack.");
            }
            case SUCCESSFULLY_LOADED -> {
                plugin.getLogger().info(player.getName() + " successfully loaded the resource pack.");
            }
            default -> {
                // Do nothing for other statuses
            }
        }
    }
}
