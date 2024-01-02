package com.crystalcoding_.crystalcore.listeners;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class QuitListener implements Listener {
    private JavaPlugin plugin;

    public QuitListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (CrystalCore.getInstance().vanishManager.isVanished(p)) {
            e.setQuitMessage(null);
            plugin.getLogger().info(CrystalCore.getInstance().messageManager.getSilentLeaveMessage(p));
            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.hasPermission("crystalcore.vanish")) {
                    Core.message(CrystalCore.getInstance().messageManager.getSilentLeaveMessage(p), op);
                }
            }
        } else {
            e.setQuitMessage(CrystalCore.getInstance().messageManager.getLeaveMessage(p));
        }

        CrystalCore.getInstance().rankManager.playerLeft(p);
        CrystalCore.getInstance().vanishManager.playerLeft(p);
    }
}
