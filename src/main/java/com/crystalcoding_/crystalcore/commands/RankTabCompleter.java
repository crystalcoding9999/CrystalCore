package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.ranks.Rank;
import com.crystalcoding_.crystalcore.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RankTabCompleter implements TabCompleter {

    private final RankManager rankManager;

    public RankTabCompleter() {
        this.rankManager = CrystalCore.getInstance().rankManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (!(sender instanceof Player)) {
            return completions;
        }

        if (args.length == 1) {
            completions.add("set");
            completions.add("list");
            completions.add("addparent");
            completions.add("removeparent");
            // Add more commands if needed
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "set":
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        completions.add(p.getName());
                    }
                    break;

                case "addparent":
                case "removeparent":
                    // Logic for completing available ranks
                    for (Rank rank : rankManager.getRanks()) {
                        completions.add(rank.getName());
                    }
                    break;

                // Add more cases for additional sub-commands if required

                default:
                    break;
            }
        } else if (args.length == 3 && (args[0].equalsIgnoreCase("addparent") || args[0].equalsIgnoreCase("removeparent") || args[0].equalsIgnoreCase("set"))) {
            // Logic for completing available ranks as parent ranks
            for (Rank rank : rankManager.getRanks()) {
                completions.add(rank.getName());
            }
        }

        return completions;
    }
}
