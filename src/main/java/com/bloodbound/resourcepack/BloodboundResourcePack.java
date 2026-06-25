package com.bloodbound.resourcepack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BloodboundResourcePack extends JavaPlugin {

    private static BloodboundResourcePack instance;
    private final List<ResourcePackEntry> packs = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig();
        loadPacks();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getCommand("bloodboundpack").setExecutor(new PackCommand(this));

        getLogger().info("BloodboundResourcePack enabled! Loaded " + packs.size() + " pack(s).");
    }

    @Override
    public void onDisable() {
        getLogger().info("BloodboundResourcePack disabled.");
    }

    public void loadPacks() {
        packs.clear();
        ConfigurationSection rpSection = getConfig().getConfigurationSection("resource-packs");
        if (rpSection == null) return;

        for (String key : rpSection.getKeys(false)) {
            ConfigurationSection packSec = rpSection.getConfigurationSection(key);
            if (packSec == null || !packSec.getBoolean("enabled", true)) continue;

            String url = packSec.getString("url", "");
            String hash = packSec.getString("hash", "");
            String prompt = packSec.getString("prompt", "&cAccept to see custom textures!");
            boolean required = packSec.getBoolean("required", true);

            if (url.isEmpty() || url.contains("your-url-here")) {
                getLogger().warning("Pack '" + key + "' has no valid URL. Skipping.");
                continue;
            }

            byte[] hashBytes = hexStringToByteArray(hash);
            Component promptComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(prompt);

            packs.add(new ResourcePackEntry(url, hashBytes, promptComponent, required));
            getLogger().info("Loaded pack: " + key + " -> " + url);
        }
    }

    public List<ResourcePackEntry> getPacks() {
        return packs;
    }

    public Component getKickMessage() {
        String msg = getConfig().getString("settings.kick-message", "&cCustom textures required!");
        return LegacyComponentSerializer.legacyAmpersand().deserialize(msg);
    }

    public Component getFailedDownloadMessage() {
        String msg = getConfig().getString("settings.kick-on-failed-download-message", "&cDownload failed!");
        return LegacyComponentSerializer.legacyAmpersand().deserialize(msg);
    }

    public boolean shouldKickOnDecline() {
        return getConfig().getBoolean("settings.kick-on-decline", true);
    }

    public boolean shouldKickOnFailedDownload() {
        return getConfig().getBoolean("settings.kick-on-failed-download", true);
    }

    public static BloodboundResourcePack getInstance() {
        return instance;
    }

    private static byte[] hexStringToByteArray(String s) {
        if (s == null || s.isEmpty()) return new byte[0];
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public record ResourcePackEntry(String url, byte[] hash, Component prompt, boolean required) {}
}
