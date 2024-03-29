package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Core.playerOnly(sender);
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("crystalcore.time")) {
            Core.noPermission(sender);
            return false;
        }

        if (args.length != 0) {
            Core.message("Usage: /" + label, sender);
            return false;
        }

        String timeArgument = label.toLowerCase();

        if (!timeArgument.equals("day") && !timeArgument.equals("night")) {
            Core.message("Invalid time argument. Usage: /" + label, sender);
            return false;
        }

        World world = player.getWorld();
        long time = 0;

        if (timeArgument.equals("night")) {
            time = 13000;
        }

        setTime(world, time);
        Core.message("Time set to " + timeArgument + ".", sender);
        return true;
    }

    private void setTime(World world, long time) {
        world.setTime(time);
    }
}




