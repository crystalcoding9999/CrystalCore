package com.crystalcoding_.crystalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 1) {
            if (sender instanceof Player) {
                completions.add("on");
                completions.add("off");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (((Player) sender).canSee(p)) {
                        completions.add(p.getName());
                    }
                }
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    completions.add(p.getName());
                }
            }
        } else if (args.length == 2) {
            if (!(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off"))) {
                completions.add("on");
                completions.add("off");
            }
        }
        return completions;
    }
}
