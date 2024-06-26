package com.crystalcoding_.crystalcore.listeners;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinListener implements Listener {
    private final JavaPlugin plugin;

    public JoinListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        CrystalCore.getInstance().rankManager.playerJoined(p);

        if (p.hasPermission("crystalcore.vanish")) {
            CrystalCore.getInstance().vanishManager.setVanished(p, true);
            e.setJoinMessage(null);
            plugin.getLogger().info(Core.color(CrystalCore.getInstance().messageManager.getSilentJoinMessage(p)));
            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.hasPermission("crystalcore.vanish")) {
                    Core.message(CrystalCore.getInstance().messageManager.getSilentJoinMessage(p), op);
                } else {
                    op.hidePlayer(plugin, p);
                }
            }
        } else {
            e.setJoinMessage(CrystalCore.getInstance().messageManager.getJoinMessage(p));
            CrystalCore.getInstance().vanishManager.refreshVanishedPlayers(p);
        }

        CrystalCore.getInstance().nameManager.updateDisplayName(p);
        CrystalCore.getInstance().nameManager.updatePlayerListName(p);
    }
}
