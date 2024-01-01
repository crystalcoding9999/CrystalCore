package com.crystalcoding_.crystalcore.vanish;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.messages.MessageManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishManager {

    private ArrayList<Player> VanishedPlayers;

    public VanishManager() {
        VanishedPlayers = new ArrayList<>();
    }

    public void setVanished(Player p, boolean v) {
        if (v) {
            VanishedPlayers.add(p);
            CrystalCore.getInstance().nameManager.updatePlayerListName(p);
        } else {
            VanishedPlayers.remove(p);
            CrystalCore.getInstance().nameManager.updatePlayerListName(p);
        }
    }

    public void playerLeft(Player p) {
        VanishedPlayers.remove(p);
    }

    public boolean isVanished(Player p) {
        return VanishedPlayers.contains(p);
    }

    public ArrayList<Player> getVanishedPlayers() {
        return VanishedPlayers;
    }

    public void refreshVanishedPlayers(Player p) {
        /**
        re-hides all the vanished players and unvanishes them (if vanished) if the player doesn't have the 'crystalcore.vanish' permission or shows them if they do have the 'crystalcore.vanish' permission.
        @param p the player to refresh the vanished players for
         */

        if (p.hasPermission("crystalcore.vanish")) {
            for (Player vp : VanishedPlayers) {
                if (!p.canSee(vp)) {
                    p.showPlayer(CrystalCore.getInstance(), vp);
                }
            }
        } else {
            setVanished(p, false);
            for (Player vp : VanishedPlayers) {
                if (p.canSee(vp)) {
                    p.hidePlayer(CrystalCore.getInstance(), vp);
                }
            }
        }
    }
}
