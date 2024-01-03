package com.crystalcoding_.crystalcore.listeners;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.staffchat.StaffchatManager;
import com.crystalcoding_.crystalcore.vanish.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private StaffchatManager staffchatManager;
    private VanishManager vanishManager;

    public ChatListener(CrystalCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.staffchatManager = plugin.staffchatManager;
        this.vanishManager = plugin.vanishManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (staffchatManager.inStaffChat(p) || (vanishManager.isVanished(p) && !e.getMessage().startsWith("!"))) {
            e.setCancelled(true);
            staffchatManager.sendStaffMessage(p, e.getMessage());
        }
    }
}
