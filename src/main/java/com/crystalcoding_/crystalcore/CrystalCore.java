package com.crystalcoding_.crystalcore;

import com.crystalcoding_.crystalcore.commands.*;
import com.crystalcoding_.crystalcore.listeners.JoinListener;
import com.crystalcoding_.crystalcore.listeners.QuitListener;
import com.crystalcoding_.crystalcore.messages.MessageManager;
import com.crystalcoding_.crystalcore.name.NameManager;
import com.crystalcoding_.crystalcore.ranks.RankManager;
import com.crystalcoding_.crystalcore.vanish.VanishManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CrystalCore extends JavaPlugin {

    public VanishManager vanishManager;
    public MessageManager messageManager;
    public RankManager rankManager;
    public NameManager nameManager;
    private static CrystalCore instance;

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        saveDefaultConfig();
        // Plugin startup logic
        instance = this;

        vanishManager = new VanishManager();
        nameManager = new NameManager();
        rankManager = new RankManager(this);
        messageManager = new MessageManager(this);

        new JoinListener(this);
        new QuitListener(this);

        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("nickname").setExecutor(new NicknameCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("rank").setTabCompleter(new RankTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CrystalCore getInstance() {
        return instance;
    }
}
