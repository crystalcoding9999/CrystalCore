package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.homes.Home;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Core.playerOnly(sender);
            return false;
        }

        CrystalCore main = CrystalCore.getInstance();
        Player p = (Player) sender;

        if (args.length == 0) {
            Core.message(main.messageManager.getMissingArgumentsMessage("home", "<create|delete|list|teleport> <name>"), sender);
            return false;
        }

        String name = args[0];
        if (name.equalsIgnoreCase("create")) {
            name = args[1];
            if (main.homeManager.getHomes(p.getUniqueId()).get(name) != null) {
                Core.message(main.messageManager.getHomeExistsMessage(name), p);
                return false;
            }

            if (main.homeManager.getHomes(p.getUniqueId()).size() >= main.homeManager.getLimit(p)) {
                Core.message(main.messageManager.getHomeLimitMessage(),p);
                Core.message(String.valueOf(main.homeManager.getLimit(p)), p);
                return false;
            }

            main.homeManager.addHome(p.getUniqueId(), name, new Home(p.getLocation()));
            Core.message(main.messageManager.getHomeCreateMessage(name), p);
            return false;
        } else if (name.equalsIgnoreCase("delete")) {
            name = args[1];
            if (main.homeManager.getHomes(p.getUniqueId()).get(name) == null) {
                Core.message(main.messageManager.getHomeNotExistMessage(name), p);
                return false;
            }

            main.homeManager.removeHome(p.getUniqueId(), name);
            Core.message(main.messageManager.getHomeDeleteMessage(name), p);
            return false;
        } else if(name.equalsIgnoreCase("list")) {
            HashMap<String, Home> homes = main.homeManager.getHomes(p.getUniqueId());
            if (homes.isEmpty()) {
                Core.message(main.messageManager.getHomeNoneMessage(), p);
                return false;
            }
            Core.message(main.messageManager.getPrefix() + " You have " + homes.size() + " home(s)", p);
            int index = 1;
            for (Map.Entry<String, Home> entry : homes.entrySet()) {
                String homeName = entry.getKey();
                Home home = entry.getValue();
                Core.message(main.messageManager.getPrefix() + " " + index + ". " + homeName + ", X: " + Core.decimals(1, home.getX()) + ", Y: " + Core.decimals(1, home.getY()) + ", Z: " + Core.decimals(1, home.getZ()) + ", World: " + home.getWorld(), p);
                index++;
            }
            return false;
        } else if (name.equalsIgnoreCase("teleport")) name = args[1];

        Home home = main.homeManager.getHomes(p.getUniqueId()).get(name);

        if (home == null) {
            Core.message(CrystalCore.getInstance().messageManager.getHomeNotExistMessage(name), p);
            return false;
        }

        Location location = home.getLoc();

        if (location == null) {
            Core.message(CrystalCore.getInstance().messageManager.getPrefix() + " &cAn unkown error has occured!", p);
            return false;
        }

        p.teleport(location);
        Core.message(CrystalCore.getInstance().messageManager.getHomeTeleportMessage(name), p);

        return false;
    }
}
