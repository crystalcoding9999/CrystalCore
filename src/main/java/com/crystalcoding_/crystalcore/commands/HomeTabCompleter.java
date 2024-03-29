package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.homes.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> responses = new ArrayList<>();

        if (args.length == 1) {
            responses.add("create");
            responses.add("delete");
            responses.add("teleport");
            responses.add("list");

            /*
            if (sender instanceof Player) {
                if (CrystalCore.getInstance().homeManager.getHomes(((Player) sender).getUniqueId()) != null) {
                    for (Map.Entry<String, Home> entry : CrystalCore.getInstance().homeManager.getHomes(((Player) sender).getUniqueId()).entrySet()) {
                        responses.add(entry.getKey());
                    }
                }
            }
             */
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("teleport")) {
                if (sender instanceof Player) {
                    for (Map.Entry<String, Home> entry : CrystalCore.getInstance().homeManager.getHomes(((Player) sender).getUniqueId()).entrySet()) {
                        responses.add(entry.getKey());
                    }
                }
            }
        }

        return responses;
    }
}
