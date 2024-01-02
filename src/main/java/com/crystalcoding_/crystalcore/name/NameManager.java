package com.crystalcoding_.crystalcore.name;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.messages.MessageManager;
import com.crystalcoding_.crystalcore.ranks.Rank;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class NameManager {

    public HashMap<Player, String> nicknames;

    public NameManager() {
        nicknames = new HashMap<>();
    }

    public void setNickName(Player p, String n) {
        nicknames.put(p, n);
        updateDisplayName(p);
        updatePlayerListName(p);
    }

    public void resetNickName(Player p) {
        nicknames.remove(p);
        updateDisplayName(p);
        updatePlayerListName(p);
    }

    public void updateDisplayName(Player p) {
        Rank playerRank = CrystalCore.getInstance().rankManager.getPlayerRank(p);

        String playerName = p.getName();
        if (nicknames.containsKey(p)) {
            playerName = nicknames.get(p);
        }

        String newPlayerName = playerRank.getPrefix() + " " + playerName;

        p.setDisplayName(Core.color(newPlayerName));
    }

    public void updatePlayerListName(Player p) {
        Rank playerRank = CrystalCore.getInstance().rankManager.getPlayerRank(p);

        String playerName = p.getName();
        if (nicknames.containsKey(p)) {
            playerName = nicknames.get(p);
        }

        String newPlayerName = playerRank.getPrefix() + " " + playerName;

        if (CrystalCore.getInstance().vanishManager.isVanished(p)) {
            newPlayerName = newPlayerName + " &6[Vanished]";
        }

        p.setPlayerListName(Core.color(newPlayerName));
    }
}
