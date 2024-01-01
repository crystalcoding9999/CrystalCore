package com.crystalcoding_.crystalcore.listeners;

import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChangeGamemodeListener implements Listener {
    private JavaPlugin plugin;

    public ChangeGamemodeListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();

        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangedMessage(e.getNewGameMode().toString().toLowerCase()));
    }
}
