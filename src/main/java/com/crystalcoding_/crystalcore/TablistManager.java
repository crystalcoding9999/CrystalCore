package com.crystalcoding_.crystalcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TablistManager {

    private static final int REFRESH_TICKS = 20; // 1 second
    private final CrystalCore plugin;

    public TablistManager(CrystalCore plugin) {
        this.plugin = plugin;
        createTablistConfig();
    }

    public void enableTablist() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateTablist, 0, REFRESH_TICKS);
    }

    private void updateTablist() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(getTablistConfigFile());

        List<String> headerLines = config.getStringList("header-footer.header");
        List<String> footerLines = config.getStringList("header-footer.footer");

        StringBuilder headerBuilder = new StringBuilder();
        for (String line : headerLines) {
            if (!line.isEmpty()) {
                line = line.replace("{onlineplayers}", String.valueOf(Bukkit.getOnlinePlayers().size()));
                headerBuilder.append(ChatColor.translateAlternateColorCodes('&', line)).append("\n");
            } else {
                headerBuilder.append(Core.color("&r \n"));
            }
        }
        String header = headerBuilder.toString().trim();

        StringBuilder footerBuilder = new StringBuilder();
        for (String line : footerLines) {
            if (!line.isEmpty()) {
                line = line.replace("{onlineplayers}", String.valueOf(Bukkit.getOnlinePlayers().size()));
                footerBuilder.append(ChatColor.translateAlternateColorCodes('&', line)).append("\n");
            } else {
                footerBuilder.append(Core.color("&r \n"));
            }
        }
        String footer = footerBuilder.toString().trim();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeader(header);
            player.setPlayerListFooter(footer);
        }
    }

    private void createTablistConfig() {
        File configFile = getTablistConfigFile();
        if (!configFile.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);


            config.set("header-footer.header", Arrays.asList("", "&3&lServer name", "", "Online Players: {onlineplayers}", ""));
            config.set("header-footer.footer", Arrays.asList("", "&7Visit our webpage", ""));

            try {
                config.save(configFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private File getTablistConfigFile() {
        return new File(plugin.getDataFolder(), "tablist.yml");
    }
}