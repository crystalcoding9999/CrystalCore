package com.crystalcoding_.crystalcore;

import com.crystalcoding_.crystalcore.commands.*;
import com.crystalcoding_.crystalcore.listeners.ChangeGamemodeListener;
import com.crystalcoding_.crystalcore.listeners.ChatListener;
import com.crystalcoding_.crystalcore.listeners.JoinListener;
import com.crystalcoding_.crystalcore.listeners.QuitListener;
import com.crystalcoding_.crystalcore.messages.MessageManager;
import com.crystalcoding_.crystalcore.name.NameManager;
import com.crystalcoding_.crystalcore.ranks.RankManager;
import com.crystalcoding_.crystalcore.staffchat.StaffchatManager;
import com.crystalcoding_.crystalcore.vanish.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CrystalCore extends JavaPlugin {

    public VanishManager vanishManager;
    public MessageManager messageManager;
    public RankManager rankManager;
    public NameManager nameManager;
    public StaffchatManager staffchatManager;
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
        staffchatManager = new StaffchatManager();

        new JoinListener(this);
        new QuitListener(this);
        new ChangeGamemodeListener(this);
        new ChatListener(this);

        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("nickname").setExecutor(new NicknameCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("rank").setTabCompleter(new RankTabCompleter());
        getCommand("staffchat").setExecutor(new StaffchatCommand());

        getCommand("message").setExecutor(new MessageCommand());
        getCommand("message").setTabCompleter(new AllPlayersTabCompleter());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("heal").setTabCompleter(new AllPlayersTabCompleter());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("feed").setTabCompleter(new AllPlayersTabCompleter());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("fly").setTabCompleter(new FlyTabCompleter());

        // gamemode commands
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("gamemode").setTabCompleter(new GamemodeTabCompleter());
        getCommand("gms").setExecutor(new GamemodeCommand());
        getCommand("gmc").setExecutor(new GamemodeCommand());
        getCommand("gmsp").setExecutor(new GamemodeCommand());
        getCommand("gma").setExecutor(new GamemodeCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player p : vanishManager.getVanishedPlayers()) {
            Core.message(messageManager.getPrefix() + " &cWarning! the CrystalCore plugin has been disabled!", p);
            Core.message(messageManager.getPrefix() + " &cYou will not be able to unvanish and you will be visible to players that join!", p);
        }
    }

    public static CrystalCore getInstance() {
        return instance;
    }
}
