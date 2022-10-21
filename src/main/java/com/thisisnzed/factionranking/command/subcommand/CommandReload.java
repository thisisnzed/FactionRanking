package com.thisisnzed.factionranking.command.subcommand;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.command.SubCommandManager;
import com.thisisnzed.factionranking.config.impl.Lang;
import com.thisisnzed.factionranking.utils.PluginHelper;
import org.bukkit.command.CommandSender;

public class CommandReload extends SubCommandManager {

    @Override
    public void execute(final Ranking ranking, final CommandSender sender, final String[] args) {
        if (!sender.hasPermission(ranking.getSettings().getPermission())) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        PluginHelper.unload(sender, "Ranking");
        PluginHelper.load(sender, "Ranking", ranking.getLauncher());
    }

    @Override
    public String name() {
        return "reload";
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