package com.thisisnzed.factionranking.command.impl;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.thisisnzed.factionranking.command.Command;
import com.thisisnzed.factionranking.file.FileManager;
import com.thisisnzed.factionranking.plugin.Launcher;
import com.thisisnzed.factionranking.plugin.Plugin;
import com.thisisnzed.factionranking.sql.DatabaseManager;
import com.thisisnzed.factionranking.sql.impl.RankingHandler;
import com.thisisnzed.factionranking.utils.CommandResult;
import com.thisisnzed.factionranking.utils.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandRanking extends Command {

    private final DatabaseManager databaseManager;
    private final Launcher launcher;

    public CommandRanking(final Plugin plugin, final Launcher launcher, final DatabaseManager databaseManager) {
        super(plugin, "factionranking", "Main command of the plugin");
        this.launcher = launcher;
        this.databaseManager = databaseManager;
    }

    @Override
    protected CommandResult perform() {
        Bukkit.getScheduler().runTaskAsynchronously(this.launcher, () -> {
            try {
                if (super.args.length == 0) {
                    this.sendHelp();
                    return;
                }
                switch (super.args[0].toLowerCase()) {
                    case "top": {
                        final RankingHandler rankingHandler = this.databaseManager.getRankingHandler();
                        super.commandSender.sendMessage("§6§lFaction Ranking §7- §fTop 10");
                        for (final String faction : rankingHandler.getAllFactions(10))
                            super.commandSender.sendMessage("§7" + rankingHandler.getRank(faction).get() + ". §f" + faction + " §7- §f" + rankingHandler.getPoints(faction).get() + " points");
                        break;
                    }
                    case "reload": {
                        if (this.hasPermission("factionranking.reload")) {
                            FileManager.CONFIG.reload();
                            this.commandSender.sendMessage("§aThe config has been reloaded");
                        }
                        break;
                    }
                    case "add": {
                        if (this.hasPermission("factionranking.add")) {
                            if (super.args.length != 3) {
                                super.commandSender.sendMessage("§cUsage: /factionranking add <player> <points>");
                                break;
                            }
                            final String uncheckedAmount = super.args[2];
                            if (!NumberUtils.isInteger(uncheckedAmount)) {
                                super.commandSender.sendMessage("§cThe amount must be a number");
                                break;
                            }
                            final Player target = Bukkit.getPlayer(super.args[1]);
                            if (target == null) {
                                super.commandSender.sendMessage("§cThe player is not online");
                                break;
                            }
                            final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(target);
                            if (!fPlayer.hasFaction()) {
                                super.commandSender.sendMessage("§cThe player is not in a faction");
                                break;
                            }
                            final String faction = fPlayer.getFaction().getTag();
                            final int amount = Integer.parseInt(uncheckedAmount);
                            this.databaseManager.getRankingHandler().addPoints(faction, amount);
                            super.commandSender.sendMessage("§aThe faction §e" + faction + " §ahas been added §e" + amount + " §apoints");
                        }
                        break;
                    }
                    case "remove": {
                        if (this.hasPermission("factionranking.remove")) {
                            if (super.args.length != 3) {
                                super.commandSender.sendMessage("§cUsage: /factionranking remove <player> <points>");
                                break;
                            }
                            final String uncheckedAmount = super.args[2];
                            if (!NumberUtils.isInteger(uncheckedAmount)) {
                                super.commandSender.sendMessage("§cThe amount must be a number");
                                break;
                            }
                            final Player target = Bukkit.getPlayer(super.args[1]);
                            if (target == null) {
                                super.commandSender.sendMessage("§cThe player is not online");
                                break;
                            }
                            final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(target);
                            if (!fPlayer.hasFaction()) {
                                super.commandSender.sendMessage("§cThe player is not in a faction");
                                break;
                            }
                            final String faction = fPlayer.getFaction().getTag();
                            final int amount = Integer.parseInt(uncheckedAmount);
                            this.databaseManager.getRankingHandler().removePoints(faction, amount);
                            super.commandSender.sendMessage("§aThe faction §e" + faction + " §ahas been removed §e" + amount + " §apoints");
                        }
                        break;
                    }
                    default: {
                        this.sendHelp();
                    }
                }
            } catch (final Exception exception) {
                exception.printStackTrace();
            }
        });
        return CommandResult.SUCCESS;
    }

    private boolean hasPermission(final String permission) {
        final boolean bool = super.commandSender.hasPermission(permission);
        if (!bool) super.commandSender.sendMessage("§cYou don't have permission to do this");
        return bool;
    }

    private void sendHelp() {
        super.commandSender.sendMessage("§6§lFactionRanking §7- §fHelp");
        super.commandSender.sendMessage("§7/factionranking help §8- §fShow this help");
        super.commandSender.sendMessage("§7/factionranking top §8- §fShow the top factions");
        super.commandSender.sendMessage("§7/factionranking reload §8- §fReload the plugin");
        super.commandSender.sendMessage("§7/factionranking add <player> <points> §8- §fAdd points to a faction by a player");
        super.commandSender.sendMessage("§7/factionranking remove <player> <points> §8- §fRemove points to a faction by a player");
    }
}
