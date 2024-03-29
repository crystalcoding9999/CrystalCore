package com.crystalcoding_.crystalcore.homes;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HomeManager {
    private HashMap<UUID, HashMap<String, Home>> homes;
    private HashMap<String, Integer> limitPermissions;

    public HomeManager(CrystalCore plugin) {
        // <editor-fold desc="loading homes">
        File homesFile = new File(plugin.getDataFolder(), "homes.yml");
        if (!homesFile.exists()) {
            try {
                homesFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        YamlConfiguration homesConfig = YamlConfiguration.loadConfiguration(homesFile);
        ConfigurationSection homesSection = homesConfig.getConfigurationSection("homes");

        if (homesSection == null) {
            homesSection = homesConfig.createSection("homes");
        }

        this.homes = new HashMap<>();
        for (String key : homesSection.getKeys(false)) {
            UUID owner = UUID.fromString(key);
            HashMap<String, Home> homes = new HashMap<>();

            for (String homeKey : homesSection.getStringList(key + ".homes")) {
                String path = key + "." + homeKey;

                double x = homesSection.getDouble(path + ".x");
                double y = homesSection.getDouble(path + ".y");
                double z = homesSection.getDouble(path + ".z");
                float yaw = (float) homesSection.getDouble(path + ".yaw");
                float pitch = (float) homesSection.getDouble(path + ".pitch");
                String world = homesSection.getString(path + ".world");

                homes.put(homeKey, new Home(x,y,z,yaw,pitch,world));
            }

            this.homes.put(owner, homes);
        }

        // </editor-fold>

        // <editor-fold desc="loading limit permissions">

        FileConfiguration config = plugin.getConfig();

        limitPermissions = new HashMap<>();
        ConfigurationSection limitsSection = config.getConfigurationSection("modules.homes.limits");

        if (limitsSection != null) {
            for (String key : limitsSection.getKeys(false)) {
                int limit = limitsSection.getInt(key);
                // Extract the numerical part from each key
                String numericalPart = key.substring(key.lastIndexOf(".") + 1);
                limitPermissions.put("crystalcore.homes.limit." + numericalPart, limit >= 0 ? limit : 0);
            }
        }

        // </editor-fold>
    }

    public void saveHomes() {
        File homesFile = new File(CrystalCore.getInstance().getDataFolder(), "homes.yml");
        if (!homesFile.exists()) {
            try {
                homesFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        YamlConfiguration homesConfig = YamlConfiguration.loadConfiguration(homesFile);
        ConfigurationSection homesSection = homesConfig.createSection("homes");

        for (Map.Entry<UUID, HashMap<String, Home>> entry : homes.entrySet()) {
            UUID owner = entry.getKey();
            HashMap<String, Home> ownerHomes = entry.getValue();

            List<String> homeKeys = new ArrayList<>();
            for (Map.Entry<String, Home> homeEntry : ownerHomes.entrySet()) {
                String homeKey = homeEntry.getKey();
                Home home = homeEntry.getValue();

                String path = owner.toString() + "." + homeKey;
                homesSection.set(path + ".x", home.getX());
                homesSection.set(path + ".y", home.getY());
                homesSection.set(path + ".z", home.getZ());
                homesSection.set(path + ".yaw", home.getYaw());
                homesSection.set(path + ".pitch", home.getPitch());
                homesSection.set(path + ".world", home.getWorld());

                homeKeys.add(homeKey);
            }
            homesSection.set(owner.toString() + ".homes", homeKeys);
        }

        // Save to file
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addHome(UUID owner, String name, Home home) {
        HashMap<String, Home> newHomes = this.homes.get(owner);

        newHomes.put(name,home);

        this.homes.put(owner, newHomes);

        saveHomes();
    }

    public void removeHome(UUID owner, String name) {
        HashMap<String, Home> newHomes = this.homes.get(owner);

        newHomes.remove(name);

        this.homes.put(owner, newHomes);
        saveHomes();
    }

    public HashMap<String, Home> getHomes(UUID uuid) {
        if (this.homes.get(uuid) == null) this.homes.put(uuid, new HashMap<>());
        return this.homes.get(uuid);
    }

    public int getLimit(Player player) {
        int limit = 0;
        for (Map.Entry<String, Integer> entry : limitPermissions.entrySet()) {
            if (player.hasPermission(entry.getKey())) {
                if (entry.getValue() > limit) {
                    limit = entry.getValue();
                }
            }
        }

        return limit;
    }
}
