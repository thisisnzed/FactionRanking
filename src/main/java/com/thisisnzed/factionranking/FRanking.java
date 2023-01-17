package com.thisisnzed.factionranking;

import com.thisisnzed.factionranking.command.impl.CommandRanking;
import com.thisisnzed.factionranking.listener.impl.FactionListeners;
import com.thisisnzed.factionranking.placeholder.PlaceholderManager;
import com.thisisnzed.factionranking.plugin.Launcher;
import com.thisisnzed.factionranking.plugin.Plugin;
import com.thisisnzed.factionranking.sql.DatabaseManager;
import com.thisisnzed.factionranking.task.TaskUpdater;
import org.bukkit.Bukkit;

public class FRanking implements Plugin {

    @Override
    public void launch(final Launcher launcher) {
        final DatabaseManager databaseManager = launcher.getDatabaseManager();
        launcher.registerCommand(new CommandRanking(this, launcher, databaseManager));
        launcher.registerListeners(new FactionListeners(launcher, databaseManager));
        new TaskUpdater(databaseManager).runTaskTimerAsynchronously(launcher, 0L, 6000L);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new PlaceholderManager(databaseManager).register();
    }
}