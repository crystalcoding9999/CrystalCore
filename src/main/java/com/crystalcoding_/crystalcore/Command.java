package com.crystalcoding_.crystalcore;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class Command extends BukkitCommand {

    public Command(String command, String[] aliases, String description) {
        super(command);

        this.setAliases(Arrays.asList(aliases));
        this.setDescription(description);

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true); // FBI! OPEN UP! (force ourselves in)
            CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            map.register(command, this); // actually register the command
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        execute(sender, args);
        return false;
    }

    public abstract void execute(CommandSender sender, String[] args);
}
