package com.crystalcoding_.crystalcore.listeners;

import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinListener implements Listener {
    private JavaPlugin plugin;

    public JoinListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("crystalcore.vanish")) {
            CrystalCore.getInstance().vanishManager.setVanished(p, true);
            e.setJoinMessage(null);
            plugin.getLogger().info(CrystalCore.getInstance().messageManager.getSilentJoinMessage(p));
            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.hasPermission("crystalcore.vanish")) {
                    op.sendMessage(CrystalCore.getInstance().messageManager.getSilentJoinMessage(p));
                } else {
                    op.hidePlayer(plugin, p);
                }
            }
        } else {
            e.setJoinMessage(CrystalCore.getInstance().messageManager.getJoinMessage(p));
            CrystalCore.getInstance().vanishManager.refreshVanishedPlayers(p);
        }

        CrystalCore.getInstance().rankManager.playerJoined(p);

        CrystalCore.getInstance().nameManager.updateDisplayName(p);
        CrystalCore.getInstance().nameManager.updatePlayerListName(p);
    }
}
