package com.crystalcoding_.crystalcore.messages;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.ranks.Rank;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MessageManager {
    private final String Prefix;

    // Join/Leave messages
    private final String JoinMessage;
    private final String LeaveMessage;
    private final String SilentJoinMessage;
    private final String SilentLeaveMessage;

    // Vanish messages
    private final String SelfVanishMessage;
    private final String SelfUnvanishMessage;
    private final String OtherVanishMessage;
    private final String OtherUnvanishMessage;

    // Rank messages
    private final String RankChangedMessage;
    private final String TempRankChangedMessage;
    private final String RankDeletedMessage;

    // Nickname messages
    private final String NicknameChangedMessage;
    private final String NicknameResetMessage;


    // Command error messages
    private final String PlayerOnlyMessage;
    private final String NoPermissionMessage;
    private final String MissingArgumentsMessage;
    private final String RankNotFoundMessage;
    private final String PlayerNotFoundMessage;

    // Command Responses
    private final String GamemodeChangedMessage;
    private final String GamemodeChangeOtherMessage;

    public MessageManager(CrystalCore plugin) {
        FileConfiguration config = plugin.getConfig();

        Prefix = color(getFromConfig("messages.prefix", config));
        JoinMessage = color(getFromConfig("messages.JoinMessage", config));
        LeaveMessage = color(getFromConfig("messages.LeaveMessage", config));
        SilentJoinMessage = color(getFromConfig("messages.SilentJoinMessage", config));
        SilentLeaveMessage = color(getFromConfig("messages.SilentLeaveMessage", config));
        SelfVanishMessage = color(getFromConfig("messages.SelfVanishMessage", config));
        SelfUnvanishMessage = color(getFromConfig("messages.SelfUnvanishMessage", config));
        OtherVanishMessage = color(getFromConfig("messages.OtherVanishMessage", config));
        OtherUnvanishMessage = color(getFromConfig("messages.OtherUnvanishMessage", config));
        RankChangedMessage = color(getFromConfig("messages.RankChangedMessage", config));
        TempRankChangedMessage = color(getFromConfig("messages.TempRankChangedMessage", config));
        RankDeletedMessage = color(getFromConfig("messages.RankDeletedMessage", config));
        NicknameChangedMessage = color(getFromConfig("messages.NicknameChangedMessage", config));
        NicknameResetMessage = color(getFromConfig("messages.NicknameResetMessage", config));
        PlayerOnlyMessage = color(getFromConfig("messages.PlayerOnlyMessage", config));
        NoPermissionMessage = color(getFromConfig("messages.NoPermissionMessage", config));
        MissingArgumentsMessage = color(getFromConfig("messages.MissingArgumentsMessage", config));
        RankNotFoundMessage = color(getFromConfig("messages.RankNotFoundMessage", config));
        PlayerNotFoundMessage = color(getFromConfig("messages.PlayerNotFoundMessage", config));
        GamemodeChangedMessage = color(getFromConfig("messages.GamemodeChangedMessage", config));
        GamemodeChangeOtherMessage = color(getFromConfig("messages.GamemodeChangeOtherMessage", config));
    }

    String getFromConfig(String key, FileConfiguration config) {
        if (Prefix == null) {
            return config.getString(key, "");
        }
        return config.getString(key, "").replace("{prefix}", Prefix);
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public String getPrefix() {
        return Prefix;
    }

    String getPlayerRankPrefix(Player p) {
        return CrystalCore.getInstance().rankManager.getPlayerRank(p).getPrefix();
    }

    public String getJoinMessage(Player p) {
        return color(JoinMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getLeaveMessage(Player p) {
        return color(LeaveMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getSilentJoinMessage(Player p) {
        return color(SilentJoinMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getSilentLeaveMessage(Player p) {
        return color(SilentLeaveMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getSelfVanishMessage() {
        return SelfVanishMessage;
    }

    public String getSelfUnvanishMessage() {
        return SelfUnvanishMessage;
    }

    public String getOtherVanishMessage(Player p) {
        return color(OtherVanishMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getOtherUnvanishMessage(Player p) {
        return color(OtherUnvanishMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getRankChangedMessage(Rank r) {
        return color(RankChangedMessage.replace("{rank}", r.getName()));
    }

    public String getTempRankChangedMessage(Rank r) {
        return color(TempRankChangedMessage.replace("{rank}", r.getName()));
    }

    public String getRankDeletedMessage() {
        return RankDeletedMessage;
    }

    public String getNicknameChangedMessage(String nickname) {
        return NicknameChangedMessage.replace("{nick}", nickname);
    }

    public String getNicknameResetMessage() {
        return NicknameResetMessage;
    }

    public String getPlayerOnlyMessage() {
        return PlayerOnlyMessage;
    }

    public String getNoPermissionMessage() {
        return NoPermissionMessage;
    }

    public String getMissingArgumentsMessage(String command) {
        return color(MissingArgumentsMessage.replace("{command}", command));
    }

    public String getMissingArgumentsMessage(String command, String args) {
        return color(getMissingArgumentsMessage(command).replace("{args}", args));
    }

    public String getRankNotFoundMessage(String rank) {
        return RankNotFoundMessage.replace("{rank}", rank);
    }

    public String getPlayerNotFoundMessage(String player) {
        return PlayerNotFoundMessage.replace("{player}", player);
    }

    public String getGamemodeChangedMessage(String gamemode) {
        return GamemodeChangedMessage.replace("{gamemode}", gamemode);
    }

    public String getGamemodeChangeOtherMessage(String player, String gamemode) {
        return GamemodeChangeOtherMessage.replace("{player}", player).replace("{gamemode}", gamemode);
    }
}
