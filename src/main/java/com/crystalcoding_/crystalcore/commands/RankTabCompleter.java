package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.ranks.Rank;
import com.crystalcoding_.crystalcore.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

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
            completions.add("settemporary");
            completions.add("settemp");
            completions.add("list");
            completions.add("addparent");
            completions.add("removeparent");
            completions.add("create");
            completions.add("delete");
            completions.add("remove");
            completions.add("setprefix");
            completions.add("addpermission");
            completions.add("removepermission");
            completions.add("info");
            completions.add("setdefault");
            // Add more commands if needed
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "set":
                case "settemporary":
                case "settemp":
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        completions.add(p.getName());
                    }
                    break;

                case "addparent":
                case "removeparent":
                case "info":
                case "addpermission":
                case "removepermission":
                    // Logic for completing available ranks
                    for (Rank rank : rankManager.getRanks()) {
                        completions.add(rank.getName());
                    }
                    break;

                case "setprefix":
                    for (Rank rank : rankManager.getRanks()) {
                        completions.add(rank.getName());
                    }
                    break;

                case "delete":
                case "remove":
                case "setdefault":
                    for (Rank rank : rankManager.getRanks()) {
                        if (!rankManager.isDefault(rank)) {
                            completions.add(rank.getName());
                        }
                    }
                    break;

                // Add more cases for additional sub-commands if required

                default:
                    break;
            }
        } else if (args.length == 3) {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "set":
                case "settemporary":
                case "settemp":
                case "addparent":
                case "removeparent":
                case "addpermission":
                    for (Permission permission : CrystalCore.getInstance().getServer().getPluginManager().getPermissions()) {
                        completions.add(permission.getName());
                    }
                case "removepermission":
                    String rankName = args[1];
                    Rank rank = rankManager.getRank(rankName);
                    if (rank != null) {
                        completions.addAll(rank.getPermissions());
                    }
            }
        }

        return completions;
    }
}
