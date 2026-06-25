package com.bloodbound.resourcepack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PackCommand implements CommandExecutor {

    private final BloodboundResourcePack plugin;

    public PackCommand(BloodboundResourcePack plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("bloodboundpack.admin")) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUsage: /bloodboundpack <reload|status|send <player>>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                plugin.reloadConfig();
                plugin.loadPacks();
                sender.sendMessage("§aBloodboundResourcePack config reloaded. Loaded " + plugin.getPacks().size() + " pack(s).");
            }
            case "status" -> {
                sender.sendMessage("§4§lBloodbound Resource Packs");
                if (plugin.getPacks().isEmpty()) {
                    sender.sendMessage("§cNo packs loaded. Check config.yml");
                } else {
                    for (int i = 0; i < plugin.getPacks().size(); i++) {
                        BloodboundResourcePack.ResourcePackEntry pack = plugin.getPacks().get(i);
                        sender.sendMessage("§7[" + (i + 1) + "] §f" + pack.url());
                        sender.sendMessage("   §7Required: " + (pack.required() ? "§aYes" : "§cNo"));
                    }
                }
            }
            case "send" -> {
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /bloodboundpack send <player>");
                    return true;
                }
                Player target = plugin.getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage("§cPlayer not found: " + args[1]);
                    return true;
                }
                for (BloodboundResourcePack.ResourcePackEntry pack : plugin.getPacks()) {
                    target.setResourcePack(pack.url(), pack.hash(), pack.prompt(), pack.required());
                }
                sender.sendMessage("§aSent resource pack(s) to " + target.getName());
            }
            default -> sender.sendMessage("§cUnknown subcommand. Use: reload, status, send");
        }

        return true;
    }
}
