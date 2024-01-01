package com.crystalcoding_.crystalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GamemodeTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("survival");
            completions.add("creative");
            completions.add("spectator");
            completions.add("adventure");
        } else if (args.length == 2) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (sender instanceof Player) {
                    if (((Player) sender).canSee(p)) {
                        completions.add(p.getName());
                    }
                } else {
                    completions.add(p.getName());
                }
            }
        }

        return completions;
    }
}
