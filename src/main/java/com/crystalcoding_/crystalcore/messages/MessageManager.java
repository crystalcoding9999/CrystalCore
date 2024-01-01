package com.crystalcoding_.crystalcore.messages;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.ranks.Rank;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MessageManager {
    private final String Prefix;
    private final String JoinMessage;
    private final String LeaveMessage;
    private final String SilentJoinMessage;
    private final String SilentLeaveMessage;
    private final String SelfVanishMessage;
    private final String SelfUnvanishMessage;
    private final String OtherVanishMessage;
    private final String OtherUnvanishMessage;
    private final String RankChangedMessage;
    private final String TempRankChangedMessage;
    private final String RankDeletedMessage;
    private final String NicknameChangedMessage;
    private final String NicknameResetMessage;
    private final String PlayerOnlyMessage;
    private final String NoPermissionMessage;
    private final String MissingArgumentsMessage;
    private final String RankNotFoundMessage;
    private final String PlayerNotFoundMessage;

    public MessageManager(CrystalCore plugin) {
        FileConfiguration config = plugin.getConfig();

        Prefix = color(color(config.getString("messages.prefix")));
        JoinMessage = color(config.getString("messages.JoinMessage").replace("{prefix}", Prefix));
        LeaveMessage = color(config.getString("messages.LeaveMessage").replace("{prefix}", Prefix));
        SilentJoinMessage = color(config.getString("messages.SilentJoinMessage").replace("{prefix}", Prefix));
        SilentLeaveMessage = color(config.getString("messages.SilentLeaveMessage").replace("{prefix}", Prefix));
        SelfVanishMessage = color(config.getString("messages.SelfVanishMessage").replace("{prefix}", Prefix));
        SelfUnvanishMessage = color(config.getString("messages.SelfUnvanishMessage").replace("{prefix}", Prefix));
        OtherVanishMessage = color(config.getString("messages.OtherVanishMessage").replace("{prefix}", Prefix));
        OtherUnvanishMessage = color(config.getString("messages.OtherUnvanishMessage").replace("{prefix}", Prefix));
        RankChangedMessage = color(config.getString("messages.RankChangedMessage").replace("{prefix}", Prefix));
        TempRankChangedMessage = color(config.getString("messages.TempRankChangedMessage").replace("{prefix}", Prefix));
        RankDeletedMessage = color(config.getString("messages.RankDeletedMessage").replace("{prefix}", Prefix));
        NicknameChangedMessage = color(config.getString("messages.NicknameChangedMessage").replace("{prefix}", Prefix));
        NicknameResetMessage = color(config.getString("messages.NicknameResetMessage").replace("{prefix}", Prefix));
        PlayerOnlyMessage = color(config.getString("messages.PlayerOnlyMessage").replace("{prefix}", Prefix));
        NoPermissionMessage = color(config.getString("messages.NoPermissionMessage").replace("{prefix}", Prefix));
        MissingArgumentsMessage = color(config.getString("messages.MissingArgumentsMessage").replace("{prefix}", Prefix));
        RankNotFoundMessage = color(config.getString("messages.RankNotFoundMessage").replace("{prefix}", Prefix));
        PlayerNotFoundMessage = color(config.getString("messages.PlayerNotFoundMessage").replace("{prefix}", Prefix));
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
}
