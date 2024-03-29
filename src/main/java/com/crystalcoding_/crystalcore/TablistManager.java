package com.crystalcoding_.crystalcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TablistManager {

    private static final int REFRESH_TICKS = 20; // 1 second
    private final CrystalCore plugin;

    String emptyLine = Core.color("&r \n");

    YamlConfiguration tablistConfig;

    public TablistManager(CrystalCore plugin) {
        this.plugin = plugin;
        createTablistConfig();
    }

    public void enableTablist() {
        reloadTablistConfig();
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateTablist, 0, REFRESH_TICKS);
    }

    private void updateTablist() {
        // Reload tablist configuration
        reloadTablistConfig();

        // Get the current number of online players
        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        // Get the tablist header and footer from the tablist config
        List<String> headerList = tablistConfig.getStringList("header");
        List<String> footerList = tablistConfig.getStringList("footer");

        // Combine header lines into a single string
        String header = headerList.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.joining("\n"));

        // Combine footer lines into a single string
        String footer = footerList.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.joining("\n"));

        // Replace the placeholder {onlineplayers} with the current number of online players
        header = header.replace("{onlineplayers}", String.valueOf(onlinePlayers));
        footer = footer.replace("{onlineplayers}", String.valueOf(onlinePlayers));

        // Set the tablist header and footer for all players
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeader(header);
            player.setPlayerListFooter(footer);
        }
    }

    private void reloadTablistConfig() {
        // Reload tablist configuration
        File tablistConfigFile = new File(plugin.getDataFolder(), "tablist.yml");
        tablistConfig = YamlConfiguration.loadConfiguration(tablistConfigFile);
    }

    private void saveDefaultTablistConfig() {
        if (!new File(plugin.getDataFolder(), "tablist.yml").exists()) {
            plugin.saveResource("tablist.yml", false);
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