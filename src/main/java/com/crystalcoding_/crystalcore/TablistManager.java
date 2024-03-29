package com.crystalcoding_.crystalcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TablistManager {

    private static final int REFRESH_TICKS = 20; // 1 second

    public void enableTablist() {
        Bukkit.getScheduler().runTaskTimer(CrystalCore.getInstance(), this::updateTablist, 0, REFRESH_TICKS);
    }

    private void updateTablist() {
        int onlinePlayers = Bukkit.getOnlinePlayers().size() - CrystalCore.getInstance().vanishManager.getVanishedPlayers().size();

        String header = Core.color("&eWelcome to the Server!");
        String footer = Core.color("&aOnline Players: &f" + onlinePlayers);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeader(header);

            if (player.hasPermission("crystalcore.vanish")) {
                footer = footer + "\n&7Vanished people: " + CrystalCore.getInstance().vanishManager.getVanishedPlayers().size();
            }

            player.setPlayerListFooter(Core.color(footer));
        }
    }
}
