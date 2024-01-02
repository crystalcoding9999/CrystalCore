package com.crystalcoding_.crystalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AllPlayersTabCompleter implements TabCompleter {
    /*
    Used if a command only needs every player as a tab completer
     */


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList<String> completions = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            completions.add(player.getName());
        }

        return completions;
    }
}
