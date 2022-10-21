package com.thisisnzed.factionranking.command.subcommand;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.command.SubCommandManager;
import com.thisisnzed.factionranking.config.impl.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandHelp extends SubCommandManager {

    @Override
    public void execute(final Ranking ranking, final CommandSender sender, final String[] args) {
        if (!sender.hasPermission(ranking.getSettings().getPermission())) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        Lang.COMMAND_HELP.getList().forEach(e -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', e)));
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}