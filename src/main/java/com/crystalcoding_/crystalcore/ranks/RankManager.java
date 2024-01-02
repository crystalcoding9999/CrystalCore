package com.crystalcoding_.crystalcore.ranks;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.messages.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.logging.Level;

public class RankManager {
    private ArrayList<Rank> ranks;
    private Map<String, Rank> rankMap;
    private HashMap<UUID, String> playerRanks;
    private HashMap<UUID, String> temporaryRanks;
    private HashMap<Player, PermissionAttachment> playerAttachments;
    private Rank defaultRank;

    private JavaPlugin plugin;
    private File ranksFile;

    public RankManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.ranksFile = new File(plugin.getDataFolder(), "ranks.yml");
        this.temporaryRanks = new HashMap<>();
        this.playerAttachments = new HashMap<>();

        this.rankMap = new HashMap<>();
        loadRanks();
        loadPlayerRanks();

        loadRanks();
        loadPlayerRanks();
    }

    private void loadRanks() {
        FileConfiguration ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);

        if (!ranksFile.exists()) {
            createDefaultRanks();
            saveRanks(); // Call saveRanks() after creating default ranks
            return;
        }

        String defaultRankName = ranksConfig.getString("defaultRank");
        ranks = new ArrayList<>();
        ConfigurationSection ranksSection = ranksConfig.getConfigurationSection("ranks");
        if (ranksSection != null) {
            for (String rankName : ranksSection.getKeys(false)) {
                ConfigurationSection rankSection = ranksSection.getConfigurationSection(rankName);
                if (rankSection != null) {
                    String name = rankSection.getString("name");
                    String prefix = rankSection.getString("prefix");
                    List<String> permissions = rankSection.getStringList("permissions");
                    List<String> parents = rankSection.getStringList("parents");

                    Rank rank = new Rank(name, prefix, permissions, parents);
                    ranks.add(rank);

                    // Check for default rank
                    if (name.equalsIgnoreCase(ranksConfig.getString("defaultRank", "default"))) {
                        defaultRank = rank;
                    }
                }
            }
        }

        // If default rank is not found, set the first rank as default
        if (defaultRank == null && !ranks.isEmpty()) {
            defaultRank = ranks.get(0);
        }
    }

    private void createDefaultRanks() {
        if (ranks == null) {
            ranks = new ArrayList<>();
        }
        FileConfiguration ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);

        ranksConfig.createSection("ranks");
        defaultRank = new Rank("default", "[&7Default&r]");
        ranks.add(defaultRank);
        rankMap.put("default", defaultRank); // Add default rank to rankMap

        ArrayList<String> vipPermissions = new ArrayList<>();
        vipPermissions.add("crystalcore.nick");
        ArrayList<String> vipParents = new ArrayList<>();
        vipParents.add("default");
        Rank vipRank = new Rank("vip", "[&aVip&r]", vipPermissions, vipParents);
        ranks.add(vipRank);
        rankMap.put("vip", vipRank); // Add vip rank to rankMap

        ArrayList<String> staffPermissions = new ArrayList<>();
        staffPermissions.add("crystalcore.vanish");
        ArrayList<String> moderatorParents = new ArrayList<>();
        moderatorParents.add("vip");
        Rank moderatorRank = new Rank("moderator", "[&2Moderator&r]", staffPermissions, moderatorParents);
        ranks.add(moderatorRank);
        rankMap.put("moderator", moderatorRank); // Add moderator rank to rankMap

        ArrayList<String> adminParents = new ArrayList<>();
        adminParents.add("moderator");
        Rank adminRank = new Rank("admin", "[&cAdmin&r]", staffPermissions, adminParents);
        ranks.add(adminRank);
        rankMap.put("admin", adminRank); // Add admin rank to rankMap

        ArrayList<String> ownerParents = new ArrayList<>();
        ownerParents.add("admin");
        Rank ownerRank = new Rank("owner", "[&4Owner&r]", staffPermissions, ownerParents);
        ranks.add(ownerRank);
        rankMap.put("owner", ownerRank); // Add owner rank to rankMap
    }


    private void saveRanks() {
        FileConfiguration ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);

        ranksConfig.set("defaultRank", defaultRank.getName());

        ConfigurationSection ranksSection = ranksConfig.createSection("ranks");
        for (Rank rank : ranks) {
            String rankPath = "ranks." + rank.getName().toLowerCase();
            ConfigurationSection rankData = ranksSection.createSection(rank.getName().toLowerCase());

            rankData.set("name", rank.getName());
            rankData.set("prefix", rank.getPrefix());
            rankData.set("permissions", new ArrayList<>(rank.getPermissions()));

            if (!rank.getParents().isEmpty()) {
                rankData.set("parents", new ArrayList<>(rank.getParents()));
            }
        }

        try {
            ranksConfig.save(ranksFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Error saving ranks to file: " + e.getMessage());
        }
    }


    private void loadPlayerRanks() {
        FileConfiguration ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);
        playerRanks = new HashMap<>();

        if (ranksConfig.contains("playerRanks")) {
            ConfigurationSection playerRanksSection = ranksConfig.getConfigurationSection("playerRanks");

            for (String key : playerRanksSection.getKeys(false)) {
                UUID uuid = UUID.fromString(key);
                Rank rank = getRank(playerRanksSection.getString(key));
                if (rank == null) rank = defaultRank;
                playerRanks.put(uuid, rank.getName());
            }
        } else {
            ranksConfig.createSection("playerRanks");
        }
    }

    public void savePlayerRanks() {
        FileConfiguration ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);
        ConfigurationSection playerRanksSection = ranksConfig.createSection("playerRanks");

        for (UUID uuid : playerRanks.keySet()) {
            playerRanksSection.set(uuid.toString(), playerRanks.get(uuid));
        }

        try {
            ranksConfig.save(ranksFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Error saving player ranks to file: " + e.getMessage());
        }
    }

    public void playerJoined(Player p) {
        UUID u = p.getUniqueId();
        if (!playerRanks.containsKey(u)) {
            playerRanks.put(u, defaultRank.getName());
        }

        updatePermissions(p);
    }

    public void playerLeft(Player p) {
        temporaryRanks.remove(p.getUniqueId());
    }

    public synchronized Rank getRank(String name) {
        for (Rank rank : ranks) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }

        return rankMap.getOrDefault(name.toLowerCase(), null);
    }

    public Rank getPlayerRank(Player p) {
        return getPlayerRank(p.getUniqueId());
    }

    public Rank getPlayerRank(UUID uuid) {
        if (uuid != null) {
            if (temporaryRanks.containsKey(uuid)) {
                return getRank(temporaryRanks.get(uuid));
            }

            Rank playerRank = getRank(playerRanks.get(uuid));
            if (playerRank != null) {
                return playerRank;
            } else {
                setRank(uuid, defaultRank, false);
                return defaultRank;
            }
        }

        return null; // Or return null if no default rank is set
    }

    public void setRank(Player p, Rank r) {
        setRank(p.getUniqueId(), r);
    }

    public void setRank(UUID u, Rank r) {
        setRank(u, r, true);
    }

    public void setRank(UUID u, Rank r, boolean t) {
        playerRanks.put(u, r.getName());
        Player p = Bukkit.getPlayer(u);
        if (p != null) {
            CrystalCore.getInstance().nameManager.updateDisplayName(p);
            CrystalCore.getInstance().nameManager.updatePlayerListName(p);
            updatePermissions(p);
            if (t) Core.message(CrystalCore.getInstance().messageManager.getRankChangedMessage(r), p);
            savePlayerRanks();
        }
    }

    public void setTemporaryRank(Player p, Rank r) {
        setTemporaryRank(p.getUniqueId(), r);
    }

    public void setTemporaryRank(UUID u, Rank r) {
        temporaryRanks.put(u, r.getName());
        Player p = Bukkit.getPlayer(u);
        if (p != null) {
            CrystalCore.getInstance().nameManager.updateDisplayName(Bukkit.getPlayer(u));
            CrystalCore.getInstance().nameManager.updatePlayerListName(Bukkit.getPlayer(u));
            updatePermissions(p);
            Core.message(CrystalCore.getInstance().messageManager.getTempRankChangedMessage(r), p);
        }
    }

    public synchronized void addRank(Rank r) {
        if (rankMap.containsKey(r.getName())) {
            throw new IllegalArgumentException("Rank already exists!");
        }

        ranks.add(r);
        rankMap.put(r.getName().toLowerCase(), r); // Store rank in the rankMap for quicker lookup
        saveRanks();
    }

    public synchronized void removeRank(Rank r) {
        if (defaultRank.getName().equalsIgnoreCase(r.getName())) {
            throw new IllegalArgumentException("Cannot delete the default rank!");
        }
        removeRank(r.getName());
        saveRanks();
    }

    private synchronized void removeRank(String rankName) {
        if (defaultRank.getName().equalsIgnoreCase(rankName)) {
            throw new IllegalArgumentException("Cannot delete the default rank!");
        }
        Rank removedRank = null;
        for (Rank rank : ranks) {
            if (rank.getName().equalsIgnoreCase(rankName)) {
                removedRank = rank;
                ranks.remove(rank);
                break;
            }
        }

        if (removedRank != null) {
            rankMap.remove(removedRank.getName().toLowerCase()); // Remove rank from the rankMap
        }

        for (UUID uuid : playerRanks.keySet()) {
            if (playerRanks.get(uuid).equalsIgnoreCase(rankName)) {
                setRank(uuid, defaultRank);
                Player p = Bukkit.getPlayer(uuid);
                if (p != null) {
                    Core.message(CrystalCore.getInstance().messageManager.getRankDeletedMessage(), p);
                }
            }
        }
    }

    private void updatePermissions(Player p) {
        if (!playerAttachments.containsKey(p)) {
            PermissionAttachment attachment = p.addAttachment(CrystalCore.getInstance());
            playerAttachments.put(p, attachment);
        }

        PermissionAttachment attachment = playerAttachments.get(p);

        if (!attachment.getPermissions().isEmpty()) {
            for (String permission : attachment.getPermissions().keySet()) {
                attachment.unsetPermission(permission);
            }
        }

        for (String permission : getPermissions(getPlayerRank(p))) {
            attachment.setPermission(permission, !(permission.startsWith("!")));
            if (permission.equals("crystalcore.vanish")) {
                CrystalCore.getInstance().vanishManager.refreshVanishedPlayers(p);
            }
        }

        playerAttachments.put(p, attachment);
    }

    private ArrayList<String> getPermissions(Rank r) {
        ArrayList<String> permissions = (ArrayList<String>) r.getPermissions();

        if (!r.getPermissions().isEmpty()) {
            for (String p : r.getPermissions()) {
                Rank parent = getRank(p);
                if (parent != null) {
                    permissions.addAll(getPermissions(parent));
                }
            }
        }

        return permissions;
    }

    public void addPermission(Permission permission, Rank rank) {
        addPermission(permission.getName(), rank);
    }

    public void addPermission(String permission, Rank rank) {
        for (Rank r : ranks) {
            if (r.getName().equalsIgnoreCase(rank.getName())) {
                r.getPermissions().add(permission);
                break;
            }
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePermissions(p);
        }
    }

    public void addPermission(String permission, String rank) {
        Rank r = getRank(rank);
        if (r != null) {
            addPermission(permission, r);
        }
    }

    public void removePermission(Permission permission, Rank rank) {
        removePermission(permission.getName(), rank);
    }

    public void removePermission(String permission, Rank rank) {
        for (Rank r : ranks) {
            if (r.getName().equalsIgnoreCase(rank.getName())) {
                r.getPermissions().add(permission);
                break;
            }
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePermissions(p);
        }
    }

    public void removePermission(String permission, String rank) {
        Rank r = getRank(rank);
        if (r != null) {
            removePermission(permission, r);
        }
    }

    public void addParentRank(String rankName, String parentName) {
        Rank rank = getRank(rankName);
        Rank parent = getRank(parentName);

        if (rank != null && parent != null) {
            if (!rank.getParents().contains(parentName)) {
                rank.getParents().add(parentName);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    updatePermissions(p);
                }
            }
        }
    }

    public void removeParentRank(String rankName, String parentName) {
        Rank rank = getRank(rankName);

        if (rank != null) {
            rank.getParents().remove(parentName);
            for (Player p : Bukkit.getOnlinePlayers()) {
                updatePermissions(p);
            }
        }
    }

    public void setPrefix(String rankName, String prefix) {
        // Update the prefix for the specified rank in your ranks list or map
        Rank rankToUpdate = getRank(rankName);
        if (rankToUpdate == null) {
            return;
        }

        rankToUpdate.setPrefix(Core.color(prefix));

        // Iterate through the playerRanks map
        for (Map.Entry<UUID, String> entry : playerRanks.entrySet()) {
            UUID uuid = entry.getKey();
            String playerRankName = entry.getValue();

            if (playerRankName.equalsIgnoreCase(rankName)) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    // Update the display name for the online player
                    if (playerRankName.equalsIgnoreCase(rankName)) {
                        CrystalCore.getInstance().nameManager.updatePlayerListName(player);
                        CrystalCore.getInstance().nameManager.updateDisplayName(player);
                    }
                }
            }
        }
    }

    public void setDefaultRank(String rank) {
        if (getRank(rank) == null) return;
        defaultRank = getRank(rank);
    }

    public  void setDefaultRank(Rank rank) {
        defaultRank = rank;
    }

    public boolean isDefault(String rankName) {
        return defaultRank.getName().equalsIgnoreCase(rankName);
    }

    public boolean isDefault(Rank rank) {
        return defaultRank.getName().equalsIgnoreCase(rank.getName());
    }

    public ArrayList<Rank> getRanks() {
        return ranks;
    }
}
